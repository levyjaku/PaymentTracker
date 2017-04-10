package eu.greyson.domain;

import eu.greyson.parser.IPaymentParser;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Class for storing payment data and make some logic above the data
 */
public class PaymentRegister {

    /**
     * Thread safe List for storing all payment entries {@link PaymentEntry}
     */
    private final List<PaymentEntry> storedPaymentEntries = new CopyOnWriteArrayList<>();

    private final IPaymentParser paymentParser;

    public PaymentRegister(final IPaymentParser paymentParser) {
        this.paymentParser = paymentParser;
    }

    public void addPayment(String paymentEntryRow) {
        paymentParser.parse(paymentEntryRow).ifPresent(this.storedPaymentEntries::add);
    }

    /**
     * Sum and order all entries with same currency
     * @return List of summed payment entries ordered by currency
     */
    public List<PaymentEntry> getActualPaymentBalance() {
        Map<String, BigDecimal> summedEntries = new HashMap<>();

        for (PaymentEntry entry : this.storedPaymentEntries) {
            String currency = entry.getCurrency();
            BigDecimal amount = entry.getAmount();

            if (summedEntries.containsKey(entry.getCurrency())) {
                summedEntries.put(currency, summedEntries.get(currency).add(amount));
            } else {
                summedEntries.put(currency, amount);
            }
        }

        List<PaymentEntry> result = summedEntries.entrySet().stream()
                .map(e -> new PaymentEntry(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        result.sort(Comparator.comparing(PaymentEntry::getCurrency));

        return result;
    }
}
