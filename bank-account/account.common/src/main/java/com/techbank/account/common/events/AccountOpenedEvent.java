package com.techbank.account.common.events;

import java.util.Date;

import com.techbank.account.common.dto.AccountType;
import com.techbank.cqrs.core.events.BaseEvent;

import lombok.Data;

@Data
public class AccountOpenedEvent extends BaseEvent {
	private String accountHolder;

	private AccountType accountType;

	private Date createdDate;
	private double openingBalance;
}
