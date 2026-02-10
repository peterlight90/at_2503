package pages;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import core.BasePage;

class AlertPageSelector {
    public static final By btnAlertWithOK = By.id("OKTab");
    public static final By btnAlertWithOKCancel = By.id("CancelTab");
    public static final By txtMessageCancelTab = By.xpath("//div[@id='CancelTab']/p");
    public static final By tabAlertWithOKCancel = By.xpath("//a[@href='#CancelTab']");
    public static final By tabAlertWithTextbox = By.xpath("//a[@href='#Textbox']");
}

public class AlertPage extends BasePage {

    AlertPageSelector selector = new AlertPageSelector();

    public void acceptAlert() {
        logger.info("[Alert Page]: Accepting alert");
        Alert alert = switchToAlert();
        acceptAlertAction(alert);
    }

    public void dismissAlert() {
        logger.info("[Alert Page]: Dismissing alert");
        Alert alert = switchToAlert();
        dismissAlertAction(alert);
    }

    public void selectAlertTab(String tabName) {
        logger.info("[Alert Page]: Selecting alert tab: {}", tabName);
        if (tabName.equals("Alert with OK & Cancel")) {
            clickElement(AlertPageSelector.tabAlertWithOKCancel);
        } else if (tabName.equals("Alert with Textbox")) {
            clickElement(AlertPageSelector.tabAlertWithTextbox);
        }
    }

    public void clickAlertWithOKButton() {
        logger.info("[Alert Page]: Clicking 'Alert with OK' button");
        clickElement(AlertPageSelector.btnAlertWithOK);
    }

    public void clickAlertWithOKCancelButton() {
        logger.info("[Alert Page]: Clicking 'Alert with OK & Cancel' button");
        clickElement(AlertPageSelector.btnAlertWithOKCancel);
    }
    
    public String getAlertDismissedMessage() {
        logger.info("[Alert Page]: Verifying alert dismissed message");
        return getElementText(AlertPageSelector.txtMessageCancelTab);
    }
}
