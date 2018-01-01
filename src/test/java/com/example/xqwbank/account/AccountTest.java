package com.example.xqwbank.account;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import com.example.xqwbank.coreapi.AccountCreatedEvent;
import com.example.xqwbank.coreapi.CreateAccountCommand;
import com.example.xqwbank.coreapi.MoneyWithdrawnEvent;
import com.example.xqwbank.coreapi.WithdrawMoneyCommand;
import com.example.xqwbank.exception.OverdraftLimitExceededException;

public class AccountTest {
	private FixtureConfiguration<Account> fixture;
	
	@Before
	public void setup() throws Exception {
		fixture = Fixtures.newGivenWhenThenFixture(Account.class);
		
	}
	
	@Test
	public void testCreateAccount() throws Exception {
		fixture.givenNoPriorActivity()
				.when(new CreateAccountCommand("1234", 1000))
				.expectEvents(new AccountCreatedEvent("1234", 1000));
	}
	
	@Test
	public void testWithdrawReasonableAmount() {
		fixture.given(new AccountCreatedEvent("1234", 1000))
				.when(new WithdrawMoneyCommand("1234", 600))
				.expectEvents(new MoneyWithdrawnEvent("1234", 600, -600));
	}
	
	@Test
	public void testWithdrawAbsurdAmount() {
		fixture.given(new AccountCreatedEvent("1234", 1000))
				.when(new WithdrawMoneyCommand("1234", 1001))
				.expectNoEvents()
				.expectException(OverdraftLimitExceededException.class);
	}
}
