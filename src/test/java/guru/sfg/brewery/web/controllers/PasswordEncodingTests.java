package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Modified by Pierrot on 2/7/22.
 */
class PasswordEncodingTests {

    static final String USER_PASSWORD = "password";
    static final String ADMIN_PASSWORD = "guru";
    static final String TIGER_PASSWORD = "tiger";

    @Test
    void testBcrypt() {
        PasswordEncoder bcrypt = new BCryptPasswordEncoder();

        System.out.println(bcrypt.encode(USER_PASSWORD));
        System.out.println(bcrypt.encode(USER_PASSWORD));

    }

    @Test
    void testSha256() {
        PasswordEncoder sha256 = new StandardPasswordEncoder();

        System.out.println(sha256.encode(USER_PASSWORD));
        System.out.println(sha256.encode(USER_PASSWORD));
        System.out.println(sha256.encode(TIGER_PASSWORD));
    }

    @Test
    void testLdap() {
        PasswordEncoder ldap = new LdapShaPasswordEncoder();
        System.out.println(ldap.encode(USER_PASSWORD));
        System.out.println(ldap.encode(USER_PASSWORD));
        System.out.println(ldap.encode(ADMIN_PASSWORD));

        String encodedPwd = ldap.encode(USER_PASSWORD);

        assertTrue(ldap.matches(USER_PASSWORD, encodedPwd ));

    }

    @Test
    void testNoOp() {
        PasswordEncoder noOp = NoOpPasswordEncoder.getInstance();

        System.out.println(noOp.encode(USER_PASSWORD));
    }

    @Test
    void hashingExample() {
        System.out.println(DigestUtils.md5DigestAsHex(USER_PASSWORD.getBytes()));
        System.out.println(DigestUtils.md5DigestAsHex(USER_PASSWORD.getBytes()));

        String salted = USER_PASSWORD + "ThisIsMySALTVALUE";
        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes()));
    }
}
