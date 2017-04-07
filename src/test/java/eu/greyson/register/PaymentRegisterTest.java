package eu.greyson.register;

import eu.greyson.domain.PaymentRegister;
import eu.greyson.formatter.IParsedPaymentFormatter;
import eu.greyson.formatter.impl.ParsedPaymentFormatterImpl;
import eu.greyson.parser.IPaymentParser;
import eu.greyson.parser.impl.PaymentParserImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class PaymentRegisterTest {

    private static IPaymentParser parser;
    private static IParsedPaymentFormatter formatter;

    private final static String RIGHT_PAYMENT_ENTRY_1 = "USD 100";
    private final static String RIGHT_PAYMENT_ENTRY_2 = "CZK -100";
    private final static String RIGHT_PAYMENT_ENTRY_3 = "USD +200";
    private final static String RIGHT_PAYMENT_ENTRY_4 = "USD -200";

    private final static String WRONG_PAYMENT_ENTRY_1 = "AAA 100";
    private final static String WRONG_PAYMENT_ENTRY_2 = "CZK 100 0";

    @BeforeClass
    public static void setUp() {
        parser = new PaymentParserImpl();
        formatter = new ParsedPaymentFormatterImpl();
    }

    @Test
    public void testOutputIfRegisterIsEmpty(){

        PaymentRegister register = new PaymentRegister(parser, formatter);

        Assert.assertEquals("", register.getFormattedPaymentSumResultAndClearErrors());
    }

    @Test
    public void testOutputIfRegisterHasOnlyValidPaymentEntries(){
        PaymentRegister register = new PaymentRegister(parser, formatter);

        register.addPayment(RIGHT_PAYMENT_ENTRY_1);
        register.addPayment(RIGHT_PAYMENT_ENTRY_2);
        register.addPayment(RIGHT_PAYMENT_ENTRY_3);

        StringBuilder sb = new StringBuilder();
        sb.append(createOutputStringFromPaymentEntryString(RIGHT_PAYMENT_ENTRY_2));
        sb.append("\n");
        /**
         * Currency plus manually summed amount
         */
        sb.append(createOutputStringFromPaymentEntryString("USD 300.00"));

        Assert.assertEquals(sb.toString(), register.getFormattedPaymentSumResultAndClearErrors());
    }

    @Test
    public void testOutputIfRegisterHasOnlyInvalidPaymentEntries(){
        PaymentRegister register = new PaymentRegister(parser, formatter);

        register.addPayment(WRONG_PAYMENT_ENTRY_1);
        register.addPayment(WRONG_PAYMENT_ENTRY_2);

        StringBuilder sb = new StringBuilder();
        sb.append(createOutputStringFromPaymentEntryString(WRONG_PAYMENT_ENTRY_1));
        sb.append("\n");
        sb.append(createOutputStringFromPaymentEntryString(WRONG_PAYMENT_ENTRY_2));

        Assert.assertEquals(sb.toString(), register.getFormattedPaymentSumResultAndClearErrors());
    }

    @Test
    public void testOutputIfRegisterHasValidNorInvalidPaymentEntries(){
        PaymentRegister register = new PaymentRegister(parser, formatter);

        register.addPayment(RIGHT_PAYMENT_ENTRY_1);
        register.addPayment(WRONG_PAYMENT_ENTRY_2);
        register.addPayment(RIGHT_PAYMENT_ENTRY_2);
        register.addPayment(WRONG_PAYMENT_ENTRY_1);

        StringBuilder sb = new StringBuilder();
        sb.append(createOutputStringFromPaymentEntryString(RIGHT_PAYMENT_ENTRY_2));
        sb.append("\n");
        sb.append(createOutputStringFromPaymentEntryString(RIGHT_PAYMENT_ENTRY_1));
        sb.append("\n");
        sb.append(createOutputStringFromPaymentEntryString(WRONG_PAYMENT_ENTRY_2));
        sb.append("\n");
        sb.append(createOutputStringFromPaymentEntryString(WRONG_PAYMENT_ENTRY_1));

        Assert.assertEquals(sb.toString(), register.getFormattedPaymentSumResultAndClearErrors());
    }

    /**
     * After calling {@link PaymentRegister#getFormattedPaymentSumResultAndClearErrors()}
     * invalid payment entries are listed but after then should be deleted
     */
    @Test
    public void testIfRegisterCleanInvalidEntryAfterAreListed(){
        PaymentRegister register = new PaymentRegister(parser, formatter);

        register.addPayment(WRONG_PAYMENT_ENTRY_1);

        StringBuilder sb = new StringBuilder();
        sb.append(createOutputStringFromPaymentEntryString(WRONG_PAYMENT_ENTRY_1));

        Assert.assertEquals(sb.toString(), register.getFormattedPaymentSumResultAndClearErrors());

        Assert.assertEquals("", register.getFormattedPaymentSumResultAndClearErrors());
    }

    @Test
    public void testOutputIfSummedAmountIsZero(){
        PaymentRegister register = new PaymentRegister(parser, formatter);

        register.addPayment(RIGHT_PAYMENT_ENTRY_2);
        register.addPayment(RIGHT_PAYMENT_ENTRY_3);
        register.addPayment(RIGHT_PAYMENT_ENTRY_4);

        StringBuilder sb = new StringBuilder();
        sb.append(createOutputStringFromPaymentEntryString(RIGHT_PAYMENT_ENTRY_2));

        Assert.assertEquals(sb.toString(), register.getFormattedPaymentSumResultAndClearErrors());
    }


    private String createOutputStringFromPaymentEntryString(String inputEntry){
        return formatter.format(parser.parse(inputEntry));
    }



}
