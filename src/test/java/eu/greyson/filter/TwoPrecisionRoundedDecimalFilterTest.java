package eu.greyson.filter;


import eu.greyson.domain.PaymentEntry;
import eu.greyson.utils.FilterUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Stream;

public class TwoPrecisionRoundedDecimalFilterTest {

    @Test
    public void testFilterForZeroValue(){
        Stream<PaymentEntry> entries = getStreamFromPayments(
                new PaymentEntry("CZK", new BigDecimal(0.00)),
                new PaymentEntry("CZK", new BigDecimal(-0.00)),
                new PaymentEntry("CZK", new BigDecimal(-0.001)));

        Assert.assertEquals(0, entries.filter(FilterUtils.twoPrecisionRoundedDecimalIsNotNull).count());
    }

    @Test
    public void testFilterForNonZeroValue(){
        Stream<PaymentEntry> entries = getStreamFromPayments(
                new PaymentEntry("CZK", new BigDecimal(5.00)),
                new PaymentEntry("CZK", new BigDecimal(+5.00)),
                new PaymentEntry("CZK", new BigDecimal(-5.00)));

        Assert.assertEquals(3, entries.filter(FilterUtils.twoPrecisionRoundedDecimalIsNotNull).count());
    }

    private Stream<PaymentEntry> getStreamFromPayments(PaymentEntry ... entries){
        return Arrays.stream(entries);
    }


}
