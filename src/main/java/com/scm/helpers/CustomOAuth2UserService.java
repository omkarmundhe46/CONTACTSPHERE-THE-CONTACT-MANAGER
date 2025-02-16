package com.scm.helpers;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        String clientName = userRequest.getClientRegistration().getClientName();

        if ("Google".equalsIgnoreCase(clientName)) {
            System.out.println("Login with Google: " + attributes);
        } else if ("GitHub".equalsIgnoreCase(clientName)) {
            System.out.println("Login with GitHub: " + attributes);
        }

        return new DefaultOAuth2User(oAuth2User.getAuthorities(), attributes, userNameAttributeName);
    }
}
