package eu.greyson.formatter.impl;

import eu.greyson.domain.PaymentEntry;
import eu.greyson.formatter.PaymentEntryFormatter;

import java.util.Locale;

/**
 * Class for formatting text output made from payment entry {@link PaymentEntry}
 */
public class PaymentEntryFormatterImpl implements PaymentEntryFormatter {

    private static final String VALID_ENTRY_FORMAT = "%s %.2f";

    public String format(PaymentEntry paymentEntry) {
        return String.format(Locale.US, VALID_ENTRY_FORMAT,
                paymentEntry.getCurrency(),
                paymentEntry.getAmount());
    }
}
