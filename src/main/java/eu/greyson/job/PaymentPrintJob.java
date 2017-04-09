package eu.greyson.job;


import eu.greyson.domain.PaymentRegister;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    public PaymentPrintJob(PaymentRegister register, Long periodInSecond) {
        this.register = register;
        this.periodInSecond = periodInSecond;
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    public synchronized void start(){
        if(!running){
            this.running = true;

            Runnable r = () -> System.out.printf(PAYMENT_OUTPUT_STRING_DECORATOR, register.getFormattedPaymentSumResultAndClearErrors());

            scheduledExecutorService.scheduleAtFixedRate(r, 0, periodInSecond, TimeUnit.SECONDS);
        }
    }

    public void stop(){
        this.scheduledExecutorService.shutdownNow();
    }

}
