package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepo;
    private final AuthorityRepository authRepo;

    public UserDataLoader(UserRepository userRepo, AuthorityRepository authRepo) {
        this.userRepo = userRepo;
        this.authRepo = authRepo;
    }


    @Override
    public void run(String... args) {
        if (authRepo.count()== 0 && userRepo.count()==0 ) {
            loadSecurityData();
            return;
        }

        log.info("No Data to load!!!!!");

    }

    private void loadSecurityData() {
        Authority adminAuth = Authority.builder().role("ADMIN").build();
        Authority userAuth = Authority.builder().role("USER").build();
        Authority customerAuth = Authority.builder().role("CUSTOMER").build();

        User user = User.builder()
                .username("user")
                .password("sha256}1296cefceb47413d3fb91ac7586a4625c33937b4d3109f5a4dd96c79c46193a029db713b96006ded")
                .authority(userAuth)
                .build();

        User admin = User.builder()
                .username("spring")
                .password("{bcrypt}$2a$10$7tYAvVL2/KwcQTcQywHIleKueg4ZK7y7d44hKyngjTwHCDlesxdla")
                .authority(userAuth)
                .authority(adminAuth)
                .build();

        User scott = User.builder()
                .username("scott")
                .password("{bcrypt10}$2a$10$jv7rEbL65k4Q3d/mqG5MLuLDLTlg5oKoq2QOOojfB3e2awo.nlmgu")
                .authority(userAuth)
                .authority(customerAuth)
                .build();

        log.info("Loading Authorities....");
        authRepo.saveAll(List.of(adminAuth, customerAuth,  userAuth));
        log.info("Loaded {} Authorities",authRepo.count());

        log.info("Loading Users....");
        userRepo.saveAll(List.of(user, scott, admin));
        log.info("Loaded {} Users",userRepo.count());
    }

}
