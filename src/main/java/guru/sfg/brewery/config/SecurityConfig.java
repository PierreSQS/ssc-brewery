package guru.sfg.brewery.config;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Modified by Pierrot on 2023-04-07.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain generalFilterChain(HttpSecurity http) throws Exception {

                http
                    .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PathRequest.toH2Console()).permitAll() //do not use in production!
                        .requestMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                        .requestMatchers("/beers/find", "/beers*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll())
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
    public SecurityFilterChain rolesFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityMatcher("/api/v1/beer/**")
                .authorizeHttpRequests()
                    .requestMatchers(HttpMethod.GET).permitAll()
                    .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .and()
                    .httpBasic()
                .and()
                    .csrf().disable();
        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
