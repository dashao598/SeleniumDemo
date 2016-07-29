package com.masteringselenium;

import com.masteringselenium.config.DriverType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import static com.masteringselenium.config.DriverType.FIREFOX;

/**
 * Created by saulwang on 7/25/2016.
 */
public class WebDriverThread {

    private WebDriver webDriver;

    private DriverType selectedDriverType;

    private final DriverType defaultDriverType = FIREFOX;

    private final String browser = System.getProperty("browser").toUpperCase();

    private final String operatingSystem = System.getProperty("os.name").toUpperCase();

    private final String systemArchitecture = System.getProperty("os.arch");

    public WebDriver getDriver() throws Exception {
        if (null == webDriver) {
            selectedDriverType = determineEffectiveDriverType();
            DesiredCapabilities desiredCapabilities = selectedDriverType.getDesiredCapabilities();
            instantiateWebDriver(desiredCapabilities);
        }
        return webDriver;
    }

    private DriverType determineEffectiveDriverType() {
        DriverType driverType = defaultDriverType;
        try {
            driverType = DriverType.valueOf(browser);
        } catch (IllegalArgumentException ignored) {
            System.err.println("Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
            System.err.println("No driver specified, defaulting to '" + driverType + "'...");
        }
        return driverType;
    }

    private void instantiateWebDriver(DesiredCapabilities
                                              desiredCapabilities) throws MalformedURLException {
        System.out.println(" ");
        System.out.println("Current Operating System: " + operatingSystem);
        System.out.println("Current Architecture: " + systemArchitecture);
        System.out.println("Current Browser Selection: " + selectedDriverType);
        System.out.println(" ");
        webDriver = selectedDriverType.getWebDriverObject(desiredCapabilities);
    }

    protected void setUpWebDriver(WebDriver webDriver) {
        WebDriver.Timeouts webDriverTimeouts = webDriver.manage().timeouts();
        webDriverTimeouts.implicitlyWait(30, TimeUnit.SECONDS);
        webDriverTimeouts.pageLoadTimeout(150, TimeUnit.SECONDS);
        webDriverTimeouts.setScriptTimeout(15, TimeUnit.SECONDS);
    }

    public void quitDriver() {
        if (null != webDriver) {
            webDriver.quit();
            // webDriver.close();
            webDriver = null;
        }
    }
}
