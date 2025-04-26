package io.plagov.spend_parser.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import io.plagov.spend_parser.models.Transaction;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class CsvToBeanParser {

    public List<Transaction> parseIntoBeans() throws IOException {
        var resource = new ClassPathResource("budget.csv");

        return new CsvToBeanBuilder<Transaction>(new InputStreamReader(resource.getInputStream()))
                .withType(Transaction.class)
                .build().parse();
    }
}
