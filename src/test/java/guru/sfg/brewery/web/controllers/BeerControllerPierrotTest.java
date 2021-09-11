package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Created by Pierrot on 9/10/21.
 */
@WebMvcTest(BeerController.class)
class BeerControllerPierrotTest {

    @MockBean
    BeerRepository beerRepoMock;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser("spring")
    void showBeer() throws Exception{
        // Given
        UUID beerID = UUID.fromString("5b97a2b1-3f29-4424-a2a1-63ca67077a4e");
        Beer beerMock =  Beer.builder().id(beerID)
                                        .beerName("33 Export")
                                        .beerStyle(BeerStyleEnum.PORTER)
                                        .build();

        when(beerRepoMock.findById(beerID)).thenReturn(Optional.of(beerMock));


        // When and Then
        mockMvc.perform(get("/beers/{beerID}", UUID.fromString("5b97a2b1-3f29-4424-a2a1-63ca67077a4e")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("beer"))
                .andExpect(view().name("beers/beerDetails"))
                .andExpect(content().string(containsString("<title>SFG Beer Works</title>")))
                .andDo(print());
    }

    @Test
    void processCreationForm() {
    }
}