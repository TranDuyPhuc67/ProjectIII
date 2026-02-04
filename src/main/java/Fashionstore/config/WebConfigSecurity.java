package Fashionstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import Fashionstore.services.AccountDetailService;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity {
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf((c) -> c.disable())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/**").permitAll()
						)
				.formLogin(formLogin -> formLogin 
						.loginPage("/login") 
						.usernameParameter("username") 
						.passwordParameter("password") 
						.successHandler((request, response, authentication) -> { 
							var authorities = authentication.getAuthorities(); 
							if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) { 
								response.sendRedirect("/admin"); 
							} else { 
								response.sendRedirect("/");
								} 
							}) 
							.failureUrl("/login?error") 
							.permitAll() 
						)	
				.logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll())
				.exceptionHandling((ex) -> ex.accessDeniedPage("/403"));

		return http.build();
	}
	
	
    UserDetailsService userDetailsService() {
		return new AccountDetailService();
	}
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}