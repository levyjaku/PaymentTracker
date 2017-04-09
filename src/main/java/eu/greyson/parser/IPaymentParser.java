package eu.greyson.parser;

import eu.greyson.domain.PaymentEntry;

import java.util.Optional;

/**
 * General interface for parsing payments
 */
public interface IPaymentParser {

    Optional<PaymentEntry> parse(String source);

}
