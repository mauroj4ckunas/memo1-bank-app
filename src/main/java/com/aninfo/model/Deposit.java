package com.aninfo.model;

import javax.persistence.Entity;

@Entity
public class Deposit extends Transaction{

    public Deposit(double amount, Account account) {
        super(amount, account);
    }

    public Deposit() {

    }

    @Override
    public String getType() {
        return "Deposit";
    }
}
