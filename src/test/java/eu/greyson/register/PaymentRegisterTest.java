package eu.greyson.register;

import eu.greyson.domain.PaymentEntry;
import eu.greyson.domain.PaymentRegister;
import eu.greyson.formatter.PaymentEntryFormatter;
import eu.greyson.parser.IPaymentParser;
import eu.greyson.parser.impl.PaymentParserImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class PaymentRegisterTest {

    private static IPaymentParser parser;

    private final static String RIGHT_PAYMENT_ENTRY_1 = "USD 100";
    private final static String RIGHT_PAYMENT_ENTRY_2 = "CZK -100";
    private final static String RIGHT_PAYMENT_ENTRY_3 = "USD +200";

    private final static String WRONG_PAYMENT_ENTRY_1 = "AAA 100";
    private final static String WRONG_PAYMENT_ENTRY_2 = "CZK 100 0";

    @BeforeClass
    public static void setUp() {
        parser = new PaymentParserImpl();
    }

    @Test
    public void testOutputIfRegisterIsEmpty() {

        PaymentRegister register = new PaymentRegister(parser);

        Assert.assertEquals(new LinkedList<PaymentEntry>(), register.getActualPaymentBalance());
    }

    @Test
    public void testOutputIfRegisterReceiveValidData() {
        PaymentRegister register = new PaymentRegister(parser);

        register.addPayment(RIGHT_PAYMENT_ENTRY_1);
        register.addPayment(RIGHT_PAYMENT_ENTRY_2);
        register.addPayment(RIGHT_PAYMENT_ENTRY_3);

        List<PaymentEntry> expectedList = getPaymentEntries(
                RIGHT_PAYMENT_ENTRY_2,
                /*
                 * Currency plus manually summed amount
                 */
                "USD 300");

        Assert.assertEquals(expectedList, register.getActualPaymentBalance());
    }

    @Test
    public void testOutputIfRegisterReceiveInvalidData() {
        PaymentRegister register = new PaymentRegister(parser);

        register.addPayment(WRONG_PAYMENT_ENTRY_1);
        register.addPayment(WRONG_PAYMENT_ENTRY_2);

        Assert.assertEquals(new LinkedList<PaymentEntry>(), register.getActualPaymentBalance());
    }

    @Test
    public void testOutputIfRegisterReceiveValidNorInvalidPaymentEntries() {
        PaymentRegister register = new PaymentRegister(parser);

        register.addPayment(RIGHT_PAYMENT_ENTRY_1);
        register.addPayment(WRONG_PAYMENT_ENTRY_2);
        register.addPayment(RIGHT_PAYMENT_ENTRY_2);
        register.addPayment(WRONG_PAYMENT_ENTRY_1);

        List<PaymentEntry> expectedList = getPaymentEntries(
                RIGHT_PAYMENT_ENTRY_2,
                RIGHT_PAYMENT_ENTRY_1);

        Assert.assertEquals(expectedList, register.getActualPaymentBalance());
    }

    private List<PaymentEntry> getPaymentEntries(String... inputs) {
        return Arrays.stream(inputs).map(i -> parser.parse(i).get()).collect(Collectors.toList());
    }

}
