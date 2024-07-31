package soon.PTCMR_Back;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
class PTCMRBackApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void requestDateFormat() throws Exception {
        // given
        String url = "/product";

        // when
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"name\":  \"name\", \"expirationDate\": \"2018-12-15T10:00:00\", \"quantity\":  3, \"imageUrl\":  \"url\","
                        + " \"storageType\": \"FROZEN\", \"repurchase\":  \"true\", \"description\":  \"de\", \"teamId\":  \"1\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("success"))
            .andDo(MockMvcResultHandlers.print());
    }

}