package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class DBWebSecurityConfig {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Bean
	SecurityFilterChain configure(HttpSecurity http)
			throws java.lang.Exception {
		http.cors().and().csrf().disable().authorizeHttpRequests()
		.requestMatchers("/actuator/**").authenticated()
		.requestMatchers("/**").permitAll();
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .httpBasic();
		return http.build();
	}

}
