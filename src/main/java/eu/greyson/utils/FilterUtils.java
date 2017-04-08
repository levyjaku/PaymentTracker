package eu.greyson.utils;

import eu.greyson.parser.wrapper.ParsedPaymentEntry;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.Predicate;

public class FilterUtils {

    public static Predicate<ParsedPaymentEntry> twoPrecisionRoundedDecimalIsNotNull = parsedPaymentEntry -> {
        BigDecimal roundedValue = parsedPaymentEntry.getPaymentEntry().getAmount()
                .round(new MathContext(2, RoundingMode.HALF_UP));

        return !BigDecimal.ZERO.equals(roundedValue);
    };
}