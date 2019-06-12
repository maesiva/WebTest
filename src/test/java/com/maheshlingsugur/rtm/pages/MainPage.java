/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maheshlingsugur.rtm.pages;

import com.maheshlingsugur.rtm.common.Helper;
import com.maheshlingsugur.rtm.common.Utils;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

/**
 *
 * @author Mahesh Lingsugur
 */
public class MainPage {

    private static final Logger LOGGER = Logger.getLogger(MainPage.class.getName());
    private final WebDriver driver;
    private final Helper AppTest;
    Utils TestUtils = new Utils();
    private final String TaskListName = "L" + TestUtils.testStartTimeStamp(14, true);
    private final int randNumber = TestUtils.getRandomInt(12, 20);
    

    @FindBy(css = ".b-Dn-Jo")
    private WebElement RtmHomeLogo;

    @FindBy(css = ".b-ho-Vm-i")
    private WebElement RtmSettingsBtn;

    @FindBy(css = ".Fj-Gj-Yn.b-i")
    private List<WebElement> plusBtnList;
    
    @FindBy(css = ".Fj-Gj-Yn.b-i.b-i-bn")
    private WebElement taskListPlusBtn;

    @FindBy(css = ".b-Gn-e-userInput.b-sm")
    private WebElement listNameTxtField;

    @FindBy(css = ".Um-buttonset-Wo")
    private WebElement addTaskListBtn;
    
    @FindBy(css = ".Fj-an-Hs.Um-Mp-Np")
    private WebElement taskListMenuBtn;
    
    @FindBy(xpath = "//div[contains(@class,'Um-sn-Dn') and contains(text(),'Remove list')]")
    private WebElement taskListDeleteMenuItem;
    
    @FindBy(css = "button[name='ok']")
    private WebElement taskListDeleteConfirmBtn;
        
    @FindBy(css = ".Fj-an.Fj-an-fb.Fj-an-No-nq-Hs-i")
    private List<WebElement> tasksListsList;
    
    @FindBy(xpath = "//div[contains(@class,'Um-sn-Dn') and contains(text(),'Sign out')]")
    private WebElement signOutMenuItem;

    @FindBy(css = ".rtm-top-nav-logo-white")
    private WebElement signedOutPageRmtLogo;

    @FindBy(css = ".b-Vj-Sj")
    private List<WebElement> rtmToastMessage;
    
    @FindBy(css = ".b-Qn-sm-Ys-Zs")
    private WebElement addTaskTitleField;
    
    @FindBy(css = ".b-Qn-i-Yn.Um-i")
    private WebElement addTaskBtn;
    
    @FindBy(css = ".b-fb-an.b-fb-an-qr")
    private List<WebElement> listOfTasks;
    
    @FindBy(css = ".b-fb-an-Oj")
    private List<WebElement> listOfTaskTitles;

    @FindBy(css = ".Um-Mp-Np.Um-Vm-i.Um-Vm-i-on")
    private WebElement taskMenuBtm;

    @FindBy(xpath = "//div[contains(@class,'Um-sn-Dn') and contains(text(),'Delete Task')]")
    private WebElement taskDeleteMenuItem;
    
    @FindBy(css = ".Um-Mp-Np.b-Qn-Rn-On-i")
    private List<WebElement> taskCreateOptionsBtnsList; //0-7
    
    @FindBy(css = ".b-An-sn")
    private List<WebElement> taskStartDateMenuItemsList; //1-7,9  
    
    @FindBy(xpath = "//body")
    private WebElement bodyElementForKeyPress;

    @FindBy(xpath = "//div[contains(@class,'b-f-Tm') and contains(text(),'Add a note...')]")
    private WebElement taskUpdateNotesField;
    
    @FindBy(css = ".Um-i.b-tp-Mo-i-Ud")
    private WebElement taskUpdateSaveBtn;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        AppTest = new Helper(driver);
        PageFactory.initElements(driver, this);
    }
    
    public void addTaskLists(int n) {
        LOGGER.info("addTaskLists Start..");
        for (int i = 1; i <= n; i++) {
            String curTaskListName = TaskListName + "-" + TestUtils.getRandomString(2, true, true);
            AppTest.click(plusBtnList.get(1));
            AppTest.setField(listNameTxtField, curTaskListName);
            AppTest.click(addTaskListBtn);
            AppTest.verifyElementText(rtmToastMessage.get(1), "List \"" + curTaskListName + "\" added.");
            AppTest.verifyTextPresent(curTaskListName, true);
            AppTest.waitMillis(Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    public void addTasks(int n) {
        LOGGER.info("addTasks Start..");
        for (int j = 1; j <= n; j++) {
            String taskTitle = TestUtils.getRandomString(60, true, true);
            AppTest.waitMillis(Thread.currentThread().getStackTrace()[1].getMethodName());
            AppTest.setField(addTaskTitleField, taskTitle, true);
            AppTest.click(addTaskBtn);
            AppTest.verifyElementText(rtmToastMessage.get(1), "Task \"", taskTitle, "\" added.");
            AppTest.waitMillis(400, Thread.currentThread().getStackTrace()[1].getMethodName());
            Assert.assertTrue(AppTest.isTextExistsInElements(listOfTaskTitles, taskTitle));
        }
    }
    
    public void addTaskWithDetails() {
        LOGGER.info("addTaskWithDetails Start..");
        String taskTitle = TestUtils.getRandomString(5, true, true) + ":" + TestUtils.getRandomString(25, true, true);
        AppTest.setField(addTaskTitleField, taskTitle, true);
        AppTest.clickAction(taskCreateOptionsBtnsList.get(0));
        AppTest.click(addTaskTitleField);
        AppTest.sendKey(bodyElementForKeyPress, Keys.ENTER);
        AppTest.click(addTaskTitleField);
        AppTest.clickAction(taskCreateOptionsBtnsList.get(1));
        AppTest.click(addTaskTitleField);
        AppTest.sendKey(bodyElementForKeyPress, Keys.ENTER);
        AppTest.click(addTaskBtn);
        AppTest.verifyElementText(rtmToastMessage.get(1), "Task \"", taskTitle, "\" added.");
        AppTest.waitMillis(400, Thread.currentThread().getStackTrace()[1].getMethodName());
        Assert.assertTrue(AppTest.isTextExistsInElements(listOfTaskTitles, taskTitle));
    }

    public void updateTaskDetails() {
        LOGGER.info("updateTaskDetails Start..");
            while (listOfTasks.size() > 0) {
                AppTest.clickAction(listOfTasks.get(0));
                String taskNotes = TestUtils.getRandomString(5, true, true) + TestUtils.getRandomQuoteRandomLang();
                AppTest.click(taskUpdateNotesField);
                AppTest.setField(bodyElementForKeyPress, taskNotes);
                AppTest.clickAction(taskUpdateSaveBtn);
                AppTest.verifyElementText(rtmToastMessage.get(1), "Note added.");
            }
    }

    public void removeTasks() {
        LOGGER.info("removeTasks Start..");
            while (listOfTasks.size() > 0) {
                AppTest.clickAction(listOfTasks.get(0));
                String cTaskTitle = AppTest.getElementText(listOfTasks.get(0));
                AppTest.waitMillis(Thread.currentThread().getStackTrace()[1].getMethodName());
                AppTest.clickAction(taskMenuBtm);
                AppTest.clickAction(taskDeleteMenuItem);
                AppTest.verifyElementText(rtmToastMessage.get(1), cTaskTitle.split("\\s+")[0], " has been moved to the Trash.");
                AppTest.waitMillis(400, Thread.currentThread().getStackTrace()[1].getMethodName());
                Assert.assertFalse(AppTest.isTextExistsInElements(listOfTaskTitles, cTaskTitle));
            }
    }

    public void clearAllTaskLists() {
        LOGGER.info("clearAllTaskLists Start..");
        while (tasksListsList.size() > 0) {
            AppTest.click(tasksListsList.get(0));
            AppTest.click(taskListMenuBtn);
            AppTest.click(taskListDeleteMenuItem);
            AppTest.click(taskListDeleteConfirmBtn);
            AppTest.waitMillis(800, Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    public void logOut() {
        LOGGER.info("logOut Start..");
        AppTest.click(RtmSettingsBtn);
        AppTest.click(signOutMenuItem);
        AppTest.waitForElementToLoad(signedOutPageRmtLogo);
        AppTest.verifyTitle("Remember The Milk - Your account has been logged out");
        AppTest.verifyTextPresent("You\'re now logged out of Remember The Milk", true);
    }

}
