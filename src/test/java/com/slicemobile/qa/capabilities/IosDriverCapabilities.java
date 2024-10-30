package com.slicemobile.qa.capabilities;

import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.MutableCapabilities;

import static com.slicemobile.qa.helpers.Properties.*;

public class IosDriverCapabilities implements DriverCapabilities {

    XCUITestOptions options = new XCUITestOptions();

    @Override
    public MutableCapabilities getMutableCapabilities() {
        MutableCapabilities mutableCapabilities = new MutableCapabilities();
        mutableCapabilities.setCapability(String.valueOf(options.getBundleId()), bundleId());
        mutableCapabilities.setCapability("WAIT_FOR_APP_SCRIPT", String.format("$.delay(%s);$.acceptAlert()", newCommandTimeout()));
        mutableCapabilities.setCapability(String.valueOf(options.getWdaLaunchTimeout()), launchTimeout());
        mutableCapabilities.setCapability(String.valueOf(options.autoAcceptAlerts()), autoAcceptAlerts());
        mutableCapabilities.setCapability("SEND_KEY_STRATEGY", sendKeyStrategy());
        mutableCapabilities.setCapability(String.valueOf(options.showXcodeLog()), showXcodeLog());
        mutableCapabilities.setCapability(String.valueOf(options.showIosLog()), showIosLog());

        /*
            Custom settings for iOS app running tests on real device.
        */
        mutableCapabilities.setCapability(String.valueOf(options.getUdid()), udid());
        mutableCapabilities.setCapability("XCODE_ORG_ID", xcodeOrgId());
        mutableCapabilities.setCapability("XCODE_SIGNING_ID", xcodeSigningId());
        mutableCapabilities.setCapability(String.valueOf(options.getUpdatedWdaBundleId()), updatedWDABundleId());
        mutableCapabilities.setCapability(String.valueOf(options.usePrebuiltWda()), usePrebuiltWDA());
        mutableCapabilities.setCapability(String.valueOf(options.getWdaLocalPort()), startIWDP());
        mutableCapabilities.setCapability("webkitDebugProxyPort", 27753);

        return mutableCapabilities;
    }
}
