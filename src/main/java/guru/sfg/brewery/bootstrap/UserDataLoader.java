package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.BeerUser;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.BeerUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Modified by Pierrot on 2/10/22.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final BeerUserRepository beerBeerUserRepository;
    private final PasswordEncoder passwordEncoder;

    private void loadSecurityData() {
        Authority admin = authorityRepository.save(Authority.builder().role("ADMIN").build());
        Authority userRole = authorityRepository.save(Authority.builder().role("USER").build());
        Authority customer = authorityRepository.save(Authority.builder().role("CUSTOMER").build());
        log.debug("Authorities Loaded: " + authorityRepository.count());

        beerBeerUserRepository.save(BeerUser.builder()
                .username("spring")
                .password(passwordEncoder.encode("guru"))
                .authority(admin)
                .build());

        beerBeerUserRepository.save(BeerUser.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .authority(userRole)
                .build());

        beerBeerUserRepository.save(BeerUser.builder()
                .username("scott")
                .password(passwordEncoder.encode("tiger"))
                .authority(customer)
                .build());

        log.debug("BeerUsers Loaded: " + beerBeerUserRepository.count());
    }

    @Override
    public void run(String... args) {
        if (authorityRepository.count() == 0) {
            loadSecurityData();
        }
    }


}
