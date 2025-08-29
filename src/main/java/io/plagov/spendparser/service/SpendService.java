package io.plagov.spendparser.service;

import io.plagov.spendparser.models.TotalSpend;
import io.plagov.spendparser.models.Transaction;
import io.plagov.spendparser.parser.CsvToBeanParser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
            "grossi",
            "a&o kauplus",
            "marjam marja",
            "bigfruit");

    private final static List<String> PHARMACIES_MERCHANTS = List.of(
            "tervisekeskus",
            "apte");

    public SpendService(CsvToBeanParser parser) {
        this.parser = parser;
    }

    public TotalSpend calculateSpends(MultipartFile file) throws IOException {
        var transactions = parser.parseIntoBeans(file);
        var groceries = calculateSumOfGroceries(transactions);
        var pharmacy = calculateSumOfPharmacy(transactions);
        return new TotalSpend(Math.ceil(groceries), Math.ceil(pharmacy));
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
