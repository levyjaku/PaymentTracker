package eu.greyson.utils;

import eu.greyson.domain.PaymentEntry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Predicate;

public class FilterUtils {

    public static final Predicate<PaymentEntry> twoPrecisionRoundedDecimalIsNotNull = paymentEntry -> {
        BigDecimal roundedValue = paymentEntry.getAmount().setScale(2, RoundingMode.HALF_UP);

        return !(BigDecimal.ZERO.compareTo(roundedValue) == 0);
    };
}
