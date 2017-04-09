package eu.greyson.job;


import eu.greyson.domain.PaymentRegister;
import eu.greyson.formatter.PaymentEntryFormatter;
import eu.greyson.utils.FilterUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Job for printing actual registered payment.
 */
public class PaymentPrintJob {

    private final static String PAYMENT_OUTPUT_STRING_DECORATOR =  "\n--- ACTUAL REGISTERED PAYMENT BALANCE ---\n%s\n" +
            "-----------------------------------------\n";

    private boolean running = false;

    private final PaymentRegister register;

    private final Long periodInSecond;

    private final ScheduledExecutorService scheduledExecutorService;

    private final PaymentEntryFormatter formatter;

    public PaymentPrintJob(PaymentRegister register, Long periodInSecond, PaymentEntryFormatter formatter) {
        this.register = register;
        this.periodInSecond = periodInSecond;
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
        this.formatter = formatter;
    }

    public synchronized void start(){
        if(!running){
            this.running = true;

            Runnable r = () -> System.out.printf(PAYMENT_OUTPUT_STRING_DECORATOR,
                    register.getActualPaymentBalance()
                            .stream()
                            .filter(FilterUtils.twoPrecisionRoundedDecimalIsNotNull)
                            .map(this.formatter::format)
                            .collect(Collectors.joining("\n")));

            scheduledExecutorService.scheduleAtFixedRate(r, 0, periodInSecond, TimeUnit.SECONDS);
        }
    }

    public void stop(){
        this.scheduledExecutorService.shutdownNow();
    }

}
