package com.slicemobile.qa.helpers;

import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.util.Map;

import static java.lang.Boolean.getBoolean;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;

public class Properties {

    private static final Map<String, String> sys = System.getenv();

    private static String getProperty(String key) {
        EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();

        Logger log = getLogger(Properties.class);

        if (StringUtils.isEmpty(key)) {
            String msg = key + " cannot be null or empty";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        return env.getProperty(key);
    }

    private static String getSysProperty(String property) {
        if (property.isEmpty())
            throw new RuntimeException(property + "must be provided");

        return sys.get(property);
    }

    public static String getSauceUsername() {
        return getSysProperty("SAUCE_USERNAME");
    }

    public static String getSauceAccessKey() {
        return getSysProperty("SAUCE_ACCESS_KEY_2");
    }

    public static String getWebDriverProvided() {
        return getProperty("webdriver.driver");
    }

    public static String automationName() {
        return getProperty("appium.automationName");
    }

    public static String appPath() {
        return getProperty("appium.app");
    }

    public static String platformName() {
        return getProperty("appium.platformName");
    }

    public static String platformVersion() {
        return getProperty("appium.platformVersion");
    }

    public static String deviceName() {
        return getProperty("appium.deviceName");
    }

    public static boolean takesScreenshot() {
        return getBoolean("appium.takesScreenshot");
    }

    public static boolean noReset() {
        return parseBoolean(getProperty("appium.noReset"));
    }

    public static boolean fullReset() {
        return parseBoolean(getProperty("appium.fullReset"));
    }

    public static int newCommandTimeout() {
        return parseInt(getProperty("appium.newCommandTimeout"));
    }

    public static int launchTimeout() {
        return parseInt(getProperty("appium.launchTimeout"));
    }

    /**
     iOS-specific capabilities, see
     http://appium.io/docs/en/writing-running-appium/caps/index.html#ios-only
     http://appium.io/docs/en/drivers/ios-xcuitest-real-devices/
     http://appium.io/docs/en/writing-running-appium/web/ios-webkit-debug-proxy/
     */
    public static String bundleId() {
        return getProperty("appium.bundleId");
    }

    public static String udid() {
        return getProperty("appium.udid");
    }

    public static String autoAcceptAlerts() {
        return getProperty("autoAcceptAlerts");
    }

    public static boolean usePrebuiltWDA() {
        return parseBoolean(getProperty("usePrebuiltWDA"));
    }

    public static String updatedWDABundleId() {
        return getProperty("updatedWDABundleId");
    }

    public static String xcodeOrgId() {
        return getProperty("xcodeOrgId");
    }

    public static String xcodeSigningId() {
        return getProperty("xcodeSigningId");
    }

    public static String sendKeyStrategy() {
        return getProperty("sendKeyStrategy");
    }

    public static boolean showXcodeLog() {
        return parseBoolean("showXcodeLog");
    }

    public static boolean showIosLog() {
        return parseBoolean("showIosLog");
    }

    public static boolean startIWDP() {
        return parseBoolean(getProperty("startIWDP"));
    }

    /***
     Android-specific capabilities, see http://appium.io/docs/en/writing-running-appium/caps/index.html#android-only
     https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/android/activity-startup.md
     **/
    public static String appPackage() {
        return getProperty("appium.appPackage");
    }

    public static String appActivity() {
        return getProperty("appium.appActivity");
    }

    public static int appWaitDuration() {
        return parseInt(getProperty("appWaitDuration"));
    }

    public static boolean resetKeyboard() {
        return parseBoolean("resetKeyboard");
    }

    public static boolean unicodeKeyboard() {
        return true;
    }

    public static boolean noSign() {
        return parseBoolean("noSign");
    }

    public static boolean autoGrantPermissions() {
        return parseBoolean("autoGrantPermissions");
    }

}
