package guru.sfg.brewery.domain.security;

import lombok.*;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Modified by Pierrot on 5/2/23.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String permission;

    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;
}
