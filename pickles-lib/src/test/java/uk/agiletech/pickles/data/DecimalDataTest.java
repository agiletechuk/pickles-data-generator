package uk.agiletech.pickles.data;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

class DecimalDataTest {
    @Test
    void name() {
    }

    @Test
    public void sequentialPerformance() throws ParseException {
        performance(LimitBehavior.NULL);
    }

    @Test
    public void randomPerformance() throws ParseException {
        performance(LimitBehavior.RANDOM);
    }

    private void performance(LimitBehavior limitBehavior) throws ParseException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        DecimalData data = new DecimalData(BigDecimal.valueOf(Long.MAX_VALUE),
                (BigDecimal) decimalFormat.parse("98,765,432,110,692,467,440,017.111"),
                (BigDecimal) decimalFormat.parse("1,000,000,000,000,000,000.111"),
                limitBehavior);
        long start = System.currentTimeMillis();
        long count = 0;
        do {
            BigDecimal val = data.getValue();
            data.next();
            if (count < 5) System.out.println(val);
            count++;
        } while (System.currentTimeMillis() - start < 1000);
        System.out.printf("%,d per second Performance of %s BigDecimal data%n",count,
                limitBehavior == LimitBehavior.RANDOM ? "random" : "sequential");
        assertThat(count, greaterThan(1000000L));
    }
}