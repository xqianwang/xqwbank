package com.example.xqwbank;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.spring.config.EnableAxonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.example.xqwbank.coreapi.CreateAccountCommand;
import com.example.xqwbank.coreapi.WithdrawMoneyCommand;

@EnableAxonAutoConfiguration
@SpringBootApplication
public class XqwbankApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext config = SpringApplication.run(XqwbankApplication.class, args);
		CommandBus commandBus = config.getBean(CommandBus.class);
		
		commandBus.dispatch(asCommandMessage(new CreateAccountCommand("2222", 3000)), new CommandCallback<C, R>() {
			@Override
			public void onSuccess() {
				
			} 
		});
		commandBus.dispatch(asCommandMessage(new WithdrawMoneyCommand("2222", 1000)));
	}
	
	@Bean
	public EventStorageEngine eventStorageEngine() {
		return new InMemoryEventStorageEngine();
	}
	
	
	@Bean
	public CommandBus commandBus() {
		return new AsynchronousCommandBus();
	}
}
