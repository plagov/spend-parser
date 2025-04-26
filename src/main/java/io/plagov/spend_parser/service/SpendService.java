package io.plagov.spend_parser.service;

import io.plagov.spend_parser.models.Transaction;
import io.plagov.spend_parser.parser.CsvToBeanParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SpendService {

    private final CsvToBeanParser parser;

    private final List<String> GROCERIES_MERCHANTS = List.of(
            "rimi",
            "selver",
            "konsum",
            "maxima",
            "prisma",
            "lidl",
            "netomarket",
            "promo",
            "grossi");

    public SpendService(CsvToBeanParser parser) {
        this.parser = parser;
    }

    public String calculateSpends() throws IOException {
        var transactions = parser.parseIntoBeans();
        var groceries = calculateSumOfGroceries(transactions);
        return "kek";
    }

    private double calculateSumOfGroceries(List<Transaction> transactions) {
        return transactions.stream()
                .filter(this::isGrocery)
                .mapToDouble(transaction -> Math.abs(transaction.getAmount()))
                .sum();
    }

    private boolean isGrocery(Transaction transaction) {
        var merchant = transaction.getMerchant().toLowerCase();
        return GROCERIES_MERCHANTS.stream().anyMatch(merchant::contains);
    }
}
