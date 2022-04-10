package uk.agiletech.pickles.data;

import java.util.Arrays;
import java.util.List;

public class GeneratorGroup implements Generator {
    final List<Generator> generators;
    private boolean end;

    public GeneratorGroup(Generator... geenerators) {
        this.generators = Arrays.asList(geenerators);
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
                if (i>generators.size()) {
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
}
