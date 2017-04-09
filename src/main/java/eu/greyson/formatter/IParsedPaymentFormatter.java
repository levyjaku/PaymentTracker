package eu.greyson.formatter;

import eu.greyson.parser.wrapper.ParsedPaymentEntryResult;

/**
 * Formatter for parsed payment entry {@Link ParsedPaymentEntryResult}
 */
public interface IParsedPaymentFormatter {

    String format(ParsedPaymentEntryResult parsedPayment);
}
