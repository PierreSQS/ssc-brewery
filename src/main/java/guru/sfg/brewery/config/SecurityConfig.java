package guru.sfg.brewery.config;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Modified by Pierrot on 2023-04-23.
 */
@Configuration
@EnableWebSecurity
// Enables @Secured specified in JSR-250 as per SB3.0.x
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                   .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PathRequest.toH2Console()).permitAll() //do not use in production!
                        .requestMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll())
                   .authorizeHttpRequests().anyRequest().authenticated()
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

    @Bean
    PasswordEncoder passwordEncoder(){
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
