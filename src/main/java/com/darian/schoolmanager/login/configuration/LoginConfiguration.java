package com.darian.schoolmanager.login.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "login")
public class LoginConfiguration {

    private UserRoot userRoot;

    @Data
    public static class UserRoot {
        private String userName;

        private String password;
    }
}