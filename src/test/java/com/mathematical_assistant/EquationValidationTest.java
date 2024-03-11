package com.mathematical_assistant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EquationValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void correctPolynomialValueValidatorTest() throws Exception{

        String jsonRequest = "{\"polynomial\": \"x^2*-4x+5=0\"}";

        mockMvc.perform(post("/equations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void correctRootsValueValidatorTest() throws Exception{

        String jsonRequest = "{\"polynomial\": \"x^2*-4x+5=0\",\"roots\": \"2.0; 1\"}";

        mockMvc.perform(post("/equations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());
    }


    @Test
    void inadmissibleCharsValidatorTest() throws Exception{

        String jsonRequest = "{\"polynomial\": \"xa^2*-4x+5=0\"}";

        mockMvc.perform(post("/equations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Inadmissible chars in polynomial!")));
    }

    @Test
    void parenthesesClosureValidatorTest() throws Exception{

        String jsonRequest = "{\"polynomial\": \"(x^2*-4x+5=0\"}";

        mockMvc.perform(post("/equations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Wrong polynomial structure! Check parentheses closure.")));
    }

    @Test
    void consecutiveSignsValidatorTest() throws Exception{

        String jsonRequest = "{\"polynomial\": \"x-^2*-4x+5=0\"}";

        mockMvc.perform(post("/equations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Wrong polynomial structure! Check the correctness of the sequence of signs.")));
    }

    @Test
    void multiplePointValidatorTest() throws Exception{

        String jsonRequest = "{\"polynomial\": \"x^2.0.0*-4x+5=0\"}";

        mockMvc.perform(post("/equations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Wrong polynomial structure! Check the multiple point in numbers.")));
    }

    @Test
    void multiplePointRootsValidatorTest() throws Exception{

        String jsonRequest = "{\"polynomial\": \"x^2*-4x+5=0\", \"roots\": \"2.0.2\"}";

        mockMvc.perform(post("/equations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Wrong roots structure! Check the multiple point in numbers.")));
    }
}
