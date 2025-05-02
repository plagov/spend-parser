package io.plagov.spend_parser.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import io.plagov.spend_parser.models.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class CsvToBeanParser {

    public List<Transaction> parseIntoBeans(MultipartFile file) throws IOException {
        return new CsvToBeanBuilder<Transaction>(new InputStreamReader(file.getInputStream()))
                .withType(Transaction.class)
                .build().parse();
    }
}
