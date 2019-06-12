/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlings.rtm.tests;

import com.mlings.common.BaseTest;
import com.mlings.rtm.pages.LoginPage;
import com.mlings.rtm.pages.MainPage;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

/**
 *
 * @author Mahesh Lingsugur
 */
public class TaskListTest extends BaseTest {
    private static final Logger LOGGER = Logger.getLogger(TaskListTest.class.getName());
    private final String RmtAppBaseUrl = conf.getValue("RmtAppBaseUrl");
    
    @Test
    public void addRemoveTaskListsTestP() {
        LOGGER.info("Test Start..");
        LoginPage loginPage = new LoginPage(driver);
        MainPage mainPage = new MainPage(driver);
        loginPage.launchApp(RmtAppBaseUrl + "/app/");
        loginPage.login(conf.getValue("User01UserName"), conf.getValue("User01Password"), true);
        mainPage.clearAllTaskLists();
        mainPage.addTaskLists(5);
        mainPage.clearAllTaskLists();
        mainPage.logOut();
    }
    
    @Test
    public void addRemoveTasksTestP() {
        LOGGER.info("Test Start..");
        LoginPage loginPage = new LoginPage(driver);
        MainPage mainPage = new MainPage(driver);
        loginPage.launchApp(RmtAppBaseUrl + "/app/");
        loginPage.login(conf.getValue("User01UserName"), conf.getValue("User01Password"), true);
        mainPage.clearAllTaskLists();
        mainPage.addTaskLists(1);
        mainPage.addTasks(3);
        mainPage.removeTasks();
        mainPage.clearAllTaskLists();
        mainPage.logOut();
    }
    
    @Test
    public void addTaskWithDetails(){
        LOGGER.info("Test Start..");
        LoginPage loginPage = new LoginPage(driver);
        MainPage mainPage = new MainPage(driver);
        loginPage.launchApp(RmtAppBaseUrl + "/app/");
        loginPage.login(conf.getValue("User01UserName"), conf.getValue("User01Password"), true);
        mainPage.clearAllTaskLists();
        mainPage.addTaskLists(1);
        mainPage.addTaskWithDetails();
        mainPage.removeTasks();
        mainPage.clearAllTaskLists();
        mainPage.logOut();
    }
}
