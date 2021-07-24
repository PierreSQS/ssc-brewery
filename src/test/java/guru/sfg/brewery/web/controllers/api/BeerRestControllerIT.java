package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;
import guru.sfg.brewery.web.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * modified by Pierrot on 7/24/21.
 */
@WebMvcTest
class BeerRestControllerIT extends BaseIT {

    public final static String BASE_URl = "/api/v1/beer";

    @Test
    void findBeers() throws Exception{
        mockMvc.perform(get(BASE_URl))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception{
        mockMvc.perform(get(BASE_URl+"/97df0c39-90c4-4ae0-b663-453e8e19c311"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpc() throws Exception{
        // Given
        BeerDto beerDtoMock = BeerDto.builder()
                .beerName("33 Export")
                .upc("0631234200036")
                .createdDate(OffsetDateTime.now())
                .build();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

        when(beerService.findBeerByUpc(anyString())).thenReturn(beerDtoMock);


        mockMvc.perform(get("/api/v1/beerUpc/{upc}",beerDtoMock.getUpc()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.createdDate")
                        .value(equalTo(beerDtoMock.getCreatedDate().format(dateTimeFormatter))))
                .andDo(print());

    }


}
