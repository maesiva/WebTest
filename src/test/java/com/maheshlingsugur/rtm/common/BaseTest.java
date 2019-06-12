/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maheshlingsugur.rtm.common;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

/**
 *
 * @author Mahesh Lingsugur
 */
public class BaseTest {

    private static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());
    public WebDriver driver = null;
    private final int IMPLICIT_WAIT_SEC = 10;
    public ConfigReader conf = ConfigReader.getInstance();
    public Utils TestUtils = new Utils();

    @BeforeSuite
    public void suiteSetup() {
        LOGGER.log(Level.INFO, "Setup Web Driver");
        conf.putValue("TestStartTimeStamp", Instant.now().toString());
        String testBrowser = conf.getValue("TestBrowser");
        switch (testBrowser) {
            case "Chrome":
                WebDriverManager.chromedriver().setup();
                break;
            case "Firefox":
                WebDriverManager.firefoxdriver().setup();
            default:
                LOGGER.log(Level.INFO, "Unsupported Browser");
        }
    }

    @BeforeMethod
    public void testSetUp() {
        LOGGER.log(Level.INFO, "Initialize Web Driver");
        String testBrowser = conf.getValue("TestBrowser");
        switch (testBrowser) {
            case "Chrome":
                driver = new ChromeDriver();
                break;
            case "Firefox":
                driver = new FirefoxDriver();
            default:
                LOGGER.log(Level.INFO, "Unsupported Browser");
        }
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_SEC, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void testCleanUp() {
        if (driver != null) {
            LOGGER.log(Level.INFO, "Quit Web Driver");
            driver.quit();
        }
    }
}
