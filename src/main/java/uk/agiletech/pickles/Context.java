package uk.agiletech.pickles;

import uk.agiletech.pickles.data.Data;
import uk.agiletech.pickles.data.Generator;
import uk.agiletech.pickles.format.Format;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {

    private static final String DEFAULT_GROUP = "DEFAULT";
    // Provides list of all Format and Data objects mapped to a unique name
    volatile Map<String, Format<?>> formats;
    volatile List<Monitor> monitors;
    volatile Map<String, List<Generator>> generatorMap;
    volatile Map<Path, Format<?>> textFileSources;

    void add(String name, Format<?> format) {
        addFormat(name, format);
    }

    public void add(Generator generator, String group) {
        add(generator, group);
    }

    public void add(String name, Data<?> data, String group) {
        synchronized (this) {
            addFormat(name, data);
            addGenerator(data, group);
        }
    }

    private void addFormat(String name, Format<?> format) {
        synchronized (this) {
            HashMap<String, Format<?>> newMap = new HashMap<>(formats);
            newMap.put(name, format);
            formats = Collections.unmodifiableMap(newMap);
        }
    }

    private void addGenerator(Data<?> generator, String group) {
        synchronized (this) {
            if (group == null) {
                group = DEFAULT_GROUP;
            }
            Map<String, List<Generator>> newMap = new HashMap<>(generatorMap);
            List<Generator> generatorList = newMap.getOrDefault(group, Collections.emptyList());
            generatorList.add(generator);
            newMap.put(group, generatorList);
            generatorMap = Collections.unmodifiableMap(newMap);
        }
    }

    // Invokes nest() on all generators/groups
    void next() {

    }

    public Context(Map<String, Format<?>> formats, List<Monitor> monitors, Map<Path, Format<?>> textFileSources) {
        this.formats = formats;
        this.monitors = monitors;
        this.textFileSources = textFileSources;
    }

}
