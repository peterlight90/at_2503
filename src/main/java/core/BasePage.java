package core;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import utils.Helper;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BasePage provides common web interaction methods for all Page Objects.
 * All page classes should extend this class to inherit WebDriver access and utility methods.
 */
public class BasePage extends Helper {
    private String originalWindow;
    protected WebDriver driver;
    public BasePage(){
        driver = DriverManager.getDriver();
    }

    public void openSite() {
        logger.info("Navigating to URL: {}", TestSettings.BASE_URL);
        this.driver.get(TestSettings.BASE_URL);
        logger.info("Navigation to URL: {} completed", TestSettings.BASE_URL);
    }

    public void openSite(String url) {
        logger.info("Navigating to URL: {}", url);
        this.driver.get(url);
        logger.info("Navigation to URL: {} completed", url);
    }

    private WebElement findElement(By selector) {
        return getWait(TestSettings.WAIT_ELEMENT).until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    public WebDriverWait getWait(long waitTime) {
        return new WebDriverWait(this.driver, Duration.ofSeconds(waitTime));
    }

    protected void waitForElementInvisible(By selector) {
        getWait(TestSettings.WAIT_ELEMENT).until(ExpectedConditions.invisibilityOfElementLocated(selector));
    }

    protected List<WebElement> waitForElementsVisible(By selector) {
        return getWait(TestSettings.WAIT_ELEMENT).until(ExpectedConditions.visibilityOfAllElements(driver.findElements(selector)));
    }
    
    private WebElement waitForElementClickable(By selector) {
        return getWait(TestSettings.WAIT_ELEMENT).until(ExpectedConditions.elementToBeClickable(selector));
    }

    protected void enterText(By selector, String text) {
        logger.info("Entering text {}", text);
        findElement(selector).sendKeys(text);
    }

    protected void enterTextWithoutWait(By selector, String text) {
        logger.info("Entering text {}", text);
        this.driver.findElement(selector).sendKeys(text);
    }

    protected String getElementAttribute(By selector, String attributeName) {
        logger.info("Getting attribute {} from element {}", attributeName, selector);
        return findElement(selector).getDomAttribute(attributeName);
    }

   protected String getElementValue(By selector) {
       logger.info("Getting value from element {}", selector);
       WebElement element = findElement(selector);
       return element.getText().isEmpty() ? element.getDomProperty("value") : element.getText();
   }

    protected void clickButton(By selector) {
        logger.info("Clicking button {}", selector);
        waitForElementClickable(selector).click();
    }

    protected void executeJavaScript(String script) {
        logger.info("Executing JavaScript: {}", script);
        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        js.executeScript(script);
    }

    protected String getElementText(By selector) {
        String text = findElement(selector).getText();
        logger.info("Retrieved text '{}' from element {}", text, selector);
        return text;
    }

    protected void verifyTrue(boolean condition, String message) {
        logger.info("Verifying condition is true");
        assertTrue(condition, message);
    }

    protected void verifyFalse(boolean condition, String message) {
        logger.info("Verifying condition is false");
        assertFalse(condition, message);
    }

    protected void verifyEquals(Object expected, Object actual, String message) {
        logger.info("Verifying equality of expected and actual values");
        assertEquals(expected, actual, message);
    }

    protected void verifyElementVisible(By selector, String errorMessage) {
        logger.info("Verifying visibility of element {}", selector);
        try {
            getWait(TestSettings.WAIT_ELEMENT).until(ExpectedConditions.visibilityOfElementLocated(selector));
            logger.info("Element {} is visible", selector);
        } catch (Exception e) {
            logger.error("Element {} is not visible: {}", selector, errorMessage);
            throw new AssertionError(errorMessage);
        }
    }

    protected void hoverElement(By selector) {
        logger.info("Hovering over element {}", selector);
        WebElement element = findElement(selector);
        // Init action object
        Actions actions = new Actions(this.driver);

        // Perform hover action
        actions.moveToElement(element).perform();
    }

    protected void dragAndDrop(By sourceEleBy, By targetEleBy) {
        logger.info("Dragging element from {} to {}", sourceEleBy, targetEleBy);
        WebElement sourceElement = findElement(sourceEleBy);
        WebElement targetElement = findElement(targetEleBy);
        targetElement.getLocation();

        // Init action object
        Actions actions = new Actions(this.driver);

        // Perform drag and drop action
        actions.dragAndDrop(sourceElement, targetElement).perform();
        actions.moveToElement(sourceElement).clickAndHold().moveByOffset(0, 0).release().perform();
        actions.dragAndDropBy(sourceElement, 0, 0).pause(2000).perform();
    }
    // ALERT METHODS
    protected Alert switchToAlert() {
        logger.info("Switching to alert");
        return this.driver.switchTo().alert();
    }

    protected void acceptAlertAction(Alert alert) {
        logger.info("Accepting alert");
        alert.accept(); // Click the "OK" button on the alert
    }

    protected void dismissAlertAction(Alert alert) {
        logger.info("Dismissing alert");
        alert.dismiss();
    }

    protected void enterAlertText(Alert alert, String text) {
        logger.info("Entering text '{}' into alert", text);
        alert.sendKeys(text);
    }

    protected WebDriver swithToNewWindow(){
        logger.info("Switching to new window");
        this.originalWindow = this.driver.getWindowHandle();
        logger.info("Current window: {}", this.originalWindow);
        for (String windowHandle : this.driver.getWindowHandles()) {
            if (!windowHandle.equals(this.originalWindow)) {
                this.driver.switchTo().window(windowHandle);
                logger.info("Switched to new window: {}", windowHandle);
                return this.driver;
            }
        }
        logger.warn("No new window found to switch to");
        return this.driver;
    }
    
    protected void switchToIframe(By selector) {
        logger.info("Switching to iframe {}", selector);
        WebElement iframeElement = findElement(selector);
        this.driver.switchTo().defaultContent(); // Switch back to the main content before switching to the iframe
        this.driver.switchTo().frame(iframeElement);
    }

    protected WebDriver switchWindowByTitle(String title) {
        logger.info("Switching to window with title: {}", title);
        String originalWindow = this.driver.getWindowHandle();
        for (String windowHandle : this.driver.getWindowHandles()) {
            this.driver.switchTo().window(windowHandle);
            if (this.driver.getTitle().equals(title)) {
                logger.info("Switched to window with title: {}", title);
                return this.driver;
            }
        }
        logger.warn("No window with title '{}' found. Switching back to original window.", title);
        this.driver.switchTo().window(originalWindow);
        return this.driver;
    }

    public void verifyTitle(String expectedTitle) {
        logger.info("Verifying page title is: {}", expectedTitle);
        String actualTitle = this.driver.getTitle();
        verifyEquals(expectedTitle, actualTitle, String.format("Expected title '%s' but found '%s'", expectedTitle, actualTitle));
    }

    public void switchBackToOriginalWindowAndCloseOtherWindows() {
        logger.info("Switching back to original window: {}", this.originalWindow);
        Set<String> arrString = this.driver.getWindowHandles();
        for (String windowHandle : arrString) {
            if (!windowHandle.equals(this.originalWindow)) {
                this.driver.switchTo().window(windowHandle);
                logger.info("Switched to new window: {}", windowHandle);
                this.driver.close();
            }
        }
        this.driver.switchTo().window(this.originalWindow);
    }
}

