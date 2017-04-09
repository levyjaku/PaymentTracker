package eu.greyson.formatter;

import eu.greyson.domain.PaymentEntry;
import eu.greyson.formatter.impl.PaymentEntryFormatterImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

public class PaymentFormatterTest {

    private static PaymentEntryFormatter formatter;

    @BeforeClass
    public static void setUp() {
        formatter = new PaymentEntryFormatterImpl();
    }

    @Test
    public void testFormatterOutput(){
        PaymentEntry rightEntry1 = new PaymentEntry("USD", new BigDecimal(100.0));
        PaymentEntry rightEntry2 = new PaymentEntry("USD", new BigDecimal(+100.0));
        PaymentEntry rightEntry3 = new PaymentEntry("USD", new BigDecimal(-100.0));
        PaymentEntry rightEntry4 = new PaymentEntry("USD", new BigDecimal(+100.004));

        Assert.assertEquals("USD 100.00", formatter.format(rightEntry1));
        Assert.assertEquals("USD 100.00", formatter.format(rightEntry2));
        Assert.assertEquals("USD -100.00", formatter.format(rightEntry3));
        Assert.assertEquals("USD 100.00", formatter.format(rightEntry4));
    }

}
