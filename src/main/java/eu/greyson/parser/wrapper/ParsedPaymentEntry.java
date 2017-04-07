package eu.greyson.parser.wrapper;


import eu.greyson.domain.PaymentEntry;
import eu.greyson.parser.enums.PaymentParserExceptionType;

public class ParsedPaymentEntry {

    private PaymentEntry ParsedEntity;

    private PaymentParserExceptionType exceptionType = null;





    public ParsedPaymentEntry(PaymentEntry parsedEntity) {
        ParsedEntity = parsedEntity;
    }

    public ParsedPaymentEntry(PaymentParserExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public PaymentEntry getParsedEntity() {
        return ParsedEntity;
    }

    public PaymentParserExceptionType getExceptionType() {
        return exceptionType;
    }

    public boolean isValid(){
        return (exceptionType == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsedPaymentEntry that = (ParsedPaymentEntry) o;

        if (ParsedEntity != null ? !ParsedEntity.equals(that.ParsedEntity) : that.ParsedEntity != null) return false;
        return exceptionType == that.exceptionType;
    }

    @Override
    public int hashCode() {
        int result = ParsedEntity != null ? ParsedEntity.hashCode() : 0;
        result = 31 * result + (exceptionType != null ? exceptionType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParsedPaymentEntry{" +
                "ParsedEntity=" + ParsedEntity +
                ", exceptionType=" + exceptionType +
                '}';
    }
}
