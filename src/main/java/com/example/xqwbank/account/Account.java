package com.example.xqwbank.account;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;

import com.example.xqwbank.coreapi.AccountCreatedEvent;
import com.example.xqwbank.coreapi.CreateAccountCommand;
import com.example.xqwbank.coreapi.MoneyWithdrawnEvent;
import com.example.xqwbank.coreapi.WithdrawMoneyCommand;
import com.example.xqwbank.exception.OverdraftLimitExceededException;

import lombok.NoArgsConstructor;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@NoArgsConstructor
public class Account {
	
	@AggregateIdentifier
	private String accountId;
	
	private int balance;
	private int overdraftLimit;
	
	@CommandHandler
	public Account(CreateAccountCommand cmd) {
		apply(new AccountCreatedEvent(cmd.getAccountId(), cmd.getOverdraftLimit()));
	}
	
	@CommandHandler
	public void handle(WithdrawMoneyCommand cmd) throws OverdraftLimitExceededException {
		if (balance + overdraftLimit >= cmd.getAmount()) {
			apply(new MoneyWithdrawnEvent(accountId, cmd.getAmount(), balance - cmd.getAmount()));
		} else {
			throw new OverdraftLimitExceededException();
		}
		
	}
	
	
	@EventSourcingHandler
	public void on(MoneyWithdrawnEvent event) {
		this.balance = event.getBalance();
	}
	
	@EventSourcingHandler
	public void on(AccountCreatedEvent event) {
		this.accountId = event.getAccountId();
		this.overdraftLimit = event.getOverdraftLimit();
	}
}
