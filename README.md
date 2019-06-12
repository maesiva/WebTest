# Introduction
**RTMTestSuite**: Page Object Model based Selenium Test Framework and Test Suite for **Remember The Milk** web app

# Project Structure  
Implements below Components:  
1. **Test Configureation file**   
Path: **\src\test\resources\RmtTest.properties**   
2. **POM Pages**  
Path: **\src\test\java\com\pingidentity\rtm\pages**  
3. **Tests**  
Path: **\src\test\java\com\pingidentity\rtm\tests**  
4. **Common functions and Utilities**  
Path: **\src\test\java\com\pingidentity\rtm\common**  

# Adding Tests  
Create Page and Test classes and implement the verification methoids inside Page class as needed  

# Execution  
1. **IDE**  
Import into IDE (Implemented using NetBeans) and Run the tests through project menu  
2. **Command**  
mvn clean [-Dkey=vlaue..] [-Dtest=testsuite#test] test  

Command line parameters will override RmtTest.properties values. e.g:  

`  
mvn clean -DUser01UserName=newuser User01Password=newPwd test  
`  
`  
mvn clean -Dtest=TaskListTest#addRemoveTaskListsTestP test  
`  

# Thanks!  
* https://www.seleniumhq.org/  
* https://testng.org/doc/  
* https://maven.apache.org/  
* https://www.java.com/en/  
* https://www.rememberthemilk.com/  
* https://thesimpsonsquoteapi.glitch.me/  
* https://translate.google.com/  
* http://rest-assured.io/  
* https://stackoverflow.com/  
* http://logging.apache.org/log4j/1.2/  
* https://github.com/bonigarcia/webdrivermanager  

# Contact   
Mahesh.Lingsugur@Gmail.com  