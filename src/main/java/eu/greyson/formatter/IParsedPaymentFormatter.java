package eu.greyson.formatter;

import eu.greyson.parser.wrapper.ParsedPaymentEntry;

/**
 * Formatter for parsed payment entry {@Link ParsedPaymentEntry}
 */
public interface IParsedPaymentFormatter {

    String format(ParsedPaymentEntry parsedPayment);
}
