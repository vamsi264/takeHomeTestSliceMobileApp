package com.slicemobile.qa.capabilities;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.remote.service.DriverService;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

public class DriverServiceManager {

    private String message;
    private DriverService driverService;
    private static final Logger LOG = getLogger(DriverServiceManager.class);

    public DriverServiceManager(DriverService driverService) {
        this.driverService = driverService;
    }

    public void start() {
        try {
            driverService.start();
            LOG.info(format("Driver service is now running on: %s", driverService.getUrl()));
        } catch (IOException e) {
            message = format("Unable to start driver service, due to %s", Arrays.toString(e.getStackTrace()));
            LOG.error(message);
            throw new UnreachableBrowserException(message);
        }
    }

    public void stop() {
        try {
            if (isRunning()) {
                driverService.stop();
                LOG.info(format("Stopped driver service: %s", driverService.getUrl()));
            }
        } catch (WebDriverException e) {
            message = format("Unable to stop driver service, due to %s", Arrays.toString(e.getStackTrace()));
            LOG.error(message);
            throw new UnreachableBrowserException(message);
        }
    }

    public URL getUrl() {
        try {
            return driverService.getUrl();
//            try {
//                return new URL("https://" + SauceLabsWatcher.username + ":" + SauceLabsWatcher.accessKey + "@ondemand.eu-central-1.saucelabs.com:443/wd/hub");
//            } catch (MalformedURLException e) {
//                throw new RuntimeException(e);
//            }
        } catch (WebDriverException e) {
            message = format("Unable to get driver service URL, due to %s", Arrays.toString(e.getStackTrace()));
            LOG.error(message);
            throw new UnreachableBrowserException(message);
        }
    }

    public boolean isRunning() {
        try {
            return driverService.isRunning();
        } catch (UnreachableBrowserException | NullPointerException e) {
            message = format("Unable to confirm driver service is running, due to %s", Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
