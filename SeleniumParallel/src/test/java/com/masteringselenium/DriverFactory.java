package com.masteringselenium;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

/**
 * Created by saulwang on 7/25/2016.
 */
public class DriverFactory {

    private static ThreadLocal<WebDriverThread> driverThread;

    @BeforeSuite
    public static void instantiateDriverObject(){
        driverThread = new ThreadLocal<WebDriverThread>(){
            @Override
            protected WebDriverThread initialValue(){
                WebDriverThread webDriverThread = new WebDriverThread();
                return webDriverThread;
            }
        };
    }

    public static WebDriver getDriver() throws Exception{
        return driverThread.get().getDriver();
    }

    @AfterMethod
    public static void quitDriver() throws Exception {
        driverThread.get().quitDriver();
    }

    @AfterMethod
    public static void clearCookies() throws Exception{
        getDriver().manage().deleteAllCookies();
    }

}
