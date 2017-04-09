package eu.greyson.parser;


import eu.greyson.domain.PaymentEntry;
import eu.greyson.parser.impl.PaymentParserImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;


public class PaymentParserTest {

    private static IPaymentParser parser;

    @BeforeClass
    public static void setUp() {
        parser = new PaymentParserImpl();
    }

    @Test
    public void testEmptyInput(){
        String nullString = null;
        String emptyString = "";

        assertEqualsPaymentTest(nullString, Optional.empty());
        assertEqualsPaymentTest(emptyString, Optional.empty());
    }

    @Test
    public void testWrongNumberOfParameters(){
        String lessParametersInput = "USD";
        String moreParametersInput = "USD 100 000";

        assertEqualsPaymentTest(lessParametersInput, Optional.empty());
        assertEqualsPaymentTest(moreParametersInput, Optional.empty());
    }

    @Test
    public void testWrongCurrencyCode(){
        String wrongCurrency1 = "AAA 100";
        String wrongCurrency2 = "100 100";
        String wrongCurrency3 = "CZK1 100";

        assertEqualsPaymentTest(wrongCurrency1, Optional.empty());
        assertEqualsPaymentTest(wrongCurrency2, Optional.empty());
        assertEqualsPaymentTest(wrongCurrency3, Optional.empty());
    }

    @Test
    public void testWrongAmountOfMoney(){
        String wrongAmount1 = "CZK AAA";
        String wrongAmount2 = "CZK 100,0";
        String wrongAmount3 = "CZK 100.0.";

        assertEqualsPaymentTest(wrongAmount1, Optional.empty());
        assertEqualsPaymentTest(wrongAmount2, Optional.empty());
        assertEqualsPaymentTest(wrongAmount3, Optional.empty());
    }

    @Test
    public void testRightPaymentEntry(){
        String rightPaymentEntry1 = "USD 100";
        String rightPaymentEntry2 = "CZK -100";
        String rightPaymentEntry3 = "RMB +100";
        String rightPaymentEntry4 = "RMB +100.5";
        String rightPaymentEntry5 = "RMB +100.";

        assertEqualsPaymentTest(rightPaymentEntry1, Optional.of(new PaymentEntry("USD", new BigDecimal(100.00))));
        assertEqualsPaymentTest(rightPaymentEntry2, Optional.of(new PaymentEntry("CZK", new BigDecimal(-100.00))));
        assertEqualsPaymentTest(rightPaymentEntry3, Optional.of(new PaymentEntry("RMB", new BigDecimal(100.00))));
        assertEqualsPaymentTest(rightPaymentEntry4, Optional.of(new PaymentEntry("RMB", new BigDecimal(100.50))));
        assertEqualsPaymentTest(rightPaymentEntry5, Optional.of(new PaymentEntry("RMB", new BigDecimal(100.00))));
    }

    private void assertEqualsPaymentTest(String input, Optional<PaymentEntry> expected){
        Optional<PaymentEntry> parsedResult = parser.parse(input);
        Assert.assertEquals(expected, parsedResult);

    }
}
