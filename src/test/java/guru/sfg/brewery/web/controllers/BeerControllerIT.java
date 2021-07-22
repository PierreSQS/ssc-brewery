package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.repositories.BeerInventoryRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.services.BeerService;
import guru.sfg.brewery.services.BreweryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Pierrot on 7/22/21.
 */
@WebMvcTest
class BeerControllerIT {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BeerRepository beerRepoMock;

    @MockBean
    BeerInventoryRepository beerInventoryRepoMock;

    @MockBean
    BreweryService brewerySrvMock;

    @MockBean
    CustomerRepository customerRepoMock;

    @MockBean
    BeerService beerSrvMock;


    @BeforeEach
    void setUp() {

    }

    @WithMockUser("spring")
    @Test
    void findBeers() throws Exception{
        mockMvc.perform(get("/beers/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"))
                .andExpect(content().string(containsString("<title>SFG Beer Works</title>")))
                .andDo(print());
    }

}