package com.slicemobile.qa.components;

import com.slicemobile.qa.capabilities.AndroidDriverCapabilities;
import com.slicemobile.qa.capabilities.Capabilities;
import com.slicemobile.qa.capabilities.DriverServiceManager;
import com.slicemobile.qa.capabilities.IosDriverCapabilities;
import com.slicemobile.qa.helpers.PageObject;
import com.slicemobile.qa.helpers.Properties;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import net.thucydides.core.webdriver.DriverSource;
import org.junit.Rule;
import org.openqa.selenium.*;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.remote.service.DriverService;
import org.slf4j.Logger;

import static com.slicemobile.qa.capabilities.DriverCapabilities.*;
import static com.slicemobile.qa.helpers.Properties.*;
import static java.lang.String.format;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;
import static org.slf4j.LoggerFactory.getLogger;

public class SharedDriver implements DriverSource {

    private String message;
    public static Scenario scenario;
    public static final long TIMEOUT = 60;
    public static String platformName = platformName().toLowerCase();
    private static final Logger LOG = getLogger(SharedDriver.class);

    DriverServiceManager dsm = new DriverServiceManager(getDriverService());

    @Before
    public void setup(Scenario scenario) {
        if (Properties.platformName().equalsIgnoreCase("Android")) {
            PageObject.runShellScript("adb shell am force-stop com.google.android.gm");
        }
        SharedDriver.scenario = scenario;
    }

    @After
    public void teardown() {
        if (isWebDriverProvided()) {
            dsm.stop();
            if (getDriver() != null) {
                LOG.warn("Quitting Appium driver...");
                getDriver().quit();
            }
        }
    }

    @Override
    public WebDriver newDriver() {
        Capabilities capabilities = new Capabilities();
        switch (platformName) {
            case "android":
                capabilities.setMutableCapabilities(new AndroidDriverCapabilities());
                break;
            case "ios":
                capabilities.setMutableCapabilities(new IosDriverCapabilities());
                break;
            default:
                message = format("%s is unsupported, try either iOS or Android.", platformName);
                LOG.error(message);
                throw new UnsupportedCommandException(message);
        }
        MutableCapabilities mutableCapabilities = capabilities.getMutableCapabilities();
        setMutableCapabilities(mutableCapabilities);

        dsm.start();

        return isAndroid() ? new AndroidDriver(dsm.getUrl(), mutableCapabilities) : new IOSDriver(dsm.getUrl(), mutableCapabilities);
    }

    @Override
    public boolean takesScreenshots() {
        return takesScreenshot();
    }

    private void setMutableCapabilities(MutableCapabilities mutableCapabilities) {

        mutableCapabilities.setCapability(AUTOMATION_NAME, automationName());
        mutableCapabilities.setCapability(PLATFORM_NAME, platformName);
        mutableCapabilities.setCapability(PLATFORM_VERSION, platformVersion());
        mutableCapabilities.setCapability(DEVICE_NAME, deviceName());
        mutableCapabilities.setCapability(APP, appPath());
        mutableCapabilities.setCapability(NO_RESET, noReset());
        mutableCapabilities.setCapability(FULL_RESET, fullReset());
        mutableCapabilities.setCapability(CLEAR_SYSTEM_FILES, true);
        mutableCapabilities.setCapability(NEW_COMMAND_TIMEOUT, newCommandTimeout());
        mutableCapabilities.setCapability("forceAppLaunch", true);
        if (platformName.equalsIgnoreCase("iOS")) {
            mutableCapabilities.setCapability("udid", udid());
            mutableCapabilities.setCapability("bundleId", bundleId());
        }
    }

    public static boolean isAndroid() {
        return platformName.equalsIgnoreCase("Android");
    }

    public DriverService getDriverService() {
        try {
            LOG.warn("Starting Appium driver local service...");
            return AppiumDriverLocalService.buildService(new AppiumServiceBuilder().withIPAddress("127.0.0.1").usingAnyFreePort());
        } catch (AppiumServerHasNotBeenStartedLocallyException err) {
            message = format("Unable to start Appium driver service locally, due to %s", err.getMessage());
            LOG.error(message);
            throw new AppiumServerHasNotBeenStartedLocallyException(message);
        }
    }

    private boolean isWebDriverProvided() {
        return Properties.getWebDriverProvided().equalsIgnoreCase("provided");
    }

}
