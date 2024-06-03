package waitorder.config;

import edu.fudan.common.security.jwt.JWTFilter;
import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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
public class SecurityConfig {

    String admin = "ADMIN";
    String order = "/api/v1/orderservice/order";

    /**
     * load password encoder
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * allow cors domain
     * header  By default, only six fields can be taken from the header, and the other fields can only be specified in the header.
     * credentials   Cookies are not sent by default and can only be true if a Cookie is needed
     * Validity of this request
     *
     * @return WebMvcConfigurer
     */
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


    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // close default csrf
                .csrf(AbstractHttpConfigurer::disable)
                // close session
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.POST, order).hasAnyRole(admin, "USER")
                        .requestMatchers(HttpMethod.PUT, order).hasAnyRole(admin, "USER")
                        .requestMatchers(HttpMethod.DELETE, order).hasAnyRole(admin, "USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/orderservice/order/admin").hasAnyRole(admin)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/orderservice/order/admin").hasAnyRole(admin)
                        .requestMatchers("/api/v1/orderservice/order/**").permitAll()
                        .requestMatchers("/swagger-ui.html", "/webjars/**", "/images/**", "/configuration/**", "/swagger-resources/**", "/v2/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);

        // close cache
        httpSecurity.headers(withDefaults());
        return httpSecurity.build();
    }
}
