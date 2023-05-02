package guru.sfg.brewery.config;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Modified by Pierrot on 2023-05-02.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Redundant as per SB3.0.x thus optional
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                .authorizeHttpRequests()
                        .requestMatchers("/h2-console/**").permitAll() //do not use in production!
                        .requestMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                .and()
                .authorizeHttpRequests()
                        .anyRequest().authenticated()
                .and()
                        .formLogin()
                .and()
                        .httpBasic()
                .and()
                        .csrf().disable();

                //h2 console config
                http.headers().frameOptions().sameOrigin();
                return http.build();
    }

    // Also Redundant!!!
    @Bean
    PasswordEncoder passwordEncoder(){
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
