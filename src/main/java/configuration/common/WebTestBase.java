package configuration.common;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import configuration.reporting.ExtentManager;
import configuration.reporting.ExtentTestManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class WebTestBase {

    //Create Driver
    public static WebDriver driver;
    //Credentials for Sauce Labs
//    String Username ="febowo1089";
//    String SauceLabEmailID="febowo1089@tebyy.com";
//    String SauceLabPassword="Test1234$";

    //Credentials for cloud environment
//    String BrowserStackUserName ="febowo_TuCOlI";
//    String BrowserStackAccessKey="6xUdpiNG3ubXb7UWLYiA";

//    setx BROWSERSTACK_USERNAME "febowo_TuCOlI"
//    setx BROWSERSTACK_ACCESS_KEY "6xUdpiNG3ubXb7UWLYiA

    public static final String SAUCELAB_USERNAME = "febowo1089";
    public static final String SAUCELAB_ACCESS_KEY = "8a66df41-4c5c-48de-8bee-2278883cc8a7";
    public static final String BROWSERSTACK_USERNAME = "febowo_TuCOlI";
    public static final String BROWSERSTACK_ACCESS_KEY = "6xUdpiNG3ubXb7UWLYiA";
    //public static final String URL = "https://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public static final String BROWSERSTACK_URL = "https://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public static final String SAUCELAB_URL = "https://" + SAUCELAB_USERNAME + ":" + SAUCELAB_ACCESS_KEY + "@ondemand.us-west-1.saucelabs.com:443/wd/hub";


//        String browserName = "chrome";
//    String url = "https://www.amazon.com/";

    /**
     * **************************************************
     * ********** Start Of Reporting Utilities **********
     * **************************************************
     * **************************************************
     */
    //ExtentReport
    public static ExtentReports extent;
    public static ExtentTest logger;

    @BeforeSuite
    public void extentSetup(ITestContext context) {
        ExtentManager.setOutputDirectory(context);
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void startExtent(Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName().toLowerCase();
        ExtentTestManager.startTest(method.getName());
        ExtentTestManager.getTest().assignCategory(className);
    }

    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    @AfterMethod
    public void afterEachTestMethod(ITestResult result) throws Exception {
        ExtentTestManager.getTest().getTest().setStartedTime(getTime(result.getStartMillis()));
        ExtentTestManager.getTest().getTest().setEndedTime(getTime(result.getEndMillis()));
        for (String group : result.getMethod().getGroups()) {
            ExtentTestManager.getTest().assignCategory(group);
        }
        if (result.getStatus() == 1) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");
        } else if (result.getStatus() == 2) {
            //logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
            // logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
            ExtentTestManager.getTest().log(LogStatus.FAIL, getStackTrace(result.getThrowable()));
            //We do pass the path captured by this method in to the extent reports using "logger.addScreenCapture" method.
            String screenshotPath = captureScreenShotWithPath(driver, result.getName());
            //To add it in the extent report
            //   logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));

//            if (result.getStatus() == ITestResult.FAILURE) {
//                captureScreenShotWithPath(driver, result.getName());
//                logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
//            }

        } else if (result.getStatus() == 3) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
        }
        ExtentTestManager.endTest();
        extent.flush();

        // driver.close();
        //driver.quit();
        // ending test
        //endTest(logger) : It ends the current test and prepares to create HTML report
        extent.endTest(logger);
    }

    @AfterSuite
    public void generateReport() {
        extent.close();
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    public static String convertToString(String st) {
        String splitString = "";
        splitString = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(st), ' ');
        return splitString;
    }



    @BeforeTest
    public void setUpAutomation() {
        System.out.println("+++++++Automation Started +++++++++++++");
    }

    //@AfterTest(alwaysRun = true)
    @AfterMethod
    public void tearDownAutomation() {
        //  driver.close();
        if (driver != null) {
            driver.quit();
        }
        System.out.println("+++++++++++Automation Ended++++++++++++");
    }


    @Parameters({"useCloudEnv", "cloudEnvName", "os", "osVersion", "browserName", "browserVersion", "url"})
    @BeforeMethod
    public void setup(@Optional("false") boolean useCloudEnv, @Optional("sauceLabs") String cloudEnvName, @Optional("OS X") String os, @Optional("Big Sur") String osVersion, @Optional("firefox") String browserName, @Optional("100") String browserVersion, @Optional("https://www.google.com") String url) throws MalformedURLException {
        if (useCloudEnv) {
            if (cloudEnvName.equalsIgnoreCase("browserStack") || cloudEnvName.equalsIgnoreCase("sauceLabs")) {
                getCloudDriver(cloudEnvName, os, osVersion, browserName, browserVersion);
            }
        }  else {
            getLocalDriver(os, browserName);
        }
        // getLocalDriver(String os, String browserName);

//        if (this.browserName == "chrome") {
//            setUpChromeBrowser();
//        } else if (this.browserName == "firefox") {
//            setUpFireFoxBrowser();
//        }

//        if ( browserName.equalsIgnoreCase("chrome")){
//            setUpChromeBrowser();
//        } else if (browserName.equalsIgnoreCase("firefox")){
//            setUpFireFoxBrowser();
//        }
        //Navigate to amazon.com
        getLog("Browser :" + browserName);
        getLog("Url : " + url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().deleteAllCookies();
        driver.get(url);

    }



    public static void getLog(final String message) {
        Reporter.log(message, true);
    }

//    public static void setUpChromeBrowser() {
//        //Driver Location
//       // String ChromeDriverPath = "../QE_22_Practice_Automation/ExternalLibrary/BrowserDriver/Windows/chromedriver.exe";
//        String ChromeDriverPath = "../WebAutomationFrameWork_TDD_SingularModule/BrowserDriver/Windows/chromedriver.exe";
//
//        System.setProperty("webdriver.chrome.driver", ChromeDriverPath);
//
//        //open browser
//        driver = new ChromeDriver();
//    }

//    public static void setUpFireFoxBrowser() {
//        //Driver Location
//
//        String fireFoxDriverPath = "../WebAutomationFrameWork_TDD_SingularModule/BrowserDriver/Windows/geckodriver.exe";
//
//        System.setProperty("webdriver.gecko.driver", fireFoxDriverPath);
//        //open browser
//        driver = new FirefoxDriver();
//    }

    public WebDriver getLocalDriver(String os, String browserName) {
        if (browserName.equalsIgnoreCase("chrome")) {
            if (os.equalsIgnoreCase("OS X")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/BrowserDriver/Mac/chromedriver");

            } else if (os.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/BrowserDriver/Windows/chromedriver.exe");
            }
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("chrome-options")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--incognito");
            options.addArguments("--start-maximized");
            ChromeOptions capability = new ChromeOptions();
            capability.setCapability(ChromeOptions.CAPABILITY, options);
            if (os.equalsIgnoreCase("OS X")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/BrowserDriver/Mac/chromedriver");
            } else if (os.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/BrowserDriver/Windows/chromedriver.exe");
            }
            driver = new ChromeDriver(options);

        } else if (browserName.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            // options.setHeadless(true);
            options.addArguments("--start-maximized");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--private");
            FirefoxOptions capability = new FirefoxOptions();
            capability.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
            if (os.equalsIgnoreCase("OS X")) {
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/BrowserDriver/Mac/geckodriver");

            } else if (os.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/BrowserDriver/Windows/geckodriver.exe");
            }
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("ie")) {
            if (os.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "/BrowserDriver/Windows/IEdriverServer.exe");
            }
            driver = new InternetExplorerDriver();
        } else if (browserName.equalsIgnoreCase("safari")) {
            if (os.equalsIgnoreCase("OS X")) {
                System.setProperty("webdriver.safari.driver", System.getProperty("user.dir") + "/BrowserDriver/Mac/safaridriver");
            }
            driver = new SafariDriver();
        }
        return driver;
    }

    // https://automate.browserstack.com/dashboard/v2/quick-start/integrate-test-suite-step#integrate-your-test-suite-with-browserstack
    // https://app.saucelabs.com/platform-configurator
    public WebDriver getCloudDriver(String envName, String os, String osVersion, String browserName, String browserVersion) throws MalformedURLException {

        // Add the following capabilities to your test script
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browserName);
        capabilities.setCapability("browserVersion", browserVersion);

        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("os", os);
        options.put("osVersion", osVersion);
        if (envName.equalsIgnoreCase("browserStack")) {
            // capabilities.setCapability("resolution","1024x786");
            capabilities.setCapability("bstack:options", options);
            driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), capabilities);
        } else if (envName.equalsIgnoreCase("sauceLabs")) {
            capabilities.setCapability("sauce:options", options);
            //URL url = new URL("https://febowo1089:8a66df41-4c5c-48de-8bee-2278883cc8a7@ondemand.us-west-1.saucelabs.com:443/wd/hub");
            driver = new RemoteWebDriver(new URL(SAUCELAB_URL), capabilities);
        }
        return driver;
    }

    public static String captureScreenShotWithPath(WebDriver driver, String screenShotName) {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File file = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String fileName = System.getProperty("user.dir") + "/ScreenShots/" + screenShotName + "_" + dateName + ".png";
        try {
            FileUtils.copyFile(file, new File(fileName));
            getLog("ScreenShot Captured");
        } catch (IOException e) {
            getLog("Exception while taking ScreenShot " + e.getMessage());
        }
        return fileName;
    }

    public void waitFor(int seconds) throws InterruptedException {
    Thread.sleep(1000*seconds);
    }
}
