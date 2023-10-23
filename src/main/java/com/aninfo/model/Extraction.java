package com.aninfo.model;

import javax.persistence.Entity;

@Entity
public class Extraction extends Transaction{

    public Extraction(double amount, Account account) {
        super(amount, account);
    }

    public Extraction() {

    }

    @Override
    public String getType() {
        return "Extraction";
    }
}
