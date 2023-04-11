package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Modified by Pierrot on 2023-01-22.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import({H2ConsoleProperties.class})
public abstract class BaseIT {

    @Autowired
    protected MockMvc mockMvc;


    @BeforeEach
    public void setup() {

    }
}
