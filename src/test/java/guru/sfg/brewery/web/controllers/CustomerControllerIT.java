package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Pierrot on 4/22/23.
 */
@SpringBootTest
class CustomerControllerIT extends BaseIT {

    @DisplayName("List Customers")
    @Nested
    class ListCustomers {
        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("guru.sfg.brewery.web.controllers.BaseIT#getStreamAdminCustomer")
        void testListCustomersAUTH(String user, String pwd) throws Exception {
            mockMvc.perform(get("/customers")
                            .param("customerName",user).with(httpBasic(user,pwd)))
                    .andExpect(status().is3xxRedirection())
                    .andDo(print());
        }

    }
    @DisplayName("Add Customers")
    @Nested
    class AddCustomers {

        @Test
        void testProcessCreationForm() throws Exception {
            mockMvc.perform(post("/customers/new")
                            .param("testCust","testPWD")
                            .with(httpBasic("spring","guru")))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("guru.sfg.brewery.web.controllers.BaseIT#getStreamNotAdmin")
        void testProcessCreationFormNotAuthorized(String user, String pwd) throws Exception {
            mockMvc.perform(post("/customers/new")
                            .param("customerName",user).with(httpBasic(user,pwd)))
                    .andExpect(status().isForbidden())
                    .andDo(print());
        }

    }
}