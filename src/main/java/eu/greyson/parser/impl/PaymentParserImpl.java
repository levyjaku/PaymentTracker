package eu.greyson.parser.impl;

import eu.greyson.domain.PaymentEntry;
import eu.greyson.parser.IPaymentParser;
import eu.greyson.parser.enums.PaymentParserExceptionType;
import eu.greyson.parser.wrapper.ParsedPaymentEntry;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;

/**
 * Base implementation of Payment Parser  {@link IPaymentParser}
 */

public class PaymentParserImpl implements IPaymentParser {
    private final static String SEPARATOR = "\\s+";
    private final static int PARAMETERS_REQUIRED_COUNT = 2;

    private static List<String> extendedCurrencyCodes = new LinkedList<>();
    {
        extendedCurrencyCodes.add("RMB");
    }


    public ParsedPaymentEntry parse(String source) {

        /**
         * Check if input is not empty
         */
        if (source == null || source.isEmpty()) {
            return new ParsedPaymentEntry(PaymentParserExceptionType.NO_DATA);
        }

        String[] parts = source.split(SEPARATOR);

        try {

            /**
             * Check if input has right number of parameters
             */
            if (parts.length != PARAMETERS_REQUIRED_COUNT) {
                return new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_DATA_LENGTH);
            } else {

                /**
                 * Check if currency code has right
                 */
                String currencyCode = parts[0];
                if(!isCurrencyCodeValid(currencyCode)){
                    return new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_CURRENCY_VALUE);
                }

                /**
                 * Check if amount of money is right
                 */
                String currencyValue = parts[1];
                BigDecimal convertedCurrencyValue;
                try {
                    convertedCurrencyValue = new BigDecimal(currencyValue);
                } catch (NumberFormatException ne) {
                    return new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_MONEY_AMOUNT);
                }

                return new ParsedPaymentEntry(new PaymentEntry(currencyCode, convertedCurrencyValue));
            }
        } catch (RuntimeException re) {
            /**
             * When during parsing some error occurred set exception to result as uknown
             */
            return new ParsedPaymentEntry(PaymentParserExceptionType.UNKNOWN);
        }
    }

    private boolean isCurrencyCodeValid(String currencyCode){
        if(extendedCurrencyCodes.contains(currencyCode)){
            return true;
        }

        try {
            Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException ie){
            return false;
        }

        return true;
    }


}
