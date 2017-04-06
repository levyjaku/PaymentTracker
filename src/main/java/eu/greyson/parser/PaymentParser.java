package eu.greyson.parser;

/**
 * General interface for parsing payments
 */
public interface PaymentParser<T> {

    T PaymentParser(String source);

}
