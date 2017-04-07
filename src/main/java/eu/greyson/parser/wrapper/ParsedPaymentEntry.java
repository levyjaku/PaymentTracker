package eu.greyson.parser.wrapper;


import eu.greyson.domain.PaymentEntry;
import eu.greyson.parser.enums.PaymentParserExceptionType;

/**
 * Class for wrapping result from class implementing {@Link IPaymentParser}.
 */
public class ParsedPaymentEntry {

    private PaymentEntry paymentEntry;

    /*
     * Storing Exception Type if parsing is not finished successfully
     */
    private PaymentParserExceptionType exceptionType = null;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsedPaymentEntry that = (ParsedPaymentEntry) o;

        if (paymentEntry != null ? !paymentEntry.equals(that.paymentEntry) : that.paymentEntry != null) return false;
        return exceptionType == that.exceptionType;
    }

    @Override
    public int hashCode() {
        int result = paymentEntry != null ? paymentEntry.hashCode() : 0;
        result = 31 * result + (exceptionType != null ? exceptionType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParsedPaymentEntry{" +
                "paymentEntry=" + paymentEntry +
                ", exceptionType=" + exceptionType +
                '}';
    }

    /**
     * Getters and Setters
     */

    public ParsedPaymentEntry(PaymentEntry paymentEntry) {
        this.paymentEntry = paymentEntry;
    }

    public ParsedPaymentEntry(PaymentParserExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public PaymentEntry getPaymentEntry() {
        return paymentEntry;
    }

    public PaymentParserExceptionType getExceptionType() {
        return exceptionType;
    }

    public boolean isValid() {
        return (exceptionType == null);
    }
}
