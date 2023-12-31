package com.techbank.account.common.events;

import java.util.Date;

import com.techbank.account.common.dto.AccountType;
import com.techbank.cqrs.core.events.BaseEvent;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountClosedEvent extends BaseEvent {
	
	public AccountClosedEvent(String id){
		super(id);
	}

}
