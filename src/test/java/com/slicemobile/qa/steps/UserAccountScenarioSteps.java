package com.slicemobile.qa.steps;

import com.slicemobile.qa.steplib.UserAccountSteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

public class UserAccountScenarioSteps {

    @Steps
    private UserAccountSteps userAccountSteps;

    @Given("I enter {string} email")
    public void iEnterEmail(String emailType) {
        userAccountSteps.enterEmail(emailType);
    }

    @And("I select magic link")
    public void iSelectMagicLink() {
        userAccountSteps.magicLinkSelection();
    }

    @And("I select {string} plan")
    public void iSelectOfAPlan(String planType) {
        userAccountSteps.selectPlanType(planType);
    }

    @When("I verify the trail period")
    public void iVerifyTheTrailPeriod() {
        userAccountSteps.assertTrailPeriod();
    }

    @Then("I logout of the account")
    public void iLogoutOfTheAccount() {
        userAccountSteps.tapLogout();
    }


}
