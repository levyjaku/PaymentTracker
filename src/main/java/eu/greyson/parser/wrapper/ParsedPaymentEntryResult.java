package eu.greyson.parser.wrapper;


import eu.greyson.domain.PaymentEntry;
import eu.greyson.parser.enums.PaymentParserExceptionType;

/**
 * Class for wrapping result from class implementing {@Link IPaymentParser}.
 */
public class ParsedPaymentEntryResult {

    private PaymentEntry paymentEntry;

    /*
     * Storing Exception Type if parsing is not finished successfully
     */
    private ExceptionWrapper exceptionWrapper = null;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsedPaymentEntryResult that = (ParsedPaymentEntryResult) o;

        if (paymentEntry != null ? !paymentEntry.equals(that.paymentEntry) : that.paymentEntry != null) return false;
        return exceptionWrapper != null ? exceptionWrapper.equals(that.exceptionWrapper) : that.exceptionWrapper == null;
    }

    @Override
    public int hashCode() {
        int result = paymentEntry != null ? paymentEntry.hashCode() : 0;
        result = 31 * result + (exceptionWrapper != null ? exceptionWrapper.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParsedPaymentEntryResult{" +
                "paymentEntry=" + paymentEntry +
                ", exceptionWrapper=" + exceptionWrapper +
                '}';
    }

    /**
     * Getters and Setters
     */

    public ParsedPaymentEntryResult(PaymentEntry paymentEntry) {
        this.paymentEntry = paymentEntry;
    }

    public ParsedPaymentEntryResult(PaymentParserExceptionType exceptionType, String originalInput) {
        this.exceptionWrapper = new ExceptionWrapper(exceptionType, originalInput);
    }

    public PaymentEntry getPaymentEntry() {
        return paymentEntry;
    }

    public ExceptionWrapper getExceptionWrapper() {
        return exceptionWrapper;
    }

    public boolean isValid() {
        return (exceptionWrapper == null);
    }


    /**
     * Inner Class for wrapping result after parsing invalid data
     */
    public class ExceptionWrapper {
        final String originalInput;
        final PaymentParserExceptionType exceptionType;

        public ExceptionWrapper(PaymentParserExceptionType exceptionType, String originalInput) {
            this.originalInput = originalInput;
            this.exceptionType = exceptionType;
        }

        public String getOriginalInput() {
            return originalInput;
        }

        public PaymentParserExceptionType getExceptionType() {
            return exceptionType;
        }

        @Override
        public String toString() {
            return "ExceptionWrapper{" +
                    "originalInput='" + originalInput + '\'' +
                    ", exceptionType=" + exceptionType +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ExceptionWrapper that = (ExceptionWrapper) o;

            if (originalInput != null ? !originalInput.equals(that.originalInput) : that.originalInput != null)
                return false;
            return exceptionType == that.exceptionType;
        }

        @Override
        public int hashCode() {
            int result = originalInput != null ? originalInput.hashCode() : 0;
            result = 31 * result + (exceptionType != null ? exceptionType.hashCode() : 0);
            return result;
        }
    }

}
