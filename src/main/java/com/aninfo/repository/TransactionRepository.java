package com.aninfo.repository;

import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;

@RepositoryRestResource
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Transaction findByTransactionId(Long transactionId);
    ArrayList<Transaction> findAllByAccountTransaction(Account accountTransaction);
    @Override
    ArrayList<Transaction> findAll();

}
