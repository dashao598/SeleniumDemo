package com.masteringselenium;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by saulwang on 7/25/2016.
 */
public class DriverFactory {

    private static ThreadLocal<WebDriverThread> driverThread;

    private static List<WebDriverThread> webDriverThreadPool =
            Collections.synchronizedList(new ArrayList<WebDriverThread>());

    @BeforeSuite
    public static void instantiateDriverObject(){
        driverThread = new ThreadLocal<WebDriverThread>(){
            @Override
            protected WebDriverThread initialValue(){
                WebDriverThread webDriverThread = new WebDriverThread();
                webDriverThreadPool.add(webDriverThread);
                return webDriverThread;
            }
        };
    }

    public static WebDriver getDriver() throws Exception{
        return driverThread.get().getDriver();
    }

//    @AfterSuite
//    public static void quitDriver() throws Exception {
//        driverThread.get().quitDriver();
//    }

    @AfterMethod
    public static void clearCookies() throws Exception{
        getDriver().manage().deleteAllCookies();;
    }

    @AfterSuite
    public static void closeDriverObjects(){
        for (WebDriverThread webDriverThread : webDriverThreadPool) {
            webDriverThread.quitDriver();
        }
    }

}
