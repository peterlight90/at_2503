package automationtesting;

import static utils.Constants.SAMPLE_FILE_PATH;

import java.sql.DriverManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import core.BaseTest;
import pages.automation_demo.AlertPage;
import pages.automation_demo.DatePickerPage;

/**
 * Test class for Automation Testing demo site.
 * Tests alert handling, window switching, and date picker interactions.
 */
public class ATTestingTest extends BaseTest {
     AlertPage alertPage;

     @BeforeEach
     public void setUpPages() {
         // Initialize page objects (BaseTest already initializes driverManager)
         alertPage = new AlertPage();
     }

     @Test
     @Tag("automationtesting")
     public void alertWithOK() {
         /**
          * Test case sample 01
          */
         logger.info("Step 1: Click 'Alert with OK' button to display an alert box");
         alertPage.clickAlertWithOKButton();

         logger.info("Step 2: Accept the alert box");
         alertPage.acceptAlert();
     }
     
     @Test
     @Tag("automationtesting")
     public void alertWithOKCancel() {
         /**
          * Test case sample 02
          */
         logger.info("Automation Testing - Alert with OK & Cancel tab");
         alertPage.selectAlertTab("Alert with OK & Cancel");

         logger.info("Step 1: Click 'Alert with OK' button to display an alert box");
         alertPage.clickAlertWithOKCancelButton();

         logger.info("Step 2: Dismiss the alert box");
         alertPage.dismissAlert();

         logger.info("Step 3: Verify the alert was dismissed successfully");
         alertPage.verifyAlertDismissedMessage("You Pressed Cancel");
     }

     @Test
     @Tag("automationtesting")
     public void testSwitchWindow01() {
         logger.info("Automation Testing - Alert with OK & Cancel tab");
         alertPage.openSite("https://demo.automationtesting.in/Windows.html");

         alertPage.clickNewTabWindowButton();
         alertPage.verifyTitle("Selenium");
        
         alertPage.switchBackToOriginalWindow();
         alertPage.verifyTitle("Frames & windows");
     }


    @Test
    @Tag("automationtesting")
    public void testDataPickerDisable() {
        String dateToSelect = "02/10/2026";
        DatePickerPage datePickerPage = new DatePickerPage();

        logger.info("Step 1: Select date '{}' from the disabled date picker", dateToSelect);
        datePickerPage.selectDateDisable(dateToSelect);

        logger.info("Step 2: Verify the date '{}' is disabled and cannot be selected", dateToSelect);
        datePickerPage.verifySelectedDateDisable(dateToSelect);
    }

    @Test
    @Tag("automationtesting")
    public void testUploadFile() {
        logger.info("Automation Testing - File Upload Test");
        alertPage.openSite("https://demo.automationtesting.in/FileUpload.html");

        String filePath = utils.Constants.SAMPLE_FILE_PATH;
        logger.info("Step 1: Upload file from path: {}", filePath);
        alertPage.uploadFile(SAMPLE_FILE_PATH);

        logger.info("Step 2: Verify the file was uploaded successfully");
        alertPage.verifyFileUploaded("sample.jpg");
    }


    @Test
    @Tag("automationtesting")
    public void testDownloadFile() {
         String expectedContent = "This is a sample text file for download testing.";

        logger.info("Automation Testing - File Upload Test");
        alertPage.openSite("https://demo.automationtesting.in/FileDownload.html");

        logger.info("Step 1: Enter text to generate the downloadable file");
        alertPage.enterTextForDownload(expectedContent);

        logger.info("Step 2: Click 'Generate File' button");
        alertPage.clickGenerateFileButton();

        logger.info("Step 2: Click 'Download File' button");
        alertPage.clickDownloadButton();
        alertPage.waitForFileDownload("info.txt", 15);

        logger.info("VP: Verify the downloaded file content");
        alertPage.verifyDownloadedFileContent("info.txt", expectedContent);


    }
}
