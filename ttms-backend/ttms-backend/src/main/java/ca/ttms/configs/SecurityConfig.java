package ca.ttms.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import lombok.RequiredArgsConstructor;

/**
 * @author Hamza & Akshat
 * date: 2023/03/07
 * description: Configures the security filter chain
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
}
