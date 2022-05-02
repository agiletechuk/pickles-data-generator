package uk.agiletech.pickles;

import uk.agiletech.pickles.data.Data;
import uk.agiletech.pickles.data.Generator;
import uk.agiletech.pickles.data.GeneratorGroup;
import uk.agiletech.pickles.format.Format;

import java.util.*;
import java.util.stream.Collectors;

public class Context {

    private static final String DEFAULT_GROUP = "DEFAULT";
    private static final Context INSTANCE = new Context();

    // Provides list of all Format and Data objects mapped to a unique name
    private volatile Map<String, Format<?>> formats;
    private volatile Map<String, Generator> generatorMap;
    private volatile Set<Generator> groups;
    private volatile Recorder recorder;

    public static Context getInstance() {
        return INSTANCE;
    }

    private Context() {
        this.generatorMap = new HashMap<>();
        this.groups = new HashSet<>();
        this.formats = new HashMap<>();
        this.recorder = new Recorder(Collections.emptyList());
    }

    public void add(String name, Format<?> format) {
        addFormat(name, format);
    }

    public void add(String name, Data<?> data) {
        addFormat(name, data);

        HashMap<String, Generator> newMap = new HashMap<>(generatorMap);
        newMap.put(name, data);
        generatorMap = Collections.unmodifiableMap(newMap);

        Set<Generator> newSet = new HashSet<>(groups);
        newSet.add(data);
        groups = Collections.unmodifiableSet(newSet);
    }

    private void addFormat(String name, Format<?> format) {
        HashMap<String, Format<?>> newMap = new HashMap<>(formats);
        newMap.put(name, format);
        formats = Collections.unmodifiableMap(newMap);
        recorder = new Recorder(formats.keySet().stream().toList());
    }

    /*
     * Rebuild the generator groups to grouop the specified generators together
     * The specified groups will be removed from any existing groups
     */
    public void generatorGroup(List<String> generatorList) {
        List<Generator> l = generatorList.stream().map(s -> generatorMap.get(s)).collect(Collectors.toList());
        GeneratorGroup group = new GeneratorGroup(l);
        HashSet<Generator> newGroups = new HashSet<>();
        newGroups.add(group);
        for (Generator g : groups) {
            if (l.contains(g)) continue;    // skip out ones that are in new group
            if (g instanceof GeneratorGroup gg) {
                Optional<GeneratorGroup> newGeneratorGroup = gg.removeGenerators(l);
                if (newGeneratorGroup.isEmpty()) continue;
                newGroups.add(newGeneratorGroup.get());
            }
        }
        groups = Collections.unmodifiableSet(newGroups);
    }

    public void next() {
        groups.forEach(Generator::next);
        printValues();
    }

    private void addToGroups(GeneratorGroup group) {
        Set<Generator> newSet = new HashSet<>(groups);
        newSet.add(group);
        groups = Collections.unmodifiableSet(newSet);
    }

    private void removeFromGroups(Generator g) {
        Set<Generator> newSet = new HashSet<>(groups);
        newSet.remove(g);
        groups = Collections.unmodifiableSet(newSet);
    }


    private void printValues() {
        formats.forEach((key, value) -> recorder.snapshot(key, value.getValue()));
        System.out.println(recorder.toString());
    }

    public Object getValue(String formatName) {
        return formats.get(formatName).getValue();
    }
}
