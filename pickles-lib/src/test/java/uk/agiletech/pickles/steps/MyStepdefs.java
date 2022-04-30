package uk.agiletech.pickles.steps;

import io.cucumber.java.en.Then;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.agiletech.pickles.Context.getInstance;

public class MyStepdefs {

    @Then("generator {string} value is {int}")
    public void expectedValue(String name, Object value) {
        assertThat(getInstance().getValue(name)).isSameAs(value);
    }
}
