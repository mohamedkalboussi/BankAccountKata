package bank;

import bank.account.Account;
import bank.account.Client;
import bank.transaction.Transaction;
import bank.transaction.TransactionType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class BankTest {

    private static final int INITIAL_BALANCE = 100;

    private Bank bank;

    private Client client = mock(Client.class);

    private Account account = mock(Account.class);;

    @Before
    public void init() {
        bank = new Bank();
    }

    @Test
    public void should_create_a_new_account() {

        // Given
        final int emptyInitialBalance = 0;

        // When
        bank.createAccount(client, emptyInitialBalance);

        // Then
        assertEquals(1, bank.getAccounts().size());
    }

    @Test
    public void should_create_a_new_account_with_initial_balance() {

        // When
        final Account account = bank.createAccount(client, INITIAL_BALANCE);

        // Then
        assertEquals(INITIAL_BALANCE, bank.calculateBalance(account));
    }

    @Test
    public void should_add_an_initial_deposit_transaction_when_creating_a_new_account() {

        // When
        final Account account = bank.createAccount(client, INITIAL_BALANCE);

        // Then
        assertEquals(1, account.getTransactions().size());
        assertEquals(TransactionType.DEPOSIT, account.getTransactions().get(0).getType());
    }

    @Test
    public void should_deposit_amount() {

        // Given
        final Account account = bank.createAccount(client, INITIAL_BALANCE);

        // When
        bank.deposit(account, 60);

        // Then
        assertEquals(2, account.getTransactions().size());
        assertEquals(TransactionType.DEPOSIT, account.getTransactions().get(0).getType());
        assertEquals(TransactionType.DEPOSIT, account.getTransactions().get(1).getType());
    }

    @Test
    public void should_withdraw_amount() {

        // Given
        final Account account = bank.createAccount(client, INITIAL_BALANCE);

        // When
        bank.withdraw(account, 60);

        // Then
        assertEquals(2, account.getTransactions().size());
        assertEquals(TransactionType.DEPOSIT, account.getTransactions().get(0).getType());
        assertEquals(TransactionType.WITHDRAWAL, account.getTransactions().get(1).getType());
    }

    @Test
    public void should_get_transactions() {

        // Given
        final Account account = bank.createAccount(client, INITIAL_BALANCE);
        bank.withdraw(account, 60);

        // When
        final List<Transaction> transactions = bank.getTransactionsHistory(account);

        // Then
        assertEquals(2, transactions.size());

        final Transaction firstTransaction = transactions.get(0);
        assertEquals(TransactionType.DEPOSIT, firstTransaction.getType());
        assertEquals(INITIAL_BALANCE, firstTransaction.getAmount());
        assertThat(firstTransaction.getDate()).isNotNull();

        final Transaction secondTransaction = transactions.get(1);
        assertEquals(TransactionType.WITHDRAWAL, secondTransaction.getType());
        assertEquals(60, secondTransaction.getAmount());
        assertThat(secondTransaction.getDate()).isNotNull();
    }

    @Test
    public void should_calculate_balance_after_one_deposit_transaction() {

        // Given
        final Account account = bank.createAccount(client, INITIAL_BALANCE);

        //When
        bank.deposit(account, 60);

        // Then
        final int expectedBalance = bank.calculateBalance(account);
        assertEquals(160, expectedBalance);
    }

    @Test
    public void
    should_calculate_balance_after_one_withdrawal_transaction() {

        // Given
        final Account account = bank.createAccount(client, INITIAL_BALANCE);

        // When
        bank.withdraw(account, 60);

        // Then
        final int expectedBalance = bank.calculateBalance(account);
        assertEquals(40, expectedBalance);
    }

    @Test
    public void should_calculate_balance_after_multiples_transactions() {

        // Given
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(TransactionType.DEPOSIT, INITIAL_BALANCE));
        transactions.add(new Transaction(TransactionType.WITHDRAWAL, 50));
        transactions.add(new Transaction(TransactionType.WITHDRAWAL, 30));
        transactions.add(new Transaction(TransactionType.DEPOSIT, 100));
        transactions.add(new Transaction(TransactionType.DEPOSIT, 150));
        transactions.add(new Transaction(TransactionType.WITHDRAWAL, 200));

        doReturn(transactions).when(account).getTransactions();

        // When
        final int actualBalance = bank.calculateBalance(account);

        // Then
        assertEquals(70, actualBalance);
    } 

}
