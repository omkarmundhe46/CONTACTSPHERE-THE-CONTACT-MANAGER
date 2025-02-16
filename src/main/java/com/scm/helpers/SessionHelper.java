package com.scm.helpers;

import com.scm.entities.User;
import com.scm.repsitories.UserRepo;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.Map;
import java.util.Optional;

@Component
public class SessionHelper {

    public static void removeMessage() {
        try {
            System.out.println("removing message from session");
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                    .getSession();
            session.removeAttribute("message");
        } catch (Exception e) {
            System.out.println("Error in SessionHelper: " + e);
            ;
            e.printStackTrace();
        }

    }

    @Service
    public static class CustomOAuth2UserService extends DefaultOAuth2UserService {

        private final UserRepo userRepo;

        public CustomOAuth2UserService(UserRepo userRepo) {
            this.userRepo = userRepo;
        }

        @Override
        public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
            OAuth2User oAuth2User = super.loadUser(userRequest);

            // Extract details (email, name, etc.) from the OAuth2User
            Map<String, Object> attributes = oAuth2User.getAttributes();
            String email = (String) attributes.get("email");

            // Check if the user already exists in the database
            Optional<User> existingUser = userRepo.findByEmail(email);

            User user;
            if (existingUser.isPresent()) {
                // Update existing user if needed
                user = existingUser.get();
            } else {
                // Create a new user if they don’t exist
                user = new User();
                user.setEmail(email);
                user.setName((String) attributes.get("name"));
                // Set other fields as needed
                userRepo.save(user);
            }

            return oAuth2User;
        }
    }
}
