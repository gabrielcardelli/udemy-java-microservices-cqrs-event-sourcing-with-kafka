package com.techbank.account.query.infrastructure.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositEvent;
import com.techbank.account.common.events.FundsWithdrawnEvent;
import com.techbank.account.query.domain.AccountRepository;
import com.techbank.account.query.domain.BankAccount;

@Service
public class AccountEventHandler implements EventHandler {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public void on(AccountOpenedEvent event) {
		var bankAccount = BankAccount.builder().id(event.getId()).accountHolder(event.getAccountHolder())
				.creationDate(event.getCreatedDate()).accountType(event.getAccountType())
				.balanace(event.getOpeningBalance()).build();
		
		accountRepository.save(bankAccount);

	}

	@Override
	public void on(FundsDepositEvent event) {
		//var bankAccount = accountRepository.g
	}

	@Override
	public void on(FundsWithdrawnEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void on(AccountClosedEvent event) {
		// TODO Auto-generated method stub

	}

}
