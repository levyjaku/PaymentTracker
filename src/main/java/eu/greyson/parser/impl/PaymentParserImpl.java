package eu.greyson.parser.impl;

import eu.greyson.domain.PaymentEntry;
import eu.greyson.parser.PaymentParser;
import eu.greyson.parser.enums.PaymentParserExceptionType;
import eu.greyson.parser.wrapper.PaymentParserResult;

import java.util.Currency;

/**
 * Base implementation of Payment Parser  {@link PaymentParser}
 */

public class PaymentParserImpl implements PaymentParser<PaymentParserResult> {
    private final static String SEPARATOR = "\\\\s+";
    private final static int PARAMETERS_REQUIRED_COUNT = 2;


    public PaymentParserResult PaymentParser(String source) {

        /**
         * Check if input is not empty
         */
        if (source == null || source.isEmpty()) {
            return new PaymentParserResult(PaymentParserExceptionType.NO_DATA);
        }

        String[] parts = source.split(SEPARATOR);

        try {

            /**
             * Check if input has right number of parameters
             */
            if (parts.length != PARAMETERS_REQUIRED_COUNT) {
                return new PaymentParserResult(PaymentParserExceptionType.WRONG_DATA_LENGTH);
            } else {

                /**
                 * Check if currency code has right
                 */
                String currencyCode = parts[0];
                Currency currency;
                try {
                    currency = Currency.getInstance(currencyCode);
                } catch (IllegalArgumentException ie) {
                    return new PaymentParserResult(PaymentParserExceptionType.WRONG_CURRENCY_VALUE);
                }

                /**
                 * Check if amount of money is right
                 */
                String currencyValue = parts[1];
                Double convertedCurrencyValue;
                try {
                    convertedCurrencyValue = Double.valueOf(currencyValue);
                } catch (NumberFormatException ne) {
                    return new PaymentParserResult(PaymentParserExceptionType.WRONG_AMOUNT_VALUE);
                }

                return new PaymentParserResult(new PaymentEntry(convertedCurrencyValue, currency));
            }
        } catch (RuntimeException re) {
            /**
             * When during parsing some error occurred set exception to result as uknown
             */
            return new PaymentParserResult(PaymentParserExceptionType.UNKNOWN);
        }
    }


}
