package com.techbank.account.cmd.domain;

import java.util.Date;

import com.techbank.account.cmd.api.commands.OpenAccountCommand;
import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositEvent;
import com.techbank.account.common.events.FundsWithdrawnEvent;
import com.techbank.cqrs.core.domain.AggregateRoot;

import lombok.Getter;

public class AccountAggregate  extends AggregateRoot{

	@Getter
	private Boolean active;
	
	@Getter
	private double balance;
	
	public AccountAggregate() {
	}
	
	public AccountAggregate(OpenAccountCommand command) {
		raiseEvent(new AccountOpenedEvent(command.getId(),command.getAccountHolder(),command.getAccountType(),new Date(),command.getOpeningBalance()));
	}
	
	public void apply(AccountOpenedEvent event) {
		this.id = event.getId();
		this.active = true;
		this.balance = event.getOpeningBalance();
	}
	
	public void depositFunds(double amount) {
		if(!this.active) {
			throw new IllegalStateException("Funds cannot be deposited into a closed account!");
		}
		
		if(amount <=0) {
			throw new IllegalStateException("The deposit amount must be greater than 0!");
		};
		
		raiseEvent(new FundsDepositEvent(this.id,amount));
		
	}
	
	public void apply(FundsDepositEvent event) {
		this.id = event.getId();
		this.balance += event.getAmount();
	}
	
	
	public void withdrawFunds(double amount) {
		if(!this.active) {
			throw new IllegalStateException("Funds cannot be withdrawn into a closed account!");
		}
		
		raiseEvent(new FundsWithdrawnEvent(this.id,amount));
		
	}
	
	public void apply(FundsWithdrawnEvent event) {
		this.id = event.getId();
		this.balance -= event.getAmount();
	}
	
	public void closeAccount() {
		if(!this.active) {
			throw new IllegalStateException("The bank account has already been closed!");
		}
		
		raiseEvent(new AccountClosedEvent(this.id));
	}
	
	public void apply(AccountClosedEvent event) {
		this.id = event.getId();
		this.active = false;
	}
}
