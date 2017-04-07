package eu.greyson.parser.enums;

import eu.greyson.parser.IPaymentParser;

/**
 * Type of error which can occured when {@link IPaymentParser} can not successfully parsed data
 */

public enum PaymentParserExceptionType {
    /**
     * Empty string to parse
     */
    NO_DATA("Payment Entry has no data!"),

    /**
     * String has not defined number of parameters
     */
    WRONG_DATA_LENGTH("Payment Entry has too many or too few parameters"),

    /**
     * Value of money amount is wrong
     */
    WRONG_MONEY_AMOUNT("Payment Entry has wrong amount value"),

    /**
     * Unknow currency code
     */
    WRONG_CURRENCY_VALUE("Payment Entry has unknown currency symbol"),

    /**
     * Some unknown error
     */
    UNKNOWN("During parsing Payment Entry an unknown exception occurred");


    private String message;


    PaymentParserExceptionType(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}
