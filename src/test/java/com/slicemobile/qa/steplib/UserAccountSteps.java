package com.slicemobile.qa.steplib;

import com.slicemobile.qa.screens.UserAccountScreen;
import net.serenitybdd.annotations.Step;
import net.thucydides.core.pages.Pages;

public class UserAccountSteps extends BaseSteps {

    private final UserAccountScreen userAccountScreen = new UserAccountScreen();

    UserAccountSteps(Pages pages) {
        super(pages);
    }

    @Step
    public void enterEmail(String emailType) {
        userAccountScreen.enter_email(emailType);
    }

    @Step
    public void selectPlanType(String planType) {
        userAccountScreen.planSelection(planType);
    }

    @Step
    public void magicLinkSelection() {
        userAccountScreen.magic_link_selection();
        userAccountScreen.click_email_login_btn();
    }

    @Step
    public void assertTrailPeriod() {
        userAccountScreen.verifyTrailPeriod();
    }

    @Step
    public void tapLogout() {
        userAccountScreen.click_logout_btn();
    }

}
