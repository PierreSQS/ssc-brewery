package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jt on 6/13/20.
 */
@WebMvcTest
class BeerRestControllerIT extends BaseIT {

    @WithMockUser
    @Test
    void deleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311").with(csrf())
                        .header("Api-Key", "spring").header("Api-Secret", "guru"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void findBeers() throws Exception{
        mockMvc.perform(get("/api/v1/beer/")
                .header("Api-Key", "spring").header("Api-Secret", "guru"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception{
        mockMvc.perform(get("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                        .header("Api-Key", "spring").header("Api-Secret", "guru"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpc() throws Exception{
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036")
                        .header("Api-Key", "spring").header("Api-Secret", "guru"))
                .andExpect(status().isOk());
    }
}
