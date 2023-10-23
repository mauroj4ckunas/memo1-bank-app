package com.aninfo;

import com.aninfo.model.Account;
import com.aninfo.model.Deposit;
import com.aninfo.model.Extraction;
import com.aninfo.model.Transaction;
import com.aninfo.service.AccountService;
import com.aninfo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SpringBootApplication
@EnableSwagger2
public class Memo1BankApp {

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	public static void main(String[] args) {
		SpringApplication.run(Memo1BankApp.class, args);
	}

	@PostMapping("/accounts")
	@ResponseStatus(HttpStatus.CREATED)
	public Account createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}

	@GetMapping("/accounts")
	public Collection<Account> getAccounts() {
		return accountService.getAccounts();
	}

	@GetMapping("/accounts/{cbu}")
	public ResponseEntity<Account> getAccount(@PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);
		return ResponseEntity.of(accountOptional);
	}

	@PutMapping("/accounts/{cbu}")
	public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);

		if (!accountOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		account.setCbu(cbu);
		accountService.save(account);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/accounts/{cbu}")
	public void deleteAccount(@PathVariable Long cbu) {
		accountService.deleteById(cbu);
	}

	@PutMapping("/accounts/{cbu}/withdraw")
	public Account withdraw(@PathVariable Long cbu, @RequestParam Double sum) {
		return accountService.withdraw(cbu, sum);
	}

	@PutMapping("/accounts/{cbu}/deposit")
	public Account deposit(@PathVariable Long cbu, @RequestParam Double sum) {
		return accountService.deposit(cbu, sum);
	}


	@PostMapping("/transactions/deposit/{amount}")
	public Deposit createDeposit(@PathVariable double amount, @RequestParam Long cbu) {
		Account account = accountService.findAnAccountByCbu(cbu);
        return transactionService.createDeposit(amount, account);
	}

	@PostMapping("/transactions/extraction/{amount}")
	public Extraction createExtraction(@PathVariable double amount, @RequestParam Long cbu) {
		Account account = accountService.findAnAccountByCbu(cbu);
		return transactionService.createExtraction(amount, account);
	}

	@DeleteMapping("/transactions/delete/{id}")
	public void deleteTransaction(@PathVariable Long id) {
		transactionService.deleteTransaction(id);
	}

	@GetMapping("/transactions/{id}")
	public Transaction getTransactionById(@PathVariable Long id) {
		return transactionService.getTransaction(id);
	}

	@GetMapping("/transactions/all/{cbu}")
	public List<Transaction> getTransactionsByCbu(@PathVariable Long cbu) {
		return transactionService.getAllTransactionFromAccount(accountService.findAnAccountByCbu(cbu));
	}

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}
}
