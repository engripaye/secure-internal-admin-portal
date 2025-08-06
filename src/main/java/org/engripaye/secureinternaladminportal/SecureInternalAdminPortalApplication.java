package org.engripaye.secureinternaladminportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:application-secret.yaml", factory = org.engripaye.secureinternaladminportal.yaml.YamlPropertySourceFactory.class)
public class SecureInternalAdminPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecureInternalAdminPortalApplication.class, args);
    }
}

