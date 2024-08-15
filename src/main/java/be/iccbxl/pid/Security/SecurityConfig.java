package be.iccbxl.pid.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig  {

	@Autowired
	CustomUserDetailService customUserDetailService;

    
	   @Bean
	 	BCryptPasswordEncoder bCryptPasswordEncoder() {
	 		return new BCryptPasswordEncoder();
	 	}
	   
	   @Bean
		public DaoAuthenticationProvider authenticationManager(
				CustomUserDetailService userDetailsService,
				BCryptPasswordEncoder passwordEncoder) {
			DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
			authenticationProvider.setUserDetailsService(customUserDetailService);
			authenticationProvider.setPasswordEncoder(passwordEncoder);


			return authenticationProvider;
		}


		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
			return http.authorizeHttpRequests(auth -> {
				//auth.requestMatchers("/admin").hasRole("ADMIN");
				auth.requestMatchers("/user/**").hasRole("member");
				auth.requestMatchers("/css/**", "/js/**", "/images/**", "/change-lang/**").permitAll();
				auth.requestMatchers("/login", "/register", "/admin/**", "/exportCSV", "rss/shows", 
						"/confirmationReservation", "/create-payment-intent"
						,"/stripe/**","/forgot-password").permitAll();
                auth.anyRequest().authenticated();
			})
					
				.formLogin(form -> {
					form.loginPage("/login");
					form.successForwardUrl("/home");
				})
				.csrf(csrf -> csrf.disable())
				.build();
		}

	

	
}
	

	



