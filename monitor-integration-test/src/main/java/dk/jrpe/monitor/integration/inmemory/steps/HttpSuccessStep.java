package dk.jrpe.monitor.integration.inmemory.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.junit.Assert.*;

public class HttpSuccessStep {
    @Given("the in-memory database is initialized")
    public void initialize() {
        fail("Nothing implemented");
    }

    @When("a client update with new HTTP success data")
    public void updateDatabase() {
        fail("Nothing implemented");
    }

    @Then("the database store the value in the database")
    public void store() {
        fail("Nothing implemented");
    }

    @Then ("new statistics can be fetched")
    public void getStatistics() {
        fail("Nothing implemented");
    }
}
