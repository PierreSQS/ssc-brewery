package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.Role;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.RoleRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Modified by Pierrot on 4/28/22.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private void loadSecurityData() {
        // Beer permissions
        Authority beerCreate = authorityRepository.save(Authority.builder().permission("beer.create").build());
        Authority beerUpdate = authorityRepository.save(Authority.builder().permission("beer.update").build());
        Authority beerRead = authorityRepository.save(Authority.builder().permission("beer.read").build());
        Authority beerDelete = authorityRepository.save(Authority.builder().permission("beer.delete").build());

        Role adminRole = roleRepository.save(Role.builder()
                .name("ROLE_ADMIN")
                .build());

        Role customerRole = roleRepository.save(Role.builder()
                .name("ROLE_CUSTOMER")
                .build());

        Role userRole = roleRepository.save(Role.builder()
                .name("ROLE_USER")
                .build());

        adminRole.setAuthorities(Set.of(beerCreate,beerUpdate,beerRead,beerDelete));
        customerRole.setAuthorities(Set.of(beerRead));
        userRole.setAuthorities(Set.of(beerUpdate,beerRead));

        userRepository.save(User.builder()
                .username("spring")
                .password(passwordEncoder.encode("guru"))
                .role(adminRole)
                .build());

        userRepository.save(User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .role(userRole)
                .build());

        userRepository.save(User.builder()
                .username("scott")
                .password(passwordEncoder.encode("tiger"))
                .role(customerRole)
                .build());

        log.debug("Users Loaded: " + userRepository.count());
    }

    @Override
    public void run(String... args) {
        if (authorityRepository.count() == 0) {
            loadSecurityData();
        }
    }


}
