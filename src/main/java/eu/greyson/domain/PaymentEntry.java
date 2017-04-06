package eu.greyson.domain;
import java.util.Currency;

/**
 * DTO for wrapping information about one payment entry
 */

public class PaymentEntry {

    private final Double amount;
    private final Currency currency;

    public PaymentEntry(Double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}
