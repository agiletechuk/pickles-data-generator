package uk.agiletech.pickles.step;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import uk.agiletech.pickles.data.IntegerData;
import uk.agiletech.pickles.data.LimitBehavior;

import java.util.Arrays;
import java.util.List;

import static uk.agiletech.pickles.Context.getInstance;

public class IntegerSteps {
    @Given("Integer generator from {int} to {int} with increment {int} and {string} limit behavior named {string}")
    public void integerGenerator(int start, int end, int increment, String limitBehavior, String name) {
        IntegerData generator = new IntegerData(start, end, increment, LimitBehavior.valueOf(limitBehavior));
        getInstance().add(name, generator);
    }

    @Given("Integer generator from {int} to {int} with increment {int} named {string}")
    public void integerGenerator(int start, int end, int increment, String name) {
        IntegerData generator = new IntegerData(start, end, increment);
        getInstance().add(name, generator);
    }

    @Given("Integer generator from {int} to {int} named {string}")
    public void integerGenerator(int start, int end, String name) {
        IntegerData generator = new IntegerData(start, end, start < end ? 1 : -1);
        getInstance().add(name, generator);
    }

    @Given("Integer generator named {string}")
    public void integerGenerator(String name) {
        IntegerData generator = new IntegerData(1, Integer.MAX_VALUE, 1);
        getInstance().add(name, generator);
    }

}
