package eu.greyson;


import eu.greyson.domain.PaymentRegister;
import eu.greyson.exception.InitialDataLoadException;
import eu.greyson.formatter.PaymentEntryFormatter;
import eu.greyson.formatter.impl.PaymentEntryFormatterImpl;
import eu.greyson.job.PaymentPrintJob;
import eu.greyson.parser.IPaymentParser;
import eu.greyson.parser.impl.PaymentParserImpl;

import java.io.*;

/**
 * Payment Tracker application main class
 */
public class PaymentTracker {
    private final static int INCORRECT_NUMBER_OF_INPUT_ARGUMENT_ERROR_CODE = 1;
    private final static int CAN_NOT_LOAD_FILE_ERROR_CODE = 2;
    private final static int ENTERED_PATH_NOT_LINK_FILE_ERROR_CODE = 3;
    private final static int CAN_NOT_LOAD_DATA_FROM_CONSOLE = 5;

    private final static String QUIT_COMMAND_PHRASE = "quit";

    private final static long PAYMENT_PRINT_JOB_DELAY_IN_SECONDS = 60;

    public static void main(String[] args) {
        BufferedReader initialInputDataReader = null;
        try {
            initialInputDataReader = checkInputArgumentsAndTryToLoadFile(args);
        } catch (InitialDataLoadException ie) {
            System.out.println(ie.getMessage());
            System.exit(ie.getErrorCode());
        }

        IPaymentParser parser = new PaymentParserImpl();

        PaymentRegister register = new PaymentRegister(parser);

        addInitialDataToRegister(register, initialInputDataReader);

        PaymentEntryFormatter formatter = new PaymentEntryFormatterImpl();
        PaymentPrintJob job = new PaymentPrintJob(register, PAYMENT_PRINT_JOB_DELAY_IN_SECONDS, formatter);
        job.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                String input = br.readLine();
                if (QUIT_COMMAND_PHRASE.equals(input)) {
                    System.out.println("System will exit.");
                    break;
                } else {
                    register.addPayment(input);
                }
            }
        } catch (IOException e) {
            System.out.println("Can not load data from console. System will exit.");
            System.exit(CAN_NOT_LOAD_DATA_FROM_CONSOLE);
        } finally {
            job.stop();
        }

        System.exit(0);
    }


    /**
     * Method for loading data from entered file
     *
     * @param args input arguments
     * @return reader containing data from file
     */
    private static BufferedReader checkInputArgumentsAndTryToLoadFile(String[] args) throws InitialDataLoadException {
        if (args.length > 1) {
            throw new InitialDataLoadException("Incorrect number of input arguments. System will exit.", INCORRECT_NUMBER_OF_INPUT_ARGUMENT_ERROR_CODE);
        }

        if (args.length == 0) {
            return null;
        }

        File file = new File(args[0]);

        if(!file.exists()){
            throw new InitialDataLoadException("Can not load file - file not found. System will exit.", CAN_NOT_LOAD_FILE_ERROR_CODE);
        }

        if (file.isFile()) {
            try {
                return new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                throw new InitialDataLoadException("Can not load file - file not found. System will exit.", CAN_NOT_LOAD_FILE_ERROR_CODE);
            }
        } else {
            throw new InitialDataLoadException("Entered path does not link file. ", ENTERED_PATH_NOT_LINK_FILE_ERROR_CODE);
        }
    }

    private static void addInitialDataToRegister(PaymentRegister register, BufferedReader initialInputDataReader) {
        if (initialInputDataReader != null) {
            String line;
            try {
                while ((line = initialInputDataReader.readLine()) != null) {
                    register.addPayment(line);
                }
            } catch (IOException e) {
                System.out.println("Can not read line from file!");
            }
        }
    }


}
