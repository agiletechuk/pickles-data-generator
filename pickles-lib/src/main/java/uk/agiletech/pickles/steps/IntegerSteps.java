package uk.agiletech.pickles.steps;

import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import uk.agiletech.pickles.Context;
import uk.agiletech.pickles.data.IntegerData;
import uk.agiletech.pickles.data.LimitBehavior;

import static uk.agiletech.pickles.Context.getInstance;


public class IntegerSteps {
    @Autowired
    Context context;

    @Given("Integer data from {int} to {int} with increment {int} and {word} limit behavior named {word}")
    public void integerGenerator(int start, int end, int increment, String limitBehavior, String name) {
        IntegerData generator = new IntegerData(start, end, increment, LimitBehavior.valueOf(limitBehavior));
        getInstance().add(name, generator);
    }

    @Given("Integer data from {int} to {int} with increment {int} named {word}")
    public void integerGenerator(int start, int end, int increment, String name) {
        IntegerData generator = new IntegerData(start, end, increment);
        getInstance().add(name, generator);
    }

    @Given("Integer data from {int} to {int} named {word}")
    public void integerGenerator(int start, int end, String name) {
        IntegerData generator = new IntegerData(start, end, start < end ? 1 : -1);
        getInstance().add(name, generator);
    }

    @Given("Integer data named {word}")
    public void integerGenerator(String name) {
        IntegerData generator = new IntegerData(1, Integer.MAX_VALUE, 1);
        getInstance().add(name, generator);
    }

}
