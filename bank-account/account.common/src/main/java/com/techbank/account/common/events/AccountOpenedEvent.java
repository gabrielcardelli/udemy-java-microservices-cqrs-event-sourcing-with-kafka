package com.techbank.account.common.events;

import java.util.Date;

import com.techbank.account.common.dto.AccountType;
import com.techbank.cqrs.core.events.BaseEvent;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountOpenedEvent extends BaseEvent {
	private String accountHolder;

	private AccountType accountType;

	private Date createdDate;
	private double openingBalance;
	public AccountOpenedEvent(String id, String accountHolder, AccountType accountType, Date createdDate, double openingBalance) {
		super(id);
		this.accountHolder = accountHolder;
		this.accountType = accountType;
		this.createdDate = createdDate;
		this.openingBalance = openingBalance;
	}
	
	
}
