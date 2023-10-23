package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.DepositNullException;
import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Account;
import com.aninfo.model.Deposit;
import com.aninfo.model.Extraction;
import com.aninfo.model.Transaction;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public ArrayList<Transaction> getAllTransactionFromAccount(Account account) {
        return transactionRepository.findAllByAccountTransaction(account);
    }

    public Transaction getTransaction(Long id) {
        return transactionRepository.findByTransactionId(id);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    private void validateAmountDeposit(double amount) {
        if (amount < 0) throw new DepositNegativeSumException("Deposits with negative amounts are not allowed");
        if (amount == 0) throw new DepositNullException("Zero deposits are not allowed");
    }

    private double applyPromo(double amount) {
        if (amount >= 2000 && (0.10 * amount) <= 500) return amount + 0.10 * amount;
        return amount;
    }

    public Deposit createDeposit(double amount, Account account) {
        validateAmountDeposit(amount);
        Deposit deposit = new Deposit(applyPromo(amount), account);
        return transactionRepository.save(deposit);
    }

    public void validateAmountExtraction(double amount, Account account) {
        if (amount > account.getBalance()) throw new InvalidTransactionTypeException("Withdrawals that exceed the account total are not allowed");
    }

    public Extraction createExtraction(double amount, Account account) {
        validateAmountExtraction(amount, account);
        Extraction extraction = new Extraction(amount, account);
        return transactionRepository.save(extraction);
    }
}
