package eu.greyson.formatter;

import eu.greyson.domain.PaymentEntry;
import eu.greyson.formatter.impl.ParsedPaymentFormatterImpl;
import eu.greyson.parser.enums.PaymentParserExceptionType;
import eu.greyson.parser.wrapper.ParsedPaymentEntryResult;
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
        ParsedPaymentEntryResult rightEntry1 = new ParsedPaymentEntryResult(new PaymentEntry("USD", new BigDecimal(100.0)));
        ParsedPaymentEntryResult rightEntry2 = new ParsedPaymentEntryResult(new PaymentEntry("USD", new BigDecimal(+100.0)));
        ParsedPaymentEntryResult rightEntry3 = new ParsedPaymentEntryResult(new PaymentEntry("USD", new BigDecimal(-100.0)));
        ParsedPaymentEntryResult rightEntry4 = new ParsedPaymentEntryResult(new PaymentEntry("USD", new BigDecimal(+100.004)));

        checkRightPayment("USD 100.00", rightEntry1);
        checkRightPayment("USD 100.00", rightEntry2);
        checkRightPayment("USD -100.00", rightEntry3);
        checkRightPayment("USD 100.00", rightEntry4);
    }

    @Test
    public void testFormatterIfPaymentEntryIsInvalid(){
        ParsedPaymentEntryResult wrongEntryNoData = new ParsedPaymentEntryResult(PaymentParserExceptionType.NO_DATA, "TEST");
        ParsedPaymentEntryResult wrongEntryWrongDataLength = new ParsedPaymentEntryResult(PaymentParserExceptionType.WRONG_DATA_LENGTH, "TEST");
        ParsedPaymentEntryResult wrongEntryWrongMoneyAmount = new ParsedPaymentEntryResult(PaymentParserExceptionType.WRONG_MONEY_AMOUNT, "TEST");
        ParsedPaymentEntryResult wrongEntryWrongCurrencyValue = new ParsedPaymentEntryResult(PaymentParserExceptionType.WRONG_CURRENCY_VALUE, "TEST");
        ParsedPaymentEntryResult wrongEntryUnknown = new ParsedPaymentEntryResult(PaymentParserExceptionType.UNKNOWN, "TEST");

        checkWrongPayment(String.format(INVALID_ENTRY_FORMAT, PaymentParserExceptionType.NO_DATA.getMessage(),"TEST"), wrongEntryNoData);
        checkWrongPayment(String.format(INVALID_ENTRY_FORMAT, PaymentParserExceptionType.WRONG_DATA_LENGTH.getMessage(),"TEST"), wrongEntryWrongDataLength);
        checkWrongPayment(String.format(INVALID_ENTRY_FORMAT, PaymentParserExceptionType.WRONG_MONEY_AMOUNT.getMessage(),"TEST"), wrongEntryWrongMoneyAmount);
        checkWrongPayment(String.format(INVALID_ENTRY_FORMAT, PaymentParserExceptionType.WRONG_CURRENCY_VALUE.getMessage(),"TEST"), wrongEntryWrongCurrencyValue);
        checkWrongPayment(String.format(INVALID_ENTRY_FORMAT, PaymentParserExceptionType.UNKNOWN.getMessage(),"TEST"), wrongEntryUnknown);
    }

    private void checkRightPayment(String expectedOutput, ParsedPaymentEntryResult inputEntry){
        Assert.assertEquals(true, inputEntry.isValid());
        Assert.assertEquals(expectedOutput, formatter.format(inputEntry));
    }

    private void checkWrongPayment(String expectedOutput, ParsedPaymentEntryResult inputEntry){
        Assert.assertEquals(false, inputEntry.isValid());
        Assert.assertEquals(expectedOutput, formatter.format(inputEntry));
    }

}
