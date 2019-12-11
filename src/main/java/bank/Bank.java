package bank;

import bank.account.Account;
import bank.account.Client;
import bank.transaction.Transaction;
import bank.transaction.TransactionType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bank {

    private List<Account> accounts;

    public Bank() {
        this.accounts = new ArrayList<>();
    }

    public Account createAccount(Client client, int balance) {
        final Account account = new Account(client);
        account.addTransaction(new Transaction(TransactionType.DEPOSIT, balance));
        accounts.add(account);
        return account;
    }

    public void deposit(Account account, int amount) {
        account.addTransaction(new Transaction(TransactionType.DEPOSIT, amount));
    }

    public void withdraw(Account account, int amount) {
        account.addTransaction(new Transaction(TransactionType.WITHDRAWAL, amount));
    }

    public List<Transaction> getTransactionsHistory(Account account) {
        return account.getTransactions();
    }

    public int calculateBalance(Account account) {
        final int totalDeposit;
        final int totalWithdrawal;
        final List<Transaction> transactions = account.getTransactions();
        totalDeposit = transactions.stream().filter(t -> t.getType().equals(TransactionType.DEPOSIT))
                .mapToInt(Transaction::getAmount)
                .sum();
        totalWithdrawal = transactions.stream().filter(t -> t.getType().equals(TransactionType.WITHDRAWAL))
                .mapToInt(Transaction::getAmount)
                .sum();

        return totalDeposit - totalWithdrawal;
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }
}
