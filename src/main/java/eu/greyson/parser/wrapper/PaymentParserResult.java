package eu.greyson.parser.wrapper;


import eu.greyson.parser.enums.PaymentParserExceptionType;

public class PaymentParserResult<T> {

    private T ParsedEntity;

    private PaymentParserExceptionType exceptionType;

    public PaymentParserResult(T parsedEntity) {
        ParsedEntity = parsedEntity;
    }

    public PaymentParserResult(PaymentParserExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public T getParsedEntity() {
        return ParsedEntity;
    }

    public PaymentParserExceptionType getExceptionType() {
        return exceptionType;
    }
}
