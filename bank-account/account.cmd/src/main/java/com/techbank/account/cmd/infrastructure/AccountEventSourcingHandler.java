package com.techbank.account.cmd.infrastructure;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techbank.account.cmd.domain.AccountAggregate;
import com.techbank.cqrs.core.domain.AggregateRoot;
import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.handlers.EventSourcingHandler;
import com.techbank.cqrs.core.infrastructure.EventStore;
import com.techbank.cqrs.core.producers.EventProducer;

import lombok.experimental.var;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

	@Autowired
	EventProducer eventProducer;
	
	@Autowired
	private EventStore eventStore;
	
	@Override
	public void save(AggregateRoot aggregate) {
		eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
		aggregate.markChangesAsCommitted();
	}

	@Override
	public AccountAggregate getById(String id) {
		AccountAggregate aggregate = new AccountAggregate();
		
		List<BaseEvent> events = eventStore.getEvents(id);
		
		if(events != null && !events.isEmpty()) {
			aggregate.replayEvents(events);
			Optional<Integer> latestVersion = events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
			
			aggregate.setVersion(latestVersion.get());
			
		}

		return aggregate;
		
	}

	@Override
	public void republishEvents() {
		var aggregateIds = eventStore.getAggregateIds();
		
		for(var aggregateId : aggregateIds) {
			var aggregate =  getById(aggregateId);
			if(aggregate == null || !aggregate.getActive()) {
				continue;
			}else {
				var events = eventStore.getEvents(aggregateId);
				for(var event : events) {
					eventProducer.produce(event.getClass().getSimpleName(),event);
				}
			}
		}
	}

}
