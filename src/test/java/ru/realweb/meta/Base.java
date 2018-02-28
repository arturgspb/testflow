package ru.realweb.meta;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit.ScreenShooter;
import com.codeborne.selenide.proxy.SelenideProxyServer;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.junit.ScreenShooter.failedTests;

public class Base {

    protected static boolean logged = false;
    protected static WebDriver driver;
    public static BrowserMobProxy proxy;
    public static Proxy seleniumProxy;

    static {
        // start the proxy
        proxy = new BrowserMobProxyServer();
        proxy.start(0);

        // get the Selenium proxy object
        seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

        // configure it as a desired capability
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

        proxy.addRequestFilter(new RequestFilter() {
            @Override
            public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
//                System.out.println("\n");
                System.out.println("messageInfo.getUrl() = " + messageInfo.getUrl());
//                System.out.println("request.getUri() = " + request.getUri());

//                if (
//                        messageInfo.getOriginalUrl().contains("garpun.com")
//                        || messageInfo.getOriginalUrl().contains("fonts.gstatic.com")
//                        || messageInfo.getOriginalUrl().contains("sedu.adhands.ru")
//                        || messageInfo.getOriginalUrl().contains("cdnjs.cloudflare.com")
//                        || messageInfo.getOriginalUrl().contains("rwstatic.ru")
//                        ) {
//                    return null;
//                }
//                System.out.println("messageInfo.getOriginalUrl() = " + messageInfo.getOriginalUrl());

//                System.out.println("\nrequest.getUri() = " + request.getUri());
//                System.out.println("messageInfo.getOriginalUrl = " + messageInfo.getOriginalUrl());
//                System.out.println("messageInfo.getUrl = " + messageInfo.getUrl());
//                System.out.println("contents = " + contents.getTextContents());
                return null;
            }
        });


        ChromeDriverManager.getInstance().setup();

        Configuration.headless = true;
        Configuration.browser = "chrome";
//        Configuration.browser = "firefox";

        Configuration.baseUrl = "https://www.google.ru";
        Configuration.timeout = 5000;
        Configuration.startMaximized = true;
        Configuration.savePageSource = false;
        Configuration.screenshots = true;
//        Configuration.remote = "http://selenium-chrome:4444/wd/hub";

        driver = new ChromeDriver(capabilities);
        WebDriverRunner.setWebDriver(driver);
        WebDriverRunner.webdriverContainer.setProxy(seleniumProxy);
    }

    @Rule
    public ScreenShooter makeScreenshotOnFailure = failedTests();

    @BeforeClass
    @AfterClass
    public static void logout() {

        driver.manage().deleteAllCookies();
        logged = false;
    }

    public WebDriver driver() {
        return driver;
    }

    public void printBrowserLogs() {
        Logs logs = driver.manage().logs();
        System.out.println("logs.getAvailableLogTypes() = " + logs.getAvailableLogTypes());

        for (String logType : logs.getAvailableLogTypes()) {
            System.out.println("\n\nlogType = " + logType);
            LogEntries logEntries = logs.get(logType);
            for (LogEntry entry : logEntries.getAll()) {
                System.out.println(entry.toString());
            }
        }
    }

    public JavascriptExecutor jsDriver() {
        return (JavascriptExecutor) driver;
    }
}
