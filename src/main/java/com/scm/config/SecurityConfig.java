package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import com.scm.services.impl.SecurityCustomUserDetailService;
import com.scm.helpers.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Autowired
    private OAuthAuthenicationSuccessHandler handler;

    @Autowired
    private AuthFailtureHandler authFailtureHandler;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(googleClientRegistration(), githubClientRegistration());
    }

    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId(System.getenv("GOOGLE_CLIENT_ID"))
                .clientSecret(System.getenv("GOOGLE_CLIENT_SECRET"))
                .scope("email", "profile")
                .authorizationUri("https://accounts.google.com/o/oauth2/auth")
                .tokenUri("https://oauth2.googleapis.com/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName("sub")
                .redirectUri("{baseUrl}/login/oauth2/code/google")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .build();
    }

    private ClientRegistration githubClientRegistration() {
        return ClientRegistration.withRegistrationId("github")
                .clientId(System.getenv("GITHUB_CLIENT_ID"))
                .clientSecret(System.getenv("GITHUB_CLIENT_SECRET"))
                .scope("user", "repo")
                .authorizationUri("https://github.com/login/oauth/authorize")
                .tokenUri("https://github.com/login/oauth/access_token")
                .userInfoUri("https://api.github.com/user")
                .userNameAttributeName("id")
                .redirectUri("{baseUrl}/login/oauth2/code/github")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/user/**").authenticated();
            authorize.requestMatchers("/auth/**", "/login", "/oauth2/**").permitAll();
            authorize.anyRequest().permitAll();
        });

        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login")
                    .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService));
            oauth.successHandler(handler);
        });

        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login")
                    .loginProcessingUrl("/authenticate")
                    .successForwardUrl("/user/profile")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .failureHandler(authFailtureHandler);
        });

        httpSecurity.logout(logout -> logout.logoutUrl("/do-logout").logoutSuccessUrl("/login?logout=true"));

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
