//    	•	Configureert de beveiliging van de applicatie.
//	•	Instellingen voor authenticatie en autorisatie.


package com.bouke.IJsvogelgezien.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Voeg hier andere beveiligingsconfiguraties toe als dat nodig is
}