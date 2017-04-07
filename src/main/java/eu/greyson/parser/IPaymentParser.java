package eu.greyson.parser;

import eu.greyson.parser.wrapper.ParsedPaymentEntry;

/**
 * General interface for parsing payments
 */
public interface IPaymentParser {

    ParsedPaymentEntry parse(String source);

}
