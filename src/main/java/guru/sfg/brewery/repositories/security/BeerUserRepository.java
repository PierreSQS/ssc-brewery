package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.BeerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by jt on 6/21/20.
 */
public interface BeerUserRepository extends JpaRepository<BeerUser, Integer> {
    Optional<BeerUser> findByUsername(String username);
}
