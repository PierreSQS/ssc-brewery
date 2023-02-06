package guru.sfg.brewery.config;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static guru.sfg.brewery.config.MyCustomDsl.customDsl;

/**
 * Updated by Pierrot on 1/17/22.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf().disable();

                // New Way in SB3.0.x to extra configure the HttpSecurity instance
                http.apply(customDsl());

                http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                        .requestMatchers("/beers/find", "/beers*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll())
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();

                return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("spring")
                .password("{bcrypt}$2a$10$7tYAvVL2/KwcQTcQywHIleKueg4ZK7y7d44hKyngjTwHCDlesxdla")
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername("user")
                .password("{sha256}1296cefceb47413d3fb91ac7586a4625c33937b4d3109f5a4dd96c79c46193a029db713b96006ded")
                .roles("USER")
                .build();
        
        UserDetails scott = User.withUsername("scott")
                .password("{bcrypt15}$2a$15$baOmQtw8UqWZRDQhMFPFj.xhkkWveCTQHe4OBdr8yw8QshejiSbI6")
                .roles("CUSTOMER")
                .build();
        
        return new InMemoryUserDetailsManager(admin, user, scott);
    }


}
