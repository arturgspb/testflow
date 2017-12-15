package ru.realweb.meta;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit.ScreenShooter;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import static com.codeborne.selenide.junit.ScreenShooter.failedTests;

public class Base {

    protected static boolean logged = false;
    protected static WebDriver driver;

    static {
        ChromeDriverManager.getInstance().setup();

//        System.setProperty("webdriver.chrome.driver", "/Users/arturgspb/Documents/chromedriver2.33");
        Configuration.browser = "chrome";
//        Configuration.browser = "firefox";

        Configuration.baseUrl = "https://www.google.ru";
        Configuration.timeout = 5000;
        Configuration.startMaximized = true;
        Configuration.savePageSource = false;
        Configuration.screenshots = true;
//        Configuration.remote = "http://selenium-chrome:4444/wd/hub";

        driver = WebDriverRunner.getWebDriver();
    }

    @Rule
    public ScreenShooter makeScreenshotOnFailure = failedTests();

    @BeforeClass
    @AfterClass
    public static void logout() {

        driver.manage().deleteAllCookies();
        logged = false;
    }

    @After
    public void disableDebugMode() {
        driver.manage().deleteCookieNamed("isDebugMode");
    }

    public void deletePageEditCookie(Long pageId) {
        driver.manage().deleteCookieNamed("showInnerEdit" + pageId);
    }

    public WebDriver driver() {
        return driver;
    }

    public void printBrowserLogs() {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries.getAll()) {
            System.out.println(entry.toString());
        }
    }

    public JavascriptExecutor jsDriver() {
        return (JavascriptExecutor) driver;
    }
}
