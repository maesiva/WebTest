/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlings.rtm.tests;

import com.mlings.rtm.common.BaseTest;
import com.mlings.rtm.common.ConfigReader;
import com.mlings.rtm.pages.LoginPage;
import com.mlings.rtm.pages.MainPage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

/**
 *
 * @author Mahesh Lingsugur
 */
public class LoginTest extends BaseTest {

    private static final Logger LOGGER = Logger.getLogger(LoginTest.class.getName());
    ConfigReader conf = ConfigReader.getInstance();
    private final String RmtAppBaseUrl = conf.getValue("RmtAppBaseUrl");

    @Test
    public void userNameLoginTestP() {
        LOGGER.info("Test Start..");
        LoginPage loginPage = new LoginPage(driver);
        MainPage mainPage = new MainPage(driver);
        loginPage.launchApp(RmtAppBaseUrl + "/app/");
        loginPage.login(conf.getValue("User01UserName"), conf.getValue("User01Password"), true);
        mainPage.logOut();
    }

    @Test
    public void emailLoginTestP() {
        LOGGER.info("Test Start..");
        LoginPage loginPage = new LoginPage(driver);
        MainPage mainPage = new MainPage(driver);
        loginPage.launchApp(RmtAppBaseUrl + "/app/");
        loginPage.login(conf.getValue("User01Email"), conf.getValue("User01Password"), true);
        mainPage.logOut();
    }

    //Google login test triggering CAPTCHA. Test may need to be disabled.
    @Test(enabled = false)
    public void googleEmailLoginTestP() {
        LOGGER.info("Test Start..");
        LoginPage loginPage = new LoginPage(driver);
        MainPage mainPage = new MainPage(driver);
        loginPage.launchApp(RmtAppBaseUrl + "/app/");
        loginPage.loginGoogle(conf.getValue("User01Email"), conf.getValue("User01GooglePassword"), true);
        mainPage.logOut();
    }

    //Negative login test trigger reCAPTCHA. Test is disabled.
    @Test(enabled = false)
    public void userNameLoginTestN() {
        LOGGER.info("Test Start..");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.launchApp(RmtAppBaseUrl + "/app/");
        loginPage.login(conf.getValue("User01UserName"), "Rtm@12", false); //Wrong pwd
    }

    //Negative login test trigger reCAPTCHA. Test may need to be disabled.
    @Test(enabled = false)
    public void googleEmailLoginTestN() {
        LOGGER.info("Test Start..");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.launchApp(RmtAppBaseUrl + "/app/");
        loginPage.loginGoogle(conf.getValue("User01Email"), "Rtm@12", false); //Wrong Pwd
    }

    @Test
    public void quote() {
        LOGGER.log(Level.INFO, "getRandomQuote: " + TestUtils.getRandomQuoteEn());
        LOGGER.log(Level.INFO, "getTranslatedQuote: " + TestUtils.getRandomQuoteRandomLang());
    }

}
