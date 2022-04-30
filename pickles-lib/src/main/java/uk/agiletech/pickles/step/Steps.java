package uk.agiletech.pickles.step;

import io.cucumber.java.en.When;

import static uk.agiletech.pickles.Context.getInstance;

public class Steps {

    @When("generate {int} messages")
    public void generateMessages(int count) {
        for( int i=0; i< count; i++) {
            getInstance().next();
        }
    }
}
