package eu.greyson.formatter;

import eu.greyson.domain.PaymentEntry;
import eu.greyson.formatter.impl.ParsedPaymentFormatterImpl;
import eu.greyson.parser.enums.PaymentParserExceptionType;
import eu.greyson.parser.wrapper.ParsedPaymentEntry;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static eu.greyson.formatter.impl.ParsedPaymentFormatterImpl.INVALID_ENTRY_FORMAT;

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
        ParsedPaymentEntry wrongEntryNoData = new ParsedPaymentEntry(PaymentParserExceptionType.NO_DATA, "TEST");
        ParsedPaymentEntry wrongEntryWrongDataLength = new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_DATA_LENGTH, "TEST");
        ParsedPaymentEntry wrongEntryWrongMoneyAmount = new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_MONEY_AMOUNT, "TEST");
        ParsedPaymentEntry wrongEntryWrongCurrencyValue = new ParsedPaymentEntry(PaymentParserExceptionType.WRONG_CURRENCY_VALUE, "TEST");
        ParsedPaymentEntry wrongEntryUnknown = new ParsedPaymentEntry(PaymentParserExceptionType.UNKNOWN, "TEST");

        checkWrongPayment(String.format(INVALID_ENTRY_FORMAT, PaymentParserExceptionType.NO_DATA.getMessage(),"TEST"), wrongEntryNoData);
        checkWrongPayment(String.format(INVALID_ENTRY_FORMAT, PaymentParserExceptionType.WRONG_DATA_LENGTH.getMessage(),"TEST"), wrongEntryWrongDataLength);
        checkWrongPayment(String.format(INVALID_ENTRY_FORMAT, PaymentParserExceptionType.WRONG_MONEY_AMOUNT.getMessage(),"TEST"), wrongEntryWrongMoneyAmount);
        checkWrongPayment(String.format(INVALID_ENTRY_FORMAT, PaymentParserExceptionType.WRONG_CURRENCY_VALUE.getMessage(),"TEST"), wrongEntryWrongCurrencyValue);
        checkWrongPayment(String.format(INVALID_ENTRY_FORMAT, PaymentParserExceptionType.UNKNOWN.getMessage(),"TEST"), wrongEntryUnknown);
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
