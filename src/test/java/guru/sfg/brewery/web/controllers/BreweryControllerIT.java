package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Modified by Pierrot on 2023-04-10.
 */
class BreweryControllerIT extends BaseIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void listBreweriesCUSTOMERRole() throws Exception {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("scott","tiger")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>My Breweries!</h1>")))
                .andDo(print());
    }
    @Test
    void listBreweriesUSERRole() throws Exception {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("user","password")))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
    @Test
    void listBreweriesADMINRole() throws Exception {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("spring","guru")))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    void listBreweriesUnauthenticated() throws Exception {
        mockMvc.perform(get("/brewery/breweries"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void getBreweriesJsonCUSTOMERRole() throws Exception  {
        mockMvc.perform(get("/brewery/api/v1/breweries").with(httpBasic("scott","tiger")))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    void getBreweriesJsonUSERRole() throws Exception  {
        mockMvc.perform(get("/brewery/api/v1/breweries").with(httpBasic("user","password")))
                .andExpect(status().isForbidden())
                .andDo(print());
    }    @Test
    void getBreweriesJsonADMINRole() throws Exception  {
        mockMvc.perform(get("/brewery/api/v1/breweries").with(httpBasic("spring","guru")))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    void getBreweriesJsonUnauthenticated() throws Exception  {
        mockMvc.perform(get("/brewery/api/v1/breweries"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

}