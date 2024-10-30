package com.slicemobile.qa.screens;

import com.slicemobile.qa.helpers.PageObject;
import com.slicemobile.qa.helpers.Properties;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.SerenityReports;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import org.junit.Assert;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

import java.util.List;

import static java.lang.String.format;
import static net.serenitybdd.core.Serenity.recordReportData;
import static org.slf4j.LoggerFactory.getLogger;

public class UserAccountScreen extends PageObject {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc=\"I have an account\"]")
    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"I have an account\"]")
    WebElement existingAccountCTA;

    @AndroidFindBy(accessibility = "Continue")
    WebElement continueBtn;

    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id=\"login-email-input\"]")
    @FindBy(xpath = "//XCUIElementTypeTextField[@name=\"login-email-input\"]")
    WebElement emailFld;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc=\"Go to your inbox\"]")
    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"Go to your inbox\"]")
    WebElement yourEmailInboxCTA;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.android.intentresolver:id/text1\" and @text=\"Gmail\"]")
    WebElement selectGmail;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id=\"com.google.android.gm:id/senders\"])[1]")
    @FindBy(xpath = "(//XCUIElementTypeOther[@name=\"Mail.messageList.cell.view\"])[1]")
    WebElement firstEmailFromGmailList;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@resource-id=\"price-option-plan-index-0\"]")
    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"price-option-plan-index-0\"]")
    WebElement firstPlan;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@resource-id=\"price-option-plan-index-1\"]")
    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"price-option-plan-index-1\"]")
    WebElement secondPlan;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@resource-id=\"price-option-plan-index-2\"]")
    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"price-option-plan-index-2\"]")
    WebElement thirdPlan;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@resource-id=\"price-option-plan-index-3\"]")
    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"price-option-plan-index-3\"]")
    WebElement fourthPlan;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc=\"Next\"]")
    WebElement nextBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='7 day Free Trial starts today']")
    WebElement trailPeriodText;

    @AndroidFindBy(xpath = "//android.widget.Button[@resource-id=\"settings-cog-pressable\"]")
    WebElement settingsBtn;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc=\"Sign out\"]")
    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"Logout\"]")
    static WebElement logoutBtn;


    String validEmail = "work.test264@gmail.com";
    private String message;

    private static final Logger LOG = getLogger(UserAccountScreen.class);

    private void click_existing_account() {
        waitABit(500);
        waitUntilVisible(existingAccountCTA).click();
        waitABit(500);
    }

    public void enter_email(String emailType) {
        switch (emailType) {
            case "Valid" -> {
                click_existing_account();
                enter_valid_email();
            }
            default -> {
                message = format("%s is not correct screen showing, try to check screenshot and run again.", emailType);
                LOG.error(message);
                throw new UnsupportedCommandException(message);
            }
        }
    }


    private void enter_valid_email() {
        waitABit(500);
        waitUntilVisible(emailFld).sendKeys(validEmail);
        recordReportData().withTitle("User Email").andContents(emailFld.getText());
        waitUntilVisible(continueBtn).click();
        waitUntilVisible(yourEmailInboxCTA).click();
        if (Properties.platformName().contains("Android")) {
            waitUntilVisible(selectGmail).click();
        }
        waitABit(500);
    }

    public void magic_link_selection() {
        waitABit(500);
        if (Properties.platformName().contains("Android")) {
            if (firstEmailFromGmailList.getAttribute("text").contains("Slice Mobile")) {
                waitABit(5000);
                List<WebElement> listItems = getAppiumDriver().findElements(By.id("com.google.android.gm:id/viewified_conversation_item_view"));
                waitABit(5000);
                listItems.get(0).click();
                waitABit(10000);
            }
        }
    }

    public void click_email_login_btn() {
        getDriver().findElement(AppiumBy.accessibilityId("Log in")).click();
    }

    public void planSelection(String planType) {
        waitABit(2000);
        switch (planType) {
            case "small" -> waitUntilVisible(firstPlan).click();
            case "medium" -> waitUntilVisible(secondPlan).click();
            case "large" -> waitUntilVisible(thirdPlan).click();
            case "unlimited" -> waitUntilVisible(fourthPlan).click();
            default -> {
                String message = format("%s plan is not showing, try to check screenshot and run again.", planType);
                LOG.error(message);
                throw new UnsupportedCommandException(message);
            }
        }
    }

    public void verifyTrailPeriod() {
        click(nextBtn);
        Assert.assertEquals(trailPeriodText.getText(), "7 day Free Trial starts today");
        Serenity.recordReportData().withTitle("Trail Period Info")
                .andContents(trailPeriodText.getText());
    }

    public void click_logout_btn() {
        click(settingsBtn);
        click(logoutBtn);
    }

}
