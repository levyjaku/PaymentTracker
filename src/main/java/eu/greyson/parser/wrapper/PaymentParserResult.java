package eu.greyson.parser.wrapper;


import eu.greyson.domain.PaymentEntry;
import eu.greyson.parser.enums.PaymentParserExceptionType;

public class PaymentParserResult {

    private PaymentEntry ParsedEntity;

    private PaymentParserExceptionType exceptionType = null;

    public PaymentParserResult(PaymentEntry parsedEntity) {
        ParsedEntity = parsedEntity;
    }

    public PaymentParserResult(PaymentParserExceptionType exceptionType) {
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
}
