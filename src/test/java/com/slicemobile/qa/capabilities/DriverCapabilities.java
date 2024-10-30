package com.slicemobile.qa.capabilities;

import org.openqa.selenium.MutableCapabilities;

public interface DriverCapabilities {
    MutableCapabilities getMutableCapabilities();

    String NO_RESET = "noRest";
    String CLEAR_SYSTEM_FILES = "clearSystemFiles";
    String PLATFORM_VERSION = "platformVersion";
    boolean TAKES_SCREENSHOT = true;
    String DEVICE_NAME = "deviceName";
    String APP = "app";
    String FULL_RESET = "fullRest";
    String NEW_COMMAND_TIMEOUT = "newCommandTimeout";
    String AUTOMATION_NAME = "automationName";
    String BUNDLE_ID = "bundleId";
    String APP_PACKAGE = "bundleId";
}
