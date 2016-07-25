package com.masteringselenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

/**
 * Created by saulwang on 7/25/2016.
 */
public class WebDriverThread {

    private WebDriver webDriver;

    private final String operatingSystem = System.getProperty("os.name").toUpperCase();

    private final String systemArchitecture = System.getProperty("os.arch");

    public WebDriver getDriver() throws Exception {
        if(null == webDriver){
            System.out.println(" ");
            System.out.println("Current Operating System: " + operatingSystem);
            System.out.println("Current Architecture: " + systemArchitecture);
            System.out.println("Current Browser Selection: Firefox");
            System.out.println(" ");
            webDriver = new FirefoxDriver(DesiredCapabilities.firefox());
        }

        setUpWebDriver(webDriver);

        return webDriver;
    }

    protected void setUpWebDriver(WebDriver webDriver){
        WebDriver.Timeouts webDriverTimeouts = webDriver.manage().timeouts();
        webDriverTimeouts.implicitlyWait(30, TimeUnit.SECONDS);
        webDriverTimeouts.pageLoadTimeout(150, TimeUnit.SECONDS);
        webDriverTimeouts.setScriptTimeout(15, TimeUnit.SECONDS);
    }

    public void quitDriver(){
        if(null !=webDriver){
           // webDriver.quit();
            webDriver.close();
            webDriver = null;
        }
    }
}
