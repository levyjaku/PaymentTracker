package eu.greyson.parser;


import eu.greyson.domain.PaymentEntry;
import eu.greyson.parser.enums.PaymentParserExceptionType;
import eu.greyson.parser.impl.PaymentParserImpl;
import eu.greyson.parser.wrapper.PaymentParsedResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class PaymentParserTest {

    private PaymentParser parser;

    @Before
    public void setUp() {
        parser = new PaymentParserImpl();
    }

    @Test
    public void testEmptyInput(){
        String nullString = null;
        String emptyString = "";

        assertEqualsPaymentTest(nullString, new PaymentParsedResult(PaymentParserExceptionType.NO_DATA));
        assertEqualsPaymentTest(emptyString, new PaymentParsedResult(PaymentParserExceptionType.NO_DATA));
    }

    @Test
    public void testWrongNumberOfParameters(){
        String lessParametersInput = "USD";
        String moreParametersInput = "USD 100 000";

        assertEqualsPaymentTest(lessParametersInput, new PaymentParsedResult(PaymentParserExceptionType.WRONG_DATA_LENGTH));
        assertEqualsPaymentTest(moreParametersInput, new PaymentParsedResult(PaymentParserExceptionType.WRONG_DATA_LENGTH));
    }

    @Test
    public void testWrongCurrencyCode(){
        String wrongCurrency1 = "AAA 100";
        String wrongCurrency2 = "100 100";
        String wrongCurrency3 = "CZK1 100";

        assertEqualsPaymentTest(wrongCurrency1, new PaymentParsedResult(PaymentParserExceptionType.WRONG_CURRENCY_VALUE));
        assertEqualsPaymentTest(wrongCurrency2, new PaymentParsedResult(PaymentParserExceptionType.WRONG_CURRENCY_VALUE));
        assertEqualsPaymentTest(wrongCurrency3, new PaymentParsedResult(PaymentParserExceptionType.WRONG_CURRENCY_VALUE));
    }

    @Test
    public void testWrongAmountOfMoney(){
        String wrongAmount1 = "CZK AAA";
        String wrongAmount2 = "CZK 100,0";
        String wrongAmount3 = "CZK 100.0.";
        String wrongAmount4 = "RMB +100.";

        assertEqualsPaymentTest(wrongAmount1, new PaymentParsedResult(PaymentParserExceptionType.WRONG_MONEY_AMOUNT));
        assertEqualsPaymentTest(wrongAmount2, new PaymentParsedResult(PaymentParserExceptionType.WRONG_MONEY_AMOUNT));
        assertEqualsPaymentTest(wrongAmount3, new PaymentParsedResult(PaymentParserExceptionType.WRONG_MONEY_AMOUNT));
        assertEqualsPaymentTest(wrongAmount4, new PaymentParsedResult(PaymentParserExceptionType.WRONG_MONEY_AMOUNT));
    }

    @Test
    public void testRightPaymentEntry(){
        String rightPaymentEntry1 = "USD 100";
        String rightPaymentEntry2 = "CZK -100";
        String rightPaymentEntry3 = "RMB +100";
        String rightPaymentEntry4 = "RMB +100.5";

        assertEqualsPaymentTest(rightPaymentEntry1, new PaymentParsedResult(new PaymentEntry(100.0, "USD")));
        assertEqualsPaymentTest(rightPaymentEntry2, new PaymentParsedResult(new PaymentEntry(-100.0, "CZK")));
        assertEqualsPaymentTest(rightPaymentEntry3, new PaymentParsedResult(new PaymentEntry(100.0, "RMB")));
        assertEqualsPaymentTest(rightPaymentEntry4, new PaymentParsedResult(new PaymentEntry(100.5, "RMB")));
    }

    private void assertEqualsPaymentTest(String input, PaymentParsedResult expected){
        PaymentParsedResult parsedResult = this.parser.parse(input);
        Assert.assertEquals(expected, parsedResult);

    }
}
