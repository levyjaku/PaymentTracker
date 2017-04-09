package eu.greyson.parser.impl;

import eu.greyson.domain.PaymentEntry;
import eu.greyson.parser.IPaymentParser;
import eu.greyson.parser.enums.PaymentParserExceptionType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Base implementation of Payment Parser  {@link IPaymentParser}
 */

public class PaymentParserImpl implements IPaymentParser {
    private final static String SEPARATOR = "\\s+";
    private final static int PARAMETERS_REQUIRED_COUNT = 2;
    private final static String EXCEPTION_MESSAGE_FORMAT = "Original Entered entry: \"%2$s\" contain error \"%1$s\" (Entry will be ignored)" ;

    private static final List<String> extendedCurrencyCodes = new LinkedList<>();
    static {
        extendedCurrencyCodes.add("RMB");
    }


    public Optional<PaymentEntry> parse(String source) {

        /*
         * Check if input is not empty
         */
        if (source == null || source.isEmpty()) {
            return logErrorAndReturnEmptyOptional("", PaymentParserExceptionType.NO_DATA);
        }

        String[] parts = source.split(SEPARATOR);

        try {

            /*
             * Check if input has right number of parameters
             */
            if (parts.length != PARAMETERS_REQUIRED_COUNT) {
                return logErrorAndReturnEmptyOptional(source, PaymentParserExceptionType.WRONG_DATA_LENGTH);
            } else {

                /*
                 * Check if currency code has right
                 */
                String currencyCode = parts[0];
                if(!isCurrencyCodeValid(currencyCode)){
                    return logErrorAndReturnEmptyOptional(source, PaymentParserExceptionType.WRONG_CURRENCY_CODE);
                }

                /*
                 * Check if amount of money is right
                 */
                String currencyValue = parts[1];
                BigDecimal convertedCurrencyValue;
                try {
                    convertedCurrencyValue = new BigDecimal(currencyValue);
                } catch (NumberFormatException ne) {
                    return logErrorAndReturnEmptyOptional(source, PaymentParserExceptionType.WRONG_MONEY_AMOUNT);
                }

                return Optional.of(new PaymentEntry(currencyCode, convertedCurrencyValue));
            }
        } catch (RuntimeException re) {
            /*
             * When during parsing some error occurred set exception to result as unknown
             */
            return logErrorAndReturnEmptyOptional(source, PaymentParserExceptionType.UNKNOWN);
        }
    }

    private Optional<PaymentEntry> logErrorAndReturnEmptyOptional(String source, PaymentParserExceptionType type){
        System.out.printf(EXCEPTION_MESSAGE_FORMAT, source, type);
        return Optional.empty();
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
