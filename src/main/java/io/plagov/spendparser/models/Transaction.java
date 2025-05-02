package io.plagov.spendparser.models;

import com.opencsv.bean.CsvBindByName;

public class Transaction {

    @CsvBindByName(column = "Имя плательщика/получателя")
    private String merchant;

    @CsvBindByName(column = "Сумма")
    private double amount;

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
