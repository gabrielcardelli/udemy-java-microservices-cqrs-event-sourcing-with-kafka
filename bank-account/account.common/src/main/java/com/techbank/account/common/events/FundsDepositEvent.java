package com.techbank.account.common.events;

import com.techbank.cqrs.core.events.BaseEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
public class FundsDepositEvent  extends BaseEvent{
		
	private double amount;

}
