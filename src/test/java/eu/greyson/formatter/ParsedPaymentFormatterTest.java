package eu.greyson.formatter;

import eu.greyson.domain.PaymentEntry;
import eu.greyson.formatter.impl.ParsedPaymentFormatterImpl;
import eu.greyson.parser.enums.PaymentParserExceptionType;
import eu.greyson.parser.wrapper.ParsedPaymentEntry;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

public class ParsedPaymentFormatterTest {

    private static IParsedPaymentFormatter formatter;

    @BeforeClass
    public static void setUp() {
        formatter = new ParsedPaymentFormatterImpl();
    }

    @Test
    public void testFormatterIfPaymentEntryIsValid(){
        ParsedPaymentEntry rightEntry1 = new ParsedPaymentEntry(new PaymentEntry("USD", new BigDecimal(100.0)));
        ParsedPaymentEntry rightEntry2 = new ParsedPaymentEntry(new PaymentEntry("USD", new BigDecimal(+100.0)));
        ParsedPaymentEntry rightEntry3 = new ParsedPaymentEntry(new PaymentEntry("USD", new BigDecimal(-100.0)));
        ParsedPaymentEntry rightEntry4 = new ParsedPaymentEntry(new PaymentEntry("USD", new BigDecimal(+100.004)));

        checkRightPayment("USD 100.00", rightEntry1);
        checkRightPayment("USD 100.00", rightEntry2);
        checkRightPayment("USD -100.00", rightEntry3);
        checkRightPayment("USD 100.00", rightEntry4);
    }

    @Test
    public void testFormatterIfPaymentEntryIsInvalid(){
        ParsedPaymentEntry wrongEntryNoData = new ParsedPaymentEntry(PaymentParserExceptionType.NO_DATA);
        ParsedPaymentEntry wrongEntryWrongDataLength = new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_DATA_LENGTH);
        ParsedPaymentEntry wrongEntryWrongMoneyAmount = new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_MONEY_AMOUNT);
        ParsedPaymentEntry wrongEntryWrongCurrencyValue = new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_CURRENCY_VALUE);
        ParsedPaymentEntry wrongEntryUnknown = new ParsedPaymentEntry(PaymentParserExceptionType.UNKNOWN);

        checkWrongPayment(PaymentParserExceptionType.NO_DATA.getMessage(), wrongEntryNoData);
        checkWrongPayment(PaymentParserExceptionType.WRONG_DATA_LENGTH.getMessage(), wrongEntryWrongDataLength);
        checkWrongPayment(PaymentParserExceptionType.WRONG_MONEY_AMOUNT.getMessage(), wrongEntryWrongMoneyAmount);
        checkWrongPayment(PaymentParserExceptionType.WRONG_CURRENCY_VALUE.getMessage(), wrongEntryWrongCurrencyValue);
        checkWrongPayment(PaymentParserExceptionType.UNKNOWN.getMessage(), wrongEntryUnknown);
    }

    private void checkRightPayment(String expectedOutput, ParsedPaymentEntry inputEntry){
        Assert.assertEquals(true, inputEntry.isValid());
        Assert.assertEquals(expectedOutput, formatter.format(inputEntry));
    }

    private void checkWrongPayment(String expectedOutput, ParsedPaymentEntry inputEntry){
        Assert.assertEquals(false, inputEntry.isValid());
        Assert.assertEquals(expectedOutput, formatter.format(inputEntry));
    }

}
