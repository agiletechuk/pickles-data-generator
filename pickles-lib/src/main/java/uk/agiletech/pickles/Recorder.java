package uk.agiletech.pickles;

import java.util.*;

public class Recorder {

    private static final int MAX_SIZE = 1000;

    private final Map<String, Queue<Object>> data = new HashMap<>();
    private final Collection<String> formats;

    Recorder(Collection<String> formats) {
        this.formats = formats;
        formats.forEach(this::initData);
    }

    public void snapshot(String key, Object value) {
        addData(key, value);
    }

    private void initData(String s) {
        data.put(s, data.getOrDefault(s, new ArrayDeque<>()));
    }

    private void addData(String s, Object value) {
        Queue<Object> dataList = data.get(s);
        dataList.add(value);
        if (dataList.size() > MAX_SIZE) {
            dataList.remove();
        }
    }

    public String toString() {
        StringBuilder retval = new StringBuilder();
        boolean first = true;
        for (String format : formats) {
//            if (!first) retval.append(", ");
            retval.append(format).append(":").append(data.get(format));
            retval.append(System.lineSeparator());
        }
        return retval.toString();
    }
}
