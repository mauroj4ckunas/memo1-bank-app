package com.aninfo.model;

import com.aninfo.model.Account;

import javax.persistence.*;

@Entity
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;
    @ManyToOne
    private Account accountTransaction;
    private double amount;

    public Transaction(double amount, Account account) {
        this.amount = amount;
        this.accountTransaction = account;
    }

    public Transaction() {}

    public Account getAccountTransaction() {
        return accountTransaction;
    }

    public void setAccountTransaction(Account accountTransaction) {
        this.accountTransaction = accountTransaction;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public abstract String getType();

}
