package com.scm.controllers;

import com.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.repsitories.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
    // verify email
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @GetMapping("/verify-email")
    public String verifyEmail(
            @RequestParam("token") String token, HttpSession session) {

        User user = userRepo.findByEmailToken(token).orElse(null);

        if (user != null) {
            // user fetch hua hai :: process karna hai
            if (user.getEmailToken().equals(token)) {
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);
                session.setAttribute("message", Message.builder()
                        .type(MessageType.green)
                        .content("You email is verified. Now you can login  ")
                        .build());
                return "success_page";
            }
            session.setAttribute("message", Message.builder()
                    .type(MessageType.red)
                    .content("Email not verified ! Token is not associated with user .")
                    .build());
            return "error_page";
        }
        session.setAttribute("message", Message.builder()
                .type(MessageType.red)
                .content("Email not verified ! Token is not associated with user .")
                .build());
        return "error_page";
    }


    @GetMapping("/forgot-password")
    public String forgotPasswordView() {
        return "forgot_password";
    }

    // Send OTP
    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email, HttpSession session) {
        try {
            userService.generateAndSendOtp(email);
            session.setAttribute("message", Message.builder()
                    .content("OTP sent to your email.")
                    .type(MessageType.green)
                    .build());
            return "redirect:/auth/reset-password"; // Redirect to reset password page
        } catch (Exception e) {
            session.setAttribute("message", Message.builder()
                    .content("Error: " + e.getMessage())
                    .type(MessageType.red)
                    .build());
            return "redirect:/auth/forgot-password";
        }
    }

    @GetMapping("/reset-password")
    public String resetPasswordView() {
        return "reset_password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword, HttpSession session) {
        try {
            if (userService.verifyOtp(email, otp)) {
                userService.resetPassword(email, newPassword);
                session.setAttribute("message", Message.builder()
                        .content("Password reset successfully.")
                        .type(MessageType.green)
                        .build());
                return "redirect:/login";
            } else {
                session.setAttribute("message", Message.builder()
                        .content("Invalid OTP.")
                        .type(MessageType.red)
                        .build());
                return "redirect:/auth/reset-password";
            }
        } catch (Exception e) {
            session.setAttribute("message", Message.builder()
                    .content("Error: " + e.getMessage())
                    .type(MessageType.red)
                    .build());
            return "redirect:/auth/reset-password";
        }
    }


}
