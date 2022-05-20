package uk.agiletech.pickles.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.Arrays;
import java.util.List;

import static uk.agiletech.pickles.Context.getInstance;

public class Steps {

    @Given("Generators group {string}")
    public void generatorsGroup(String commaSeparatedGroups) {
        List<String> groups = trim(Arrays.stream(commaSeparatedGroups.split(",")).toList());
        getInstance().generatorGroup(groups);
    }

    @When("generate {int} messages")
    public void generateMessages(int count) {
        for (int i = 0; i < count; i++) {
            getInstance().next();
        }
    }

    private List<String> trim(List<String> list) {
        return list.stream().map(String::trim).toList();
    }
}
