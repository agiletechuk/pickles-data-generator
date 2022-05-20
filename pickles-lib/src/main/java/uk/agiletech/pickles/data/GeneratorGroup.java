package uk.agiletech.pickles.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GeneratorGroup implements Generator {
    List<Generator> generators;
    private boolean end;

    public GeneratorGroup(Generator... generators) {
        this(Arrays.stream(generators).collect(Collectors.toList()));
    }

    public GeneratorGroup(List<Generator> generators) {
        if (generators.size() < 2) {
            throw new PicklesException("Group must contain more than one generator");
        }
        List<Generator> nonGroupable = generators.subList(0, generators.size() - 1).stream()
                .filter(g -> !g.isGroupable()).toList();
        if (!nonGroupable.isEmpty()) {
            throw new PicklesException("Generator list contains non-groupable generators. Only last generator is allowed to be non-groupable");
        }
        this.generators = Collections.unmodifiableList(generators);
        end = false;
    }

    @Override
    public boolean endSequence() {
        return end;
    }

    @Override
    public void next() {
        if (end) {
            return;
        }
        boolean next;
        int i = 0;
        do {
            next = false;
            Generator generator = generators.get(i);
            generator.next();
            if (generator.endSequence()) {
                i++;
                if (i > generators.size()) {
                    end = true;
                } else {
                    next = true;
                    generator.reset();
                }
            }
        } while (next);
    }

    @Override
    public void reset() {
        generators.forEach(Generator::reset);
    }

    @Override
    public boolean isGroupable() {
        return true;
    }

    public boolean isEmpty() {
        return generators.isEmpty();
    }

    public Optional<GeneratorGroup> removeGenerators(List<Generator> l) {
        List<Generator> remaining = generators.stream().filter(g -> !l.contains(g)).collect(Collectors.toList());
        return remaining.isEmpty() ? Optional.empty() : Optional.of(new GeneratorGroup(remaining));
    }
}
