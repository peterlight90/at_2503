package tests;
import core.DriverManager;
import core.TestSettings;
import io.qameta.allure.Allure;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Assert;
import org.testng.annotations.*;
import core.BasePage;
import utils.Helper;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.nio.file.Files;

public class BaseTest extends Helper {
    protected DriverManager driverManager;
    protected BasePage basePage;

    /**
     * Setup executed before each test method.
     * Initializes WebDriver instance for the test.
     *
     * @param context TestNG test context
     * @throws MalformedURLException if hub URL is malformed
     */
    @BeforeClass
    public void setup(ITestContext context) throws MalformedURLException {
        logger.info("########################################");
        logger.info("[Setup] Starting test: {}", context.getName());
        logger.info("[Setup] Test class: {}", context.getCurrentXmlTest().getName());
        logger.info("[Setup] Environment: {}", TestSettings.TEST_ENV);
        logger.info("[Setup] Browser: {}", TestSettings.BROWSER_TYPE);
        logger.info("========================================");
        
        driverManager = new DriverManager("chrome");
        basePage = new BasePage();
        basePage.navigateTo(TestSettings.BASE_URL);
    }
    
    @BeforeTest
    public void beforeTest(ITestContext context) {
        String testName = context.getName();
        logger.info("[BeforeTest] Running test: " + testName);
    }

    @BeforeMethod
    public void beforeMethod(ITestContext context) {
        String methodName = context.getCurrentXmlTest().getName();
        logger.info("[BeforeMethod] Starting method: " + methodName);
    }

    @AfterMethod
    public void afterMethod(ITestContext context, ITestResult result) {
        String methodName = context.getCurrentXmlTest().getName();
        logger.info("[AfterMethod] Finished method: " + methodName);
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test Failed: " + result.getTestName() + " - " + result.getName());

            // 2. Thực hiện chụp màn hình
            if (DriverManager.getDriver() != null) {
                String screenshotPath = captureScreenshot(result.getName()+"_afterMethod");
                // Attach screenshot to Allure report
                try {
                    byte[] screenshotBytes = Files.readAllBytes(Paths.get(screenshotPath));
                    Allure.addAttachment("Screenshot on Failure", "image/png", new java.io.ByteArrayInputStream(screenshotBytes), "png");
                } catch (java.io.IOException e) {
                    logger.error("Failed to attach screenshot to Allure report", e);
                }
            }
        }
    }

    @AfterClass
    public void teardown(ITestContext context){
        logger.info("========================================");
        try{
            if (driverManager != null) {
                driverManager.quit();
                logger.info("[BaseTest]: WebDriver quit successfully");
            }
        } catch(Exception e){
            logger.error("[BaseTest]: Error during test teardown", e);
        }
        logger.info("########################################");
    }
    
    // Assertion Helpers
    /**
     * Verifies that a condition is true and logs the result.
     * @param condition
     * @param message
     */
    protected void verifyTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
        logger.info("[ASSERTION PASSED] " + message);
    }

    /**
     * Verifies that a condition is false and logs the result.
     * @param condition
     * @param message
     */
    protected void verifyFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
        logger.info("[ASSERTION PASSED] " + message);
    }

    /**
     * Verifies that two objects are equal and logs the result.
     * @param actual
     * @param expected
     * @param message
     */
    protected void verifyEquals(Object actual, Object expected, String message) {
        Assert.assertEquals(actual, expected, message);
        logger.info("[ASSERTION PASSED] " + message);
    }
}
