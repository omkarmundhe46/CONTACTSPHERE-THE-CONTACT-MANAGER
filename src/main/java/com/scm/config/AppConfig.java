package com.scm.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
@Configuration
public class AppConfig {
    @Value("${cloudinary.cloud.name}")
    private String cloudName;
    @Value("${cloudinary.api.key}")
    private String apiKey;
    @Value("${cloudinary.api.secret}")
    private String apiSecret;
    @Bean
    public Cloudinary cloudinary() {
        System.out.println("Cloudinary Name: " + cloudName);
        System.out.println("Cloudinary API Key: " + apiKey);
        System.out.println("Cloudinary API Secret: " + apiSecret);
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret)
        );
    }
}
