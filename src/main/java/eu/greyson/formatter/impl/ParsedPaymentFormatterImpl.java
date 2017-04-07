package eu.greyson.formatter.impl;

import eu.greyson.formatter.IParsedPaymentFormatter;
import eu.greyson.parser.wrapper.ParsedPaymentEntry;

import java.util.Locale;

/**
 * Class for formating text output made from parsed payment {@link ParsedPaymentEntry}
 */
public class ParsedPaymentFormatterImpl implements IParsedPaymentFormatter{

    private static final String VALID_ENTRY_FORMAT = "%s %.2f";

    public String format(ParsedPaymentEntry parsedPayment){
        if(parsedPayment.isValid()){
            return String.format(Locale.US, VALID_ENTRY_FORMAT,
                    parsedPayment.getPaymentEntry().getCurrency(),
                    parsedPayment.getPaymentEntry().getAmount());
        } else {
            return parsedPayment.getExceptionType().getMessage();
        }

    }

}
