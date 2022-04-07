package uk.agiletech.pickles;

import uk.agiletech.pickles.data.Data;
import uk.agiletech.pickles.data.Generator;
import uk.agiletech.pickles.format.Format;

import java.util.Map;

public interface Context extends Generator {
    // Provides list of all Format and Data objects mapped to a unique name
    Map<String, Format> formats();

    // Invokes nest() on all generators/groups
    void next();

    void add(String name, Format format);
    void add(String name, Data data, String group);
    void add(Generator generator, String group);
}

