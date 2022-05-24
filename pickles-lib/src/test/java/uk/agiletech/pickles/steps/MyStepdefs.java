package uk.agiletech.pickles.steps;

import io.cucumber.java.en.Then;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.agiletech.pickles.Context.getInstance;

public class MyStepdefs {

    @Then("format {string} value is {int}")
    public void expectedValue(String name, int value) {
        assertThat(getInstance().getValue(name)).isSameAs(value);
    }

    @Then("format {string} value is {string}")
    public void expectedValue(String name, String value) {
        assertThat(getInstance().getValue(name)).isEqualTo(value);
    }
}
