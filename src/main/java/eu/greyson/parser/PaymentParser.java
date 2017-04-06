package eu.greyson.parser;

import eu.greyson.parser.wrapper.PaymentParsedResult;

/**
 * General interface for parsing payments
 */
public interface PaymentParser {

    PaymentParsedResult parse(String source);

}
