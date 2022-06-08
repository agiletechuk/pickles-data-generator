package uk.agiletech.pickles.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.agiletech.pickles.data.Data;
import uk.agiletech.pickles.data.ListData;
import uk.agiletech.pickles.data.ListValueData;
import uk.agiletech.pickles.data.RandomListSize;
import uk.agiletech.pickles.format.Format;
import uk.agiletech.pickles.format.StringFormat;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.agiletech.pickles.Context.getInstance;
import static uk.agiletech.pickles.data.LimitBehavior.LOOP;
import static uk.agiletech.pickles.data.LimitBehavior.RANDOM;

public class Steps {

    @Given("Generator group {string}")
    public void generatorsGroup(String commaSeparatedGroups) {
        List<String> groups = StepHelper.splitValues(commaSeparatedGroups);
        getInstance().generatorGroup(groups);
    }

    @When("generate {int} data")
    public void generateMessages(int count) {
        for (int i = 0; i < count; i++) {
            getInstance().next();
        }
    }

    @Given("String format {string} {string} named {word}")
    public void stringFormat(String format, String commaSeparatedFormats, String name) {
        List<String> formatNameList = StepHelper.splitValues(commaSeparatedFormats);
        List<? extends Format<?>> formatList = formatNameList.stream().map(s -> getInstance().getFormat(s)).toList();
        getInstance().add(name, new StringFormat(format, formatList));
    }


    @Given("Value data {string} named {word}")
    public void valueDataNamed(String commaSepValues, String name) {
        List<String> values = StepHelper.splitValues(commaSepValues);
        ListValueData<String> data = new ListValueData<>(values, LOOP);
        getInstance().add(name, data);
    }

    @Given("Randomly ordered value data {string} named {word}")
    public void randomValueDataNamed(String commaSepValues, String name) {
        List<String> values = StepHelper.splitValues(commaSepValues);
        ListValueData<String> data = new ListValueData<>(values, RANDOM);
        getInstance().add(name, data);
    }

    @Given("List data from {string} min {int} max {int} named {word}")
    public void listDataMinMaxNamed(String format, int min, int max, String name) {
        ListData data = new ListData(",", "[", "]",
                (Data<?>) getInstance().getFormat(format), new RandomListSize(min, max));
        getInstance().add(name, data);
    }

    @Then("format {word} value is like {string}")
    public void formatValueIsLike(String format, String regex) {
        String value = getInstance().getValue(format).toString();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        assertThat(matcher.find()).isTrue();
    }

    @And("display {word}")
    public void display(String format) {
        getInstance().enableDisplay(format);
    }

    @And("dont display {word}")
    public void dontDisplay(String format) {
        getInstance().disableDisplay(format);
    }
}
