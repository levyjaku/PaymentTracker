package eu.greyson.parser;

import eu.greyson.parser.wrapper.ParsedPaymentEntryResult;

/**
 * General interface for parsing payments
 */
public interface IPaymentParser {

    ParsedPaymentEntryResult parse(String source);

}
