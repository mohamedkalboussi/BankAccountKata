package bank.account;

import bank.transaction.Transaction;
import bank.transaction.TransactionType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class AccountTest {

    private Client client = mock(Client.class);

    private Account account;

    @Before
    public void initialise() {
        account = new Account(client);
    }

    @Test
    public void should_save_deposit_transaction() {

        // When
        account.addTransaction(new Transaction(TransactionType.DEPOSIT, 100));

        // Then
        assertEquals(1, account.getTransactions().size());
    }

    @Test
    public void should_save_withdrawal_transaction() {

        // When
        account.addTransaction(new Transaction(TransactionType.WITHDRAWAL, 100));

        // Then
        assertEquals(1, account.getTransactions().size());
    }

    @Test
    public void should_get_transactions_history() {

        // When
        account.addTransaction(new Transaction(TransactionType.WITHDRAWAL, 10));
        account.addTransaction(new Transaction(TransactionType.DEPOSIT, 20));
        account.addTransaction(new Transaction(TransactionType.WITHDRAWAL, 30));
        account.addTransaction(new Transaction(TransactionType.DEPOSIT, 70));

        // Then
        assertEquals(4, account.getTransactions().size());
    }

}
