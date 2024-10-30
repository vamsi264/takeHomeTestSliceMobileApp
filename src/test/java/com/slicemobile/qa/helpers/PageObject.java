package com.slicemobile.qa.helpers;

import com.slicemobile.qa.components.SharedDriver;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.webdriver.WebDriverFacade;
import net.thucydides.core.webdriver.exceptions.ElementNotFoundAfterTimeoutError;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static com.slicemobile.qa.components.SharedDriver.TIMEOUT;
import static com.slicemobile.qa.components.SharedDriver.isAndroid;
import static java.lang.String.format;
import static java.time.Duration.ofSeconds;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.slf4j.LoggerFactory.getLogger;

public class PageObject extends net.serenitybdd.core.pages.PageObject {

    private static final Logger LOG = getLogger(PageObject.class);
    private String message;

    public void write(String text) {
        LOG.info(text);
        System.out.printf("%s%n", text);
        SharedDriver.scenario.log(text);
    }

    public AppiumDriver getAppiumDriver() {
        return (AppiumDriver) ((WebDriverFacade) getDriver()).getProxiedDriver();
    }


    public void click(WebElement element) {
        waitFor(waitUntilVisible(element)).waitUntilClickable().click();
    }

    public void typeInto(WebElement element, String text) {
        WebElement e = waitUntilVisible(element);
        waitABit(100);
        if (!isEmpty(e.getText())) {
            e.clear();
        }

        write(format("Inputting data: %s", text));
        ((WebElementFacade) e).type(text);
    }

    public WebElement waitUntilVisible(WebElement element, long timeout) {
        try {
            return waitFor(element).waitUntilVisible().withTimeoutOf(ofSeconds(timeout));
        } catch (NotFoundException | TimeoutException | StaleElementReferenceException | NoSuchElementException |
                 ElementNotFoundAfterTimeoutError err) {
            LOG.error(err.toString(), err);
            throw new ElementNotFoundAfterTimeoutError(err.toString());
        }
    }

    public WebElement waitUntilVisible(WebElement element) {
        try {
            return waitUntilVisible(element, TIMEOUT);
        } catch (NotFoundException | TimeoutException | StaleElementReferenceException | NoSuchElementException |
                 ElementNotFoundAfterTimeoutError err) {
            LOG.error(err.toString(), err);
            throw new ElementNotFoundAfterTimeoutError(err.toString());
        }
    }

    public static void runShellScript(String command) {
        int iExitValue;
        String sCommandString;
        sCommandString = command;
        CommandLine oCmdLine = CommandLine.parse(sCommandString);
        DefaultExecutor oDefaultExecutor = new DefaultExecutor();
        oDefaultExecutor.setExitValue(0);
        try {
            iExitValue = oDefaultExecutor.execute(oCmdLine);
        } catch (ExecuteException e) {
            System.out.println("Fail");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Denied");
            e.printStackTrace();
        }
    }
}
