package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Pierrot on 7/23/21.
 */
@WebMvcTest
class BeerRestControllerIT extends BaseIT {

    final static String BASE_URL = "/api/v1/beer";

    @Test
    void listBeers() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get(BASE_URL+"/{beerId}", "b8d0977e-86d1-44d8-8f35-ae949940c3b3"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}