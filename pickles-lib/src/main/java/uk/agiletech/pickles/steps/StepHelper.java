package uk.agiletech.pickles.steps;

import java.util.Arrays;
import java.util.List;

public class StepHelper {
    static List<String> splitValues(String commaSepValues) {
        return trim(Arrays.stream(commaSepValues.split(",")).toList());
    }

    private static List<String> trim(List<String> list) {
        return list.stream().map(String::trim).toList();
    }
}
