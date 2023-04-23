package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

/**
 * Modified by Pierrot on 4/22/23.
 */
@AutoConfigureMockMvc
@Import(H2ConsoleProperties.class)
public abstract class BaseIT {

    @Autowired
    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() {

    }

    public static Stream<Arguments> getStreamAdminCustomer() {
        return Stream.of(Arguments.of("spring" , "guru"),
                Arguments.of("scott", "tiger"));
    }

    public static Stream<Arguments> getStreamAllUsers() {
        return Stream.of(Arguments.of("spring" , "guru"),
                Arguments.of("scott", "tiger"),
                Arguments.of("user", "password"));
    }

    public static Stream<Arguments> getStreamNotAdmin() {
        return Stream.of(Arguments.of("scott", "tiger"),
                Arguments.of("user", "password"));
    }
}
