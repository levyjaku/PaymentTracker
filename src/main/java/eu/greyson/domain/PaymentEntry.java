package eu.greyson.domain;

/**
 * DTO for wrapping information about one payment entry
 */

public class PaymentEntry {

    private final Double amount;
    private final String currency;

    public PaymentEntry(String currency, Double amount) {
        this.amount = amount;
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentEntry that = (PaymentEntry) o;

        if (!amount.equals(that.amount)) return false;
        return currency.equals(that.currency);
    }

    @Override
    public int hashCode() {
        int result = amount.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }
}
