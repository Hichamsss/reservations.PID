package be.iccbxl.pid.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/user/**").hasRole("member");
                auth.requestMatchers("/css/**", "/js/**", "/images/**", "/change-lang/**").permitAll();
                auth.requestMatchers("/login", "/register", "/admin/**", "/exportCSV", "/rss/shows",
                        "/confirmationReservation", "/create-payment-intent",
                        "/stripe/**", "/forgot-password", "/shows-rss").permitAll(); // Ajout de /shows-rss ici
                auth.anyRequest().authenticated();
            })
            .formLogin(form -> {
                form.loginPage("/login")
                    .defaultSuccessUrl("/home", true)
                    .failureUrl("/login?error=true")
                    .permitAll();
            })
            .logout(logout -> {
                logout.logoutUrl("/logout")
                       .logoutSuccessUrl("/login?logout=true")
                       .permitAll();
            })
            .csrf(csrf -> csrf.disable())
            .build();
    }
}
