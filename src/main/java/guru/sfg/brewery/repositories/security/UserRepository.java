package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.JTUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by jt on 6/21/20.
 */
public interface UserRepository extends JpaRepository<JTUser, Integer> {
    Optional<JTUser> findByUsername(String username);
}
