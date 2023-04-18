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
 * Modified by Pierrot on 2023-04-18.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

                httpSecurity
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers(PathRequest.toH2Console()).permitAll() //do not use in production!
                            .requestMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/beer/**")
                                .hasAnyRole("ADMIN", "CUSTOMER", "USER")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}")
                                .hasAnyRole("ADMIN", "CUSTOMER", "USER")
                            .requestMatchers("/brewery/breweries")
                                .hasAnyRole("ADMIN", "CUSTOMER")
                            .requestMatchers(HttpMethod.GET, "/brewery/api/v1/breweries")
                                .hasAnyRole("ADMIN", "CUSTOMER")
                            .requestMatchers("/beers/find", "/beers/{beerId}")
                                .hasAnyRole("ADMIN", "CUSTOMER", "USER");
                } )
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic()
                .and().csrf().disable();

                //h2 console config
                httpSecurity.headers().frameOptions().sameOrigin();
                
                return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
