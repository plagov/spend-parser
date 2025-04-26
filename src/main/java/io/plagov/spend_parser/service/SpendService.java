package io.plagov.spend_parser.service;

import io.plagov.spend_parser.models.TotalSpend;
import io.plagov.spend_parser.models.Transaction;
import io.plagov.spend_parser.parser.CsvToBeanParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SpendService {

    private final CsvToBeanParser parser;

    private final static List<String> GROCERIES_MERCHANTS = List.of(
            "rimi",
            "selver",
            "konsum",
            "maxima",
            "prisma",
            "lidl",
            "netomarket",
            "promo",
            "grossi");

    private final static List<String> PHARMACIES_MERCHANTS = List.of(
            "tervisekeskus",
            "apte");

    public SpendService(CsvToBeanParser parser) {
        this.parser = parser;
    }

    public TotalSpend calculateSpends() throws IOException {
        var transactions = parser.parseIntoBeans();
        var groceries = calculateSumOfGroceries(transactions);
        var pharmacy = calculateSumOfPharmacy(transactions);
        return new TotalSpend(groceries, pharmacy);
    }

    private boolean isPharmacy(Transaction transaction) {
        var merchant = transaction.getMerchant().toLowerCase();
        return PHARMACIES_MERCHANTS.stream().anyMatch(merchant::contains);
    }

    private double calculateSumOfPharmacy(List<Transaction> transactions) {
        return transactions.stream()
                .filter(this::isPharmacy)
                .mapToDouble(transaction -> Math.abs(transaction.getAmount()))
                .sum();
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
