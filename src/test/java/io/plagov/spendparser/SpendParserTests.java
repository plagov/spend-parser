package io.plagov.spendparser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@AutoConfigureMockMvc
@SpringBootTest
class SpendParserTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCalculateSpends() throws Exception {
        var fileContent = Files.readAllBytes(Paths.get("src/test/resources/files/budget.csv"));

        mockMvc.perform(multipart("/upload").file("file", fileContent))
                .andExpect(status().isOk())
                .andExpect(view().name("total"))
                .andExpect(xpath("//span[@data-testid='groceries-total']").string("€160"))
                .andExpect(xpath("//span[@data-testid='pharmacy-total']").string("€96"));
    }
}
