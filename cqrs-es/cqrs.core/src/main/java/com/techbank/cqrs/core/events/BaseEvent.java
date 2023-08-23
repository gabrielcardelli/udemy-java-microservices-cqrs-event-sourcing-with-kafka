package com.techbank.cqrs.core.events;

import com.techbank.cqrs.core.messages.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEvent extends Message{
 
	public BaseEvent(String id){
		super(id);
	}
	
	private int version;
	
}
