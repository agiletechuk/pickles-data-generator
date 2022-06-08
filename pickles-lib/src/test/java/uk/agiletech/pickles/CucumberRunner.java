package uk.agiletech.pickles;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/uk/agiletech/pickles"
//        ,tags = "@pickles"
        ,glue = "uk/agiletech/pickles/steps"
)
class CucumberRunner {
}
