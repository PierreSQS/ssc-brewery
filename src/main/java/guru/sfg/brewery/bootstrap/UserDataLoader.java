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
import org.springframework.stereotype.Component;

import java.util.List;
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

    private void loadSecurityData() {
        // Beer permissions
        Authority createBeer = authorityRepository.save(Authority.builder().permission("beer.create").build());
        Authority readBeer = authorityRepository.save(Authority.builder().permission("beer.read").build());
        Authority updateBeer = authorityRepository.save(Authority.builder().permission("beer.update").build());
        Authority deleteBeer = authorityRepository.save(Authority.builder().permission("beer.delete").build());

        // Customer permissions
        Authority createCustomer = authorityRepository.save(Authority.builder().permission("customer.create").build());
        Authority readCustomer = authorityRepository.save(Authority.builder().permission("customer.read").build());
        Authority updateCustomer = authorityRepository.save(Authority.builder().permission("customer.update").build());
        Authority deleteCustomer = authorityRepository.save(Authority.builder().permission("customer.delete").build());

        // Brewery permissions
        Authority createBrewery = authorityRepository.save(Authority.builder().permission("brewery.create").build());
        Authority readBrewery = authorityRepository.save(Authority.builder().permission("brewery.read").build());
        Authority updateBrewery = authorityRepository.save(Authority.builder().permission("brewery.update").build());
        Authority deleteBrewery = authorityRepository.save(Authority.builder().permission("brewery.delete").build());

        Role adminRole = roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
        Role customerRole = roleRepository.save(Role.builder().name("ROLE_CUSTOMER").build());
        Role userRole = roleRepository.save(Role.builder().name("ROLE_USER").build());

        adminRole.setAuthorities(Set.of(createBeer, updateBeer, readBeer, deleteBeer,
                createCustomer, readCustomer, updateCustomer, deleteCustomer,
                createBrewery, readBrewery, updateBrewery, deleteBrewery));

        customerRole.setAuthorities(Set.of(readBeer, updateBeer, readCustomer, updateCustomer, readBrewery, updateBrewery));

        userRole.setAuthorities(Set.of(readBeer, readCustomer,readBrewery));

        roleRepository.saveAll(List.of(adminRole, customerRole, userRole));

        userRepository.save(User.builder()
                .username("spring")
                .password("{bcrypt10}$2a$10$fmQXIYEj0HzlpUTpoaLypukDGMzJPobdhnmJ6Shwk4SSasUbebj0u") // guru
                .role(adminRole)
                .build());

        userRepository.save(User.builder()
                .username("user")
                .password("{bcrypt10}$2a$10$veQm7eW19JiqIn3Jd2WNAu/tR.7ZytXl5o5FqvwmMMRDbuE1oB3vK") // password
                .role(userRole)
                .build());

        User scott = userRepository.save(User.builder()
                .username("scott")
                .password("{bcrypt10}$2a$10$M9SEWILH/MQtY4NP5G7IX.4tpnVKyRygGS/iqN6svBLFAkVoTDxHG") // tiger
                .role(customerRole)
                .build());

        log.info("Users Loaded: " + userRepository.count());

        // Just to Check the loaded permissions in the DB for USER scott
        scott.getAuthorities().forEach(authority -> log.debug("##### {} #####",authority.getPermission()));
    }

    @Override
    public void run(String... args) {
        if (authorityRepository.count() == 0) {
            loadSecurityData();
        }
    }


}
