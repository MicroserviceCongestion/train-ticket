package auth.config;


import edu.fudan.common.security.jwt.JWTFilter;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.web.cors.CorsConfiguration.ALL;

/**
 * @author fdse
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {


    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(@Qualifier("userDetailServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@Nonnull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(ALL)
                        .allowedMethods(ALL)
                        .allowedHeaders(ALL)
                        .allowCredentials(false)
                        .maxAge(3600);
            }
        };
    }

    /**
     * load password encoder
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/v1/auth", "/api/v1/auth/hello", "/api/v1/user/hello").permitAll()
                        .requestMatchers("/api/v1/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/*").hasRole("ADMIN")
                        // create user and role while user register
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/swagger-ui.html", "/webjars/**", "/images/**", "/configuration/**", "/swagger-resources/**", "/v2/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.headers(withDefaults());
        return httpSecurity.build();
    }
}
