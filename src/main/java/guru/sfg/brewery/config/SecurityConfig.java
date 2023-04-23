package guru.sfg.brewery.config;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Modified by Pierrot on 2023-04-20.
 */
@Configuration
@EnableWebSecurity
// Enables @Secured specified in JSR-250 as per SB3.0.x
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String CUSTOMER_ROLE = "CUSTOMER";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                   .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PathRequest.toH2Console()).permitAll() //do not use in production!
                        .requestMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/beer/**")
                            .hasAnyRole(ADMIN_ROLE, CUSTOMER_ROLE, "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}")
                            .hasAnyRole(ADMIN_ROLE, CUSTOMER_ROLE, "USER")
                        .requestMatchers("/brewery/breweries")
                            .hasAnyRole(ADMIN_ROLE, CUSTOMER_ROLE)
                        .requestMatchers(HttpMethod.GET, "/brewery/api/v1/breweries")
                            .hasAnyRole(ADMIN_ROLE, CUSTOMER_ROLE)
                        .requestMatchers("/beers/find", "/beers/{beerId}")
                            .hasAnyRole(ADMIN_ROLE, CUSTOMER_ROLE, "USER"))
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
