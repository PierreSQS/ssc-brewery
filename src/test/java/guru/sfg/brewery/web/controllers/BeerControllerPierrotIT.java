package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Pierrot on 9/11/21.
 */
@WebMvcTest(BeerController.class)
class BeerControllerPierrotIT {

    @MockBean
    BeerRepository beerRepoMock;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findBeers() throws Exception {
        mockMvc.perform(get("/beers/find"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("beer"))
                .andExpect(view().name("beers/findBeers"))
                .andDo(print());
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/beers/new").with(httpBasic("user","password")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("beer"))
                .andExpect(view().name("beers/createBeer"))
                .andDo(print());
    }

    // Anonymous doesn't work here!!
    // Because the path is not authorized with authentication ("/beers/new)!!!!!!!!!!
    @Test
    void initCreationFormWithAnonymousNotAuthorized() throws Exception {
        mockMvc.perform(get("/beers/new").with(anonymous()))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}