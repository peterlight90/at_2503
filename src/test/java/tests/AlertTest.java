package tests;
import org.testng.annotations.Test;

import core.TestSettings;
import pages.LoginPage;
import pages.AlertPage;

public class AlertTest extends BaseTest {
    LoginPage loginPage;
    AlertPage alertPage;

    @Test
    public void tc01Abc() {
        /**
        * Test Case 01: Verify Alert with OK & Cancel functionality
        */
      alertPage = new AlertPage();

      logStep("1. Open Alert page");
      alertPage.navigateTo(TestSettings.AUTOMATION_DEMO_ALERTS_URL);

      logStep("2. Automation Testing - Alert with OK & Cancel tab");
      alertPage.selectAlertTab("Alert with OK & Cancel");

      logStep("3. Click 'Alert with OK' button to display an alert box");
      alertPage.clickAlertWithOKCancelButton();

      logStep("4. Dismiss the alert box");
      alertPage.dismissAlert();

      logStep("VP. Verify the alert was dismissed successfully");
      verifyEquals(alertPage.getAlertDismissedMessage(), "You Pressed Cancel", "Alert dismissed message is incorrect");
    }


}
