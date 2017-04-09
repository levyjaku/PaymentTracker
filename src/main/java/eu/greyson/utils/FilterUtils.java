package eu.greyson.utils;

import eu.greyson.domain.PaymentEntry;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.Predicate;

public class FilterUtils {

    public static final Predicate<PaymentEntry> twoPrecisionRoundedDecimalIsNotNull = paymentEntry -> {
        BigDecimal roundedValue = paymentEntry.getAmount()
                .round(new MathContext(2, RoundingMode.HALF_UP));

        return !BigDecimal.ZERO.equals(roundedValue);
    };
}
