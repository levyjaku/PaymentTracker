package eu.greyson.parser;


import eu.greyson.domain.PaymentEntry;
import eu.greyson.parser.enums.PaymentParserExceptionType;
import eu.greyson.parser.impl.PaymentParserImpl;
import eu.greyson.parser.wrapper.ParsedPaymentEntry;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;


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

        assertEqualsPaymentTest(nullString, new ParsedPaymentEntry(PaymentParserExceptionType.NO_DATA));
        assertEqualsPaymentTest(emptyString, new ParsedPaymentEntry(PaymentParserExceptionType.NO_DATA));
    }

    @Test
    public void testWrongNumberOfParameters(){
        String lessParametersInput = "USD";
        String moreParametersInput = "USD 100 000";

        assertEqualsPaymentTest(lessParametersInput, new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_DATA_LENGTH));
        assertEqualsPaymentTest(moreParametersInput, new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_DATA_LENGTH));
    }

    @Test
    public void testWrongCurrencyCode(){
        String wrongCurrency1 = "AAA 100";
        String wrongCurrency2 = "100 100";
        String wrongCurrency3 = "CZK1 100";

        assertEqualsPaymentTest(wrongCurrency1, new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_CURRENCY_VALUE));
        assertEqualsPaymentTest(wrongCurrency2, new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_CURRENCY_VALUE));
        assertEqualsPaymentTest(wrongCurrency3, new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_CURRENCY_VALUE));
    }

    @Test
    public void testWrongAmountOfMoney(){
        String wrongAmount1 = "CZK AAA";
        String wrongAmount2 = "CZK 100,0";
        String wrongAmount3 = "CZK 100.0.";

        assertEqualsPaymentTest(wrongAmount1, new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_MONEY_AMOUNT));
        assertEqualsPaymentTest(wrongAmount2, new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_MONEY_AMOUNT));
        assertEqualsPaymentTest(wrongAmount3, new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_MONEY_AMOUNT));
    }

    @Test
    public void testRightPaymentEntry(){
        String rightPaymentEntry1 = "USD 100";
        String rightPaymentEntry2 = "CZK -100";
        String rightPaymentEntry3 = "RMB +100";
        String rightPaymentEntry4 = "RMB +100.5";
        String rightPaymentEntry5 = "RMB +100.";

        assertEqualsPaymentTest(rightPaymentEntry1, new ParsedPaymentEntry(new PaymentEntry("USD", new BigDecimal(100.0))));
        assertEqualsPaymentTest(rightPaymentEntry2, new ParsedPaymentEntry(new PaymentEntry("CZK", new BigDecimal(-100.0))));
        assertEqualsPaymentTest(rightPaymentEntry3, new ParsedPaymentEntry(new PaymentEntry("RMB", new BigDecimal(100.0))));
        assertEqualsPaymentTest(rightPaymentEntry4, new ParsedPaymentEntry(new PaymentEntry("RMB", new BigDecimal(100.5))));
        assertEqualsPaymentTest(rightPaymentEntry5, new ParsedPaymentEntry(new PaymentEntry("RMB", new BigDecimal(100.0))));
    }

    private void assertEqualsPaymentTest(String input, ParsedPaymentEntry expected){
        ParsedPaymentEntry parsedResult = parser.parse(input);
        Assert.assertEquals(expected, parsedResult);

    }
}
