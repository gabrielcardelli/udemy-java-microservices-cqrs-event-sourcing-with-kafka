package com.techbank.account.common.events;

import com.techbank.cqrs.core.events.BaseEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
public class FundsWithdrawnEvent  extends BaseEvent{
		
	
	
	public FundsWithdrawnEvent(String id, double amount) {
		super(id);
		this.amount = amount;
	}

	private double amount;

}
