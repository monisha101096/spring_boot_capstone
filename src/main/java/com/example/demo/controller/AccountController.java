package com.example.demo.controller;

import java.util.List;

//import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.DuplicateException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;

@RestController
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;

	@GetMapping("/accounts/all")
	public List<Account> getAllAccounts() {
		return (List<Account>) accountRepository.findAll();
	}

	@GetMapping("/accounts/{id}")
	public Account getAccountById(@PathVariable(value = "id") Long accountId) {
		List<Account> accounts = (List<Account>) accountRepository.findAll();
		for (Account acc : accounts) {
			if(acc.getAccountNumber().equals(accountId)) {
				return acc;
			}
			else {
				new ResourceNotFoundException("Account", "id", accountId);
			}
		}
		return null;
	}
	
	@PostMapping("/accounts/new")
    public Account createAccount(@Validated @RequestBody Account account) {
		List<Account> accounts = (List<Account>) accountRepository.findAll();
		for (Account acc : accounts) {
			if(acc.getAccountNumber().equals(account.getAccountNumber())) {
				throw new DuplicateException("Account", "account", account.getAccountNumber());
			}
		}
			
        return accountRepository.save(account);
    }

	@PutMapping("/accounts/{id}")
	public Account updateAmount(@PathVariable(value = "id") Long accountId, @RequestParam("toAcc") Long toAcc, @RequestParam("amount") double amount, @RequestParam("transaction") String transaction) {

		Account fromAcc = null;
		Account toAccount = null;
		List<Account> accounts = (List<Account>) accountRepository.findAll();
		for (Account acc : accounts) {
			if(acc.getAccountNumber().equals(accountId)) {
				fromAcc = acc;
			}
			if(acc.getAccountNumber().equals(toAcc)) {
				toAccount = acc;
			}
		}

//		Account account = accountRepository.findById(accountId)
//				.orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));

		if(transaction.equalsIgnoreCase("credit")) {
			if(toAccount.getBalance() >= amount) {
				fromAcc.setBalance(fromAcc.getBalance() + amount);
				toAccount.setBalance(fromAcc.getBalance() - amount);
			}
			else {
				System.out.println("Insufficient amount in beneficiary account");
				return null;
			}			
		}
		else if(transaction.equalsIgnoreCase("debit")) {
			if(fromAcc.getBalance() < amount ) {
				System.out.println("Insufficient amount");
				return null;
			}
			else {
				System.out.println();
				fromAcc.setBalance(fromAcc.getBalance() - amount);
				toAccount.setBalance(toAccount.getBalance() + amount);
			}
		}

		Account updatedAccount = accountRepository.save(fromAcc);
		Account updatedAccount2 = accountRepository.save(toAccount);
		return updatedAccount;
	}
	
	@DeleteMapping("/accounts/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable(value = "id") Long accountId) {
		List<Account> accounts = (List<Account>) accountRepository.findAll();
		for (Account acc : accounts) {
			if(acc.getAccountNumber().equals(accountId)) {
				accountRepository.delete(acc);
			    return ResponseEntity.ok().build();
			}
		}
	    
	    return null;
	}
	
	@DeleteMapping("/accounts/all")
	public ResponseEntity<?> deleteAllAccounts() {
		accountRepository.deleteAll();
		return ResponseEntity.ok().build();
	}

}