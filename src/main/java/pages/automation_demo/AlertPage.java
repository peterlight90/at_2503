package pages.automation_demo;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import core.BasePage;

import static automationtesting.Constants.AUTOMATION_DEMO_ALERTS_URL;
import static automationtesting.Constants.DOWNLOAD_FOLDER_PATH;

import java.nio.file.Paths;

class AlertPageSelector {
    public static final By btnAlertWithOK = By.id("OKTab");
    public static final By btnAlertWithOKCancel = By.id("CancelTab");
    public static final By txtMessageCancelTab = By.xpath("//div[@id='CancelTab']/p");
    public static final By tabAlertWithOKCancel = By.xpath("//a[@href='#CancelTab']");
    public static final By tabAlertWithTextbox = By.xpath("//a[@href='#Textbox']");
//    NEW TAB WINDOW SELECTOR
    public static final By btnNewTabWindow = By.xpath("//div[@id='Tabbed']/a");
    // UPLOAD FILE SELECTOR
//    public static final By txtBrowserFile = By.xpath("//div[span[contains(text(), 'Browse')]]/input/..");
    public static final By txtBrowserFile = By.id("input-4");
    public static final By imgUploadedFile = By.xpath("(//div[@class='kv-file-content']//img)[1]");

    public static final By txtEnterTextForDownload = By.id("textbox");
    public static final By btnGenerateFile = By.id("createTxt");
    public static final By btnDownloadFile = By.id("link-to-download");
}

public class AlertPage extends BasePage {

    public AlertPage() {
        super();
        openSite(AUTOMATION_DEMO_ALERTS_URL);
    }

    public void acceptAlert() {
        logger.info("Accepting alert");
        Alert alert = switchToAlert();
        acceptAlertAction(alert);
    }

    public void dismissAlert() {
        logger.info("Dismissing alert");
        Alert alert = switchToAlert();
        dismissAlertAction(alert);
    }

    public void clickAlertWithOKButton() {
        logger.info("Clicking 'Alert with OK' button");
        clickButton(AlertPageSelector.btnAlertWithOK);
    }

    public void clickAlertWithOKCancelButton() {
        logger.info("Clicking 'Alert with OK & Cancel' button");
        clickButton(AlertPageSelector.btnAlertWithOKCancel);
    }

    public void verifyAlertDismissedMessage(String expectedMessage) {
        logger.info("Verifying alert dismissed message contains: {}", expectedMessage);

        String crrMsg = getElementText(AlertPageSelector.txtMessageCancelTab);
        verifyEquals(expectedMessage, crrMsg, String.format("The message %s displays intead of %s", crrMsg, expectedMessage));
    }

    public void selectAlertTab(String tabName) {
        logger.info("Selecting alert tab: {}", tabName);
        if (tabName.equals("Alert with OK & Cancel")) {
            clickButton(AlertPageSelector.tabAlertWithOKCancel);
        } else if (tabName.equals("Alert with Textbox")) {
            clickButton(AlertPageSelector.tabAlertWithTextbox);
        }
    }

    // NEW WINDOW METHODS
    public void clickNewTabWindowButton() {
        logger.info("Clicking 'New Tab / Window' button");
        clickButton(AlertPageSelector.btnNewTabWindow);
        swithToNewWindow();
    }

    // UPLOAD FILE
    public void uploadFile(String filePath) {
        logger.info("Uploading file: {} to element: {}", filePath);
        enterTextWithoutWait(AlertPageSelector.txtBrowserFile, filePath);
    }

    public void verifyFileUploaded(String expectedFileName) {
        logger.info("Verifying file uploaded: {}", expectedFileName);
        String crrFile = getElementAttribute(AlertPageSelector.imgUploadedFile, "title");
        verifyEquals(expectedFileName, crrFile, String.format("The uploaded file %s does not match expected %s", crrFile, expectedFileName));
    }

    public void enterTextForDownload(String text) {
        logger.info("Entering text for download: {}", text);
        enterText(AlertPageSelector.txtEnterTextForDownload, text);
    }

    public void clickGenerateFileButton() {
        logger.info("Clicking 'Generate File' button");
        clickButton(AlertPageSelector.btnGenerateFile);
    }

    public void clickDownloadButton() {
        logger.info("Clicking 'Download' button");
        clickButton(AlertPageSelector.btnDownloadFile);
    }


    public void verifyDownloadedFileContent(String fileName, String expectedFileContent) {
        logger.info("Verifying downloaded file content: {}", fileName);
        String downloadedFilePath = Paths.get(DOWNLOAD_FOLDER_PATH, fileName).toString();
        String fileContent = readFileContent(downloadedFilePath);
        verifyTrue(fileContent.contains(expectedFileContent), "The downloaded file content does not match the expected content.");

    }
}