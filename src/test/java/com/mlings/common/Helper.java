/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlings.common;

import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 *
 * @author Mahesh Lingsugur
 */
public class Helper {

    WebDriver driver = null;
    private static final Logger LOGGER = Logger.getLogger(Helper.class.getName());
    private final int MUST_WAIT_MILLIS = 200;
    private final int ELEMENT_HIGHLIGHT_MILLIS = 151;
    private final int ELEM_WAIT_SEC = 30;
    Utils TestUtils = new Utils();

    public Helper(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyTitle(String expTitle) {
        Assert.assertEquals(driver.getTitle(), expTitle);
    }

    public void setField(WebElement webEl, String txt) {
        setField(webEl, txt, MUST_WAIT_MILLIS, false);
    }

    public void setField(WebElement webEl, String txt, boolean noClear) {
        setField(webEl, txt, MUST_WAIT_MILLIS, noClear);
    }

    public void setField(WebElement webEl, String txt, int mustWaitMs, boolean noClear) {
        LOGGER.log(Level.INFO, String.format("Set Field: %s with text: [%s], after a wait of [%s] ms", getWebElemInfo(webEl), txt, mustWaitMs));
        click(webEl);
        try {
            if (!noClear) {
                clearWebEl(webEl);
            }
            webEl.sendKeys(txt);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    public void sendKey(WebElement webEl, Keys key) {
        sendKey(webEl, key, MUST_WAIT_MILLIS);
    }

    public void sendKey(WebElement webEl, Keys key, int waitMs) {
        click(webEl);
        try {
            webEl.sendKeys(key);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    public void setFieldJs(WebElement webEl, String txt) {
        setFieldJs(webEl, txt, MUST_WAIT_MILLIS, false);
    }

    public void setFieldJs(WebElement webEl, String txt, boolean noClear) {
        setFieldJs(webEl, txt, MUST_WAIT_MILLIS, noClear);
    }

    public void setFieldJs(WebElement webEl, String txt, int mustWaitMs, boolean noClear) {
        LOGGER.log(Level.INFO, String.format("Set Field Js: %s with text: [%s], after a wait of [%s] ms", getWebElemInfo(webEl), txt, mustWaitMs));
        click(webEl);
        if (!noClear) {
            clearWebEl(webEl);
        }
        String js = "arguments[0].setAttribute('value','" + txt + "')";
        ((JavascriptExecutor) driver).executeScript(js, webEl);
    }

    public void clickAction(WebElement webEl) {
        clickAction(webEl, MUST_WAIT_MILLIS);
    }

    public void clickAction(WebElement webEl, int mustWaitMs) {
        LOGGER.log(Level.INFO, "Click element: " + getWebElemInfo(webEl));
        waitMillis(mustWaitMs, Thread.currentThread().getStackTrace()[1].getMethodName());
        highLightWebElement(webEl);
        Actions builder = new Actions(driver);
        builder.moveToElement(webEl).click(webEl).perform();
    }

    public void click(WebElement webEl) {
        click(webEl, MUST_WAIT_MILLIS);
    }

    public void click(WebElement webEl, int mustWaitMs) {
        LOGGER.log(Level.INFO, "Click element: " + getWebElemInfo(webEl));
        waitForElementToLoad(webEl);
        waitMillis(mustWaitMs, Thread.currentThread().getStackTrace()[1].getMethodName());
        highLightWebElement(webEl);
        try {
            webEl.click();
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    public void verifyElementPresent(WebElement webEl, boolean isPresent) {
        waitForElementToLoad(webEl);
        highLightWebElement(webEl);
        try {
            Assert.assertEquals(webEl.isDisplayed(), isPresent);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    public void verifyElementText(WebElement webEl, String... textArray) {
        verifyElementText(webEl, MUST_WAIT_MILLIS, textArray);
    }

    public void verifyElementText(WebElement webEl, int mustWaitMs, String... textArray) {
        waitMillis(mustWaitMs, Thread.currentThread().getStackTrace()[1].getMethodName());
        highLightWebElement(webEl);
        Assert.assertTrue(isTextExistsInElement(webEl, textArray));
    }

    public boolean isTextExistsInElements(List<WebElement> webElements, String... textArray) {
        boolean textExists = false;
        for (WebElement webEl : webElements) {
            if (isTextExistsInElement(webEl, textArray)) {
                textExists = true;
            }
        }
        return textExists;
    }

    public boolean isTextExistsInElement(WebElement webEl, String... textArray) {
        boolean everyTextExists = true;
        for (String text : textArray) {
            text = text.replaceAll("^.|.$", "");
            boolean aTextExists = false;
            if (isGetText(webEl, text)) {
                aTextExists = true;
            } else {
                try {
                    waitForElementToLoad(webEl);
                    List<WebElement> childEls = webEl.findElements(By.xpath(".//*"));
                    for (WebElement el : childEls) {
                        if (isGetText(el, text)) {
                            aTextExists = true;
                        }
                    }
                } catch (Exception ex) {
                    Assert.fail("isTextExistsInElement: " + ex.getMessage());
                }
            }
            if (aTextExists == false) {
                everyTextExists = false;
            }
        }
        LOGGER.log(Level.INFO, String.format("Text: [%s] - Exists: [%b]", Arrays.toString(textArray), everyTextExists));
        return everyTextExists;
    }

    private boolean isGetText(WebElement webEl, String text) {
        boolean textExists = false;
        waitForElementToLoad(webEl);
        highLightWebElement(webEl);
        try {
            if (webEl.getText().contains(text)) {
                textExists = true;
            }
        } catch (Exception ex) {
            Assert.fail("isGetText: " + ex.getMessage());
        }
        return textExists;
    }

    public void verifyTextPresent(String text, boolean isExists) {
        verifyTextPresent(text, isExists, MUST_WAIT_MILLIS);
    }

    public void verifyTextPresent(String text, boolean isExists, int mustWaitMs) {
        waitMillis(mustWaitMs, Thread.currentThread().getStackTrace()[1].getMethodName());
        LOGGER.log(Level.INFO, String.format("Verify if [%s] text present [%b]", text, isExists));
        Assert.assertEquals(driver.getPageSource().contains(text), isExists);
    }

    public String getElementText(WebElement webEl) {
        String text = null;
        try {
            text = webEl.getText();
            highLightWebElement(webEl);
        } catch (Exception ex) {
            Assert.fail("getElementText: " + ex.getMessage());
        }
        return text;
    }

    public void clearWebEl(WebElement webEl) {
        try {
            webEl.clear();
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage(), ex);
        }
    }

    public void waitMillis(String info) {
        waitMillis(MUST_WAIT_MILLIS, info);
    }

    public void waitMillis(int waitMs, String info) {
        LOGGER.log(Level.INFO, String.format("Wait [%d] for [%s]", waitMs, info));
        try {
            Thread.sleep(waitMs);
        } catch (InterruptedException ex) {
            LOGGER.log(Level.FATAL, ex.getMessage(), ex);
        }

    }

    private String getWebElemInfo(WebElement webEl) {
        String info = webEl.toString();
        info = "[" + info.substring(info.indexOf("->") + 3);
        return info;
    }

    public void waitForElementToLoad(WebElement webEl) {
        waitForElementToLoad(webEl, ELEM_WAIT_SEC);
    }

    public void waitForElementToLoad(WebElement webEl, int maxWaitSec) {
        WebDriverWait wait = new WebDriverWait(driver, maxWaitSec);
        wait.until(
                ExpectedConditions.or(
                        ExpectedConditions.visibilityOf(webEl),
                        ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(webEl))
                )
        );
    }

    public void waitForElementToDisappear(WebElement webEl) {
        waitForElementToDisappear(webEl, ELEM_WAIT_SEC);
    }

    public void waitForElementToDisappear(WebElement webEl, int maxWaitSec) {
        WebDriverWait wait = new WebDriverWait(driver, maxWaitSec);
        wait.until(ExpectedConditions.invisibilityOf(webEl));
    }
    
    public void highLightWebElement(WebElement webEl) {
        JavascriptExecutor jsEx = (JavascriptExecutor) driver;

        jsEx.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", webEl);
        waitMillis(ELEMENT_HIGHLIGHT_MILLIS, Thread.currentThread().getStackTrace()[1].getMethodName());
        jsEx.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", webEl);
    }
}
