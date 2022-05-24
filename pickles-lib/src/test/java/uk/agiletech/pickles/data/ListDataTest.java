package uk.agiletech.pickles.data;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

class ListDataTest {
    @Test
    public void dataLessThanSize() {
        IntegerData data = new IntegerData(1, 3, 1, LimitBehavior.NULL);
        ListData listData = new ListData(data, new FixedListSize(4));
        assertThat(listData.getValue()).isEqualTo("[1,2,3]");
    }

    @Test
    public void dataGreaterThanMaxSize() {
        IntegerData data = new IntegerData(1, 10, 1, LimitBehavior.NULL);
        ListData listData = new ListData(data, new FixedListSize(4));
        assertThat(listData.getValue()).isEqualTo("[1,2,3,4]");
    }

    @Test
    public void variableSizeLists() {
        IntegerData data = new IntegerData(1, 4, 1, LimitBehavior.LOOP);
        ListData listData = new ListData(data, new RandomListSize(3,4));
        int three = 0,four = 0;
        // Generate 10 lists
        for (int i=0; i<10; i++) {
            if (listData.getValue().equals("[1,2,3]")) {
                three++;
            } else if (listData.getValue().equals("[1,2,3,4]")) {
                four++;
            } else {
                fail("list expected to be either [1,2,3] or [1,2,3,4]");
            }
            listData.next();
        }
        // Verify some have length 3 and some have length 4
        assertThat(three).isGreaterThan(0);
        assertThat(four).isGreaterThan(0);
    }

    @Test
    public void randomValues() {
        IntegerData data = new IntegerData(1, 100, 1, LimitBehavior.RANDOM);
        ListData listData = new ListData(data, new RandomListSize(4,4));
        for (int i=0; i<10; i++) {
            String result = listData.getValue();
            Pattern pattern = Pattern.compile("\\[\\d+,\\d+,\\d+,\\d+]");
            Matcher matcher = pattern.matcher(result);
            assertThat(matcher.find()).isTrue();
            listData.next();
        }
    }

    @Test
    public void randomValuesAndLengths() {
        IntegerData data = new IntegerData(1, 100, 1, LimitBehavior.RANDOM);
        ListData listData = new ListData(data, new RandomListSize(1,10));
        for (int i=0; i<10; i++) {
            String result = listData.getValue();
            System.out.println(result);
            Pattern pattern = Pattern.compile("\\[\\d+[,\\d]*]");
            Matcher matcher = pattern.matcher(result);
            assertThat(matcher.find()).isTrue();
            listData.next();
        }
    }

    @Test
    public void zeroListSize() {
        IntegerData data = new IntegerData(1, 100, 1, LimitBehavior.RANDOM);
        ListData listData = new ListData(data, new FixedListSize(0));
        assertThat(listData.getValue()).isEqualTo("[]");
    }

}