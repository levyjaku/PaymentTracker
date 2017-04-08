package eu.greyson;


import eu.greyson.domain.PaymentRegister;
import eu.greyson.formatter.IParsedPaymentFormatter;
import eu.greyson.formatter.impl.ParsedPaymentFormatterImpl;
import eu.greyson.job.PaymentPrintJob;
import eu.greyson.parser.IPaymentParser;
import eu.greyson.parser.impl.PaymentParserImpl;

/**
 * Hello world!
 *
 */
public class PaymentTracker
{
    public static void main( String[] args ) {
        IPaymentParser parser = new PaymentParserImpl();
        IParsedPaymentFormatter formatter = new ParsedPaymentFormatterImpl();
        PaymentRegister register = new PaymentRegister(parser, formatter);

        String input1 = "USD 100";
        String input2 = "USD -300";
        String input3 = "TEST";

        register.addPayment(input1);
        register.addPayment(input2);
        register.addPayment(input3);

        PaymentPrintJob job = new PaymentPrintJob(register, 2L);
        job.start();
    }
}
