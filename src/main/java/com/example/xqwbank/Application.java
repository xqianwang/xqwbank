package com.example.xqwbank;

import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

import com.example.xqwbank.account.Account;
import com.example.xqwbank.coreapi.CreateAccountCommand;
import com.example.xqwbank.coreapi.WithdrawMoneyCommand;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

public class Application {

	public static void main(String[] args) {
		Configuration config = DefaultConfigurer.defaultConfiguration()
								.configureAggregate(Account.class)
								.configureEmbeddedEventStore(c -> new InMemoryEventStorageEngine())
								.buildConfiguration();
		config.start();
		config.commandBus().dispatch(asCommandMessage(new CreateAccountCommand("2222", 3000)));
		config.commandBus().dispatch(asCommandMessage(new WithdrawMoneyCommand("2222", 1000)));
	}
}
