package com.scm;
import com.scm.config.AppConfig;
import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.repsitories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		System.setProperty("ACTIVE_PROFILE", System.getenv("ACTIVE_PROFILE"));
		System.setProperty("EMAIL_HOST", System.getenv("EMAIL_HOST"));
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${EMAIL_HOST}")
	private String emailHost;

	@Value("${EMAIL_PORT}")
	private String emailPort;

	@Value("${EMAIL_USERNAME}")
	private String emailUsername;

	@Value("${EMAIL_PASSWORD}")
	private String emailPassword;

	@Override
	public void run(String... args) throws Exception {
		// Log email configuration
		System.out.println("EMAIL_HOST: " + emailHost);
		System.out.println("EMAIL_PORT: " + emailPort);
		System.out.println("EMAIL_USERNAME: " + emailUsername);
		System.out.println("EMAIL_PASSWORD: " + emailPassword);

		User user = new User();
		user.setUserId(UUID.randomUUID().toString());
		user.setName("admin");
		user.setEmail("admin@gmail.com");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setRoleList(List.of(AppConstants.ROLE_USER));
		user.setEmailVerified(true);
		user.setEnabled(true);
		user.setAbout("This is dummy user created initially");
		user.setPhoneVerified(true);

		userRepo.findByEmail("admin@gmail.com").ifPresentOrElse(user1 -> {}, () -> {
			userRepo.save(user);
			System.out.println("user created");
		});
	}
}
