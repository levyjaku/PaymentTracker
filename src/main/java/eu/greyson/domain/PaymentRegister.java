package eu.greyson.domain;

import eu.greyson.formatter.IParsedPaymentFormatter;
import eu.greyson.parser.IPaymentParser;
import eu.greyson.parser.wrapper.ParsedPaymentEntry;
import eu.greyson.utils.FilterUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Class for storing payment data and make some logic above the data
 */
public class PaymentRegister {

    /**
     * Thread safe List for storing all payment entries {@link PaymentEntry}
     */
    private final List<ParsedPaymentEntry> storedPaymentEntries = new CopyOnWriteArrayList<>();

    private final List<ParsedPaymentEntry> invalidPaymentEntries = new CopyOnWriteArrayList<>();


    private final IPaymentParser paymentParser;
    private final IParsedPaymentFormatter parsedPaymentFormatter;

    public PaymentRegister(final IPaymentParser paymentParser, final IParsedPaymentFormatter parsedPaymentFormatter) {
        this.paymentParser = paymentParser;
        this.parsedPaymentFormatter = parsedPaymentFormatter;
    }

    public void addPayment(String paymentEntryRow) {
        ParsedPaymentEntry result = paymentParser.parse(paymentEntryRow);
        processParsedResult(result);
    }

    private void processParsedResult(ParsedPaymentEntry result) {
        if (result.isValid()) {
            storedPaymentEntries.add(result);
        } else {
            invalidPaymentEntries.add(result);
        }
    }


    /**
     * This method produce formatted output contains summed payment entries
     * followed with information about invalid payment entries.
     * Invalid entries are after producing the output cleared.
     *
     * @return  formatted output of actual state of payments
     */
    public String getFormattedPaymentSumResultAndClearErrors() {
        StringBuilder sb = new StringBuilder();
        List<ParsedPaymentEntry> summedPaymentEntries = sumEntries(storedPaymentEntries);

        if(!summedPaymentEntries.isEmpty()) {
            sb.append(summedPaymentEntries.stream()
                    .filter(FilterUtils.twoPrecisionRoundedDecimalIsNotNull)
                    .map(parsedPaymentFormatter::format)
                    .collect(Collectors.joining("\n")));
        }

        if(!summedPaymentEntries.isEmpty() && !invalidPaymentEntries.isEmpty()){
            sb.append("\n");
        }

        if(!invalidPaymentEntries.isEmpty()) {
            sb.append(invalidPaymentEntries.stream().map(parsedPaymentFormatter::format).collect(Collectors.joining("\n")));
        }

        invalidPaymentEntries.clear();

        return sb.toString();
    }

    /**
     * Sum all entries with same currency
     * @param entries for processing
     * @return List of summed payment entries ordered by currency
     */
    private List<ParsedPaymentEntry> sumEntries(List<ParsedPaymentEntry> entries) {
        Map<String, BigDecimal> summedEntries = new HashMap<>();

        for (ParsedPaymentEntry entry : entries) {
            String currency = entry.getPaymentEntry().getCurrency();
            BigDecimal amount = entry.getPaymentEntry().getAmount();

            if (summedEntries.containsKey(entry.getPaymentEntry().getCurrency())) {
                summedEntries.put(currency, summedEntries.get(currency).add(amount));
            } else {
                summedEntries.put(currency, amount);
            }
        }

        List<ParsedPaymentEntry> result = summedEntries.entrySet().stream()
                .map(e -> new ParsedPaymentEntry(new PaymentEntry(e.getKey(), e.getValue())))
                .collect(Collectors.toList());

        Collections.sort(result, Comparator.comparing(p -> p.getPaymentEntry().getCurrency()));

        return result;
    }
}
