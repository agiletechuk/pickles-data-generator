package uk.agiletech.pickles;

import java.util.List;

public interface DataObject extends Data {
    List<String> getFieldNames();

    Object getObject(String fieldName);

    DataObject getDataObject(String fieldName);

    DataList getDataList(String fieldName);

    String getString(String fieldName);

    int getInt(String fieldName);

    double getDecimal(String fieldName);

    boolean getBoolean(String fieldName);
}

