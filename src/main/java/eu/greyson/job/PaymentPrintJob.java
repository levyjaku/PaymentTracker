package eu.greyson.job;


import eu.greyson.domain.PaymentRegister;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Job for printing actual registered payment.
 */
public class PaymentPrintJob {

    private final static String PAYMENT_OUTPUT_STRING_DECORATOR =  "REGISTERED PAYMENT STATE:\n%s\n\n";

    private boolean running = false;

    private final PaymentRegister register;

    private final Long periodInSecond;

    public PaymentPrintJob(PaymentRegister register, Long periodInSecond) {
        this.register = register;
        this.periodInSecond = periodInSecond;
    }

    public synchronized void start(){
        if(!running){
            this.running = true;
            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

            Runnable r = () -> System.out.printf(PAYMENT_OUTPUT_STRING_DECORATOR, register.getFormattedPaymentSumResultAndClearErrors());

            scheduledExecutorService.scheduleAtFixedRate(r, 0, periodInSecond, TimeUnit.SECONDS);
        }
    }

}
