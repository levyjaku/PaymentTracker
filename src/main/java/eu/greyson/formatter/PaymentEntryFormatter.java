package eu.greyson.formatter;

import eu.greyson.domain.PaymentEntry;

/**
 * Formatter for payment entry {@link PaymentEntry}
 */
public interface PaymentEntryFormatter {

    String format(PaymentEntry paymentEntry);
}
