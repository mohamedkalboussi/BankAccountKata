package bank.transaction;

import java.time.OffsetDateTime;

public class Transaction {

    private TransactionType type;
    private int amount;
    private OffsetDateTime date;

    public Transaction(TransactionType type, int amount) {
        this.type = type;
        this.amount = amount;
        this.date = OffsetDateTime.now();
    }

    public TransactionType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public OffsetDateTime getDate() {
        return date;
    }
}
