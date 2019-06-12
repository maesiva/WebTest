/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlings.rtm.pages;

import com.mlings.common.Helper;
import com.mlings.common.Utils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 *
 * @author Mahesh Lingsugur
 */
public class LoginPage {

    private static final Logger LOGGER = Logger.getLogger(LoginPage.class.getName());
    private final WebDriver driver;
    private final Helper AppTest;
    private Utils TestUtils = new Utils();

    private final int MIN_DELAYE_MS = 1500;
    private final int MAX_DELAYE_MS = 3500;

    @FindBy(id = "username")
    private WebElement usernameTxtBox;

    @FindBy(id = "password")
    private WebElement passwordTxtBox;

    @FindBy(id = "login-button")
    private WebElement loginBtn;

    @FindBy(xpath = "//div[contains(@class, 'rtm-alert')]")
    private WebElement loginError;

    @FindBy(xpath = "//div[contains(@class, 'GQ8Pzc')]")
    private WebElement googleLoginError;

    @FindBy(xpath = "//div[contains(@class, 'b-Dn-Jo')]")
    private WebElement RtmHomeLogo;

    @FindBy(xpath = "//*[contains(@class, 'rtm-login-btn-google')]")
    private WebElement logInWithGoogleBtn;

    @FindBy(id = "identifierId")
    private WebElement googleEmailTxtBox;

    @FindBy(id = "identifierNext")
    private WebElement googleIdNextBtn;

    @FindBy(name = "password")
    private WebElement googlePasswordTxtBox;

    @FindBy(id = "passwordNext")
    private WebElement googlePwdNextBtn;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        AppTest = new Helper(driver);
        PageFactory.initElements(driver, this);
    }

    public void launchApp(String appUrl) {
        LOGGER.log(Level.INFO, "launchApp: " + appUrl);
        driver.get(appUrl);
        AppTest.verifyTitle("Remember The Milk - Login");
    }

    public void loginGoogle(String email, String pwd, boolean expectPass) {
        LOGGER.log(Level.INFO, String.format("Login with user: %s, password: %s and expect to %s", email, pwd, Boolean.toString(expectPass)));
        AppTest.click(logInWithGoogleBtn);
        AppTest.setField(googleEmailTxtBox, email, TestUtils.getRandomInt(MIN_DELAYE_MS, MAX_DELAYE_MS), false);
        AppTest.click(googleIdNextBtn);
        AppTest.setField(googlePasswordTxtBox, pwd, TestUtils.getRandomInt(MIN_DELAYE_MS, MAX_DELAYE_MS), false);
        AppTest.click(googlePwdNextBtn);
        if (expectPass) {
            AppTest.verifyElementPresent(RtmHomeLogo, true);
        } else {
            AppTest.verifyElementPresent(googleLoginError, true);
            AppTest.verifyTextPresent("Wrong password. Try again or click Forgot password to reset it.", true);
        }
    }

    public void login(String user, String pwd, boolean expectPass) {
        LOGGER.log(Level.INFO, String.format("Login with user: %s, password: %s and expect to %s", user, pwd, Boolean.toString(expectPass)));
        AppTest.setField(usernameTxtBox, user, TestUtils.getRandomInt(MIN_DELAYE_MS, MAX_DELAYE_MS), false);
        AppTest.setField(passwordTxtBox, pwd, TestUtils.getRandomInt(MIN_DELAYE_MS, MAX_DELAYE_MS), false);
        AppTest.click(loginBtn);
        if (expectPass) {
            AppTest.verifyElementPresent(RtmHomeLogo, true);
        } else {
            AppTest.verifyElementPresent(loginError, true);
            AppTest.verifyTextPresent("Sorry, that wasn't a valid login. Please try again. If you've forgotten your password, you can always", true);
        }
    }
}
