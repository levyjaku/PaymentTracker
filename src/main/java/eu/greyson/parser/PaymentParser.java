package eu.greyson.parser;

import eu.greyson.parser.wrapper.PaymentParserResult;

/**
 * General interface for parsing payments
 */
public interface PaymentParser {

    PaymentParserResult parse(String source);

}
