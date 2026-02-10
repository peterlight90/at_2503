package utils;
import org.testng.ITestListener;
import org.testng.ITestResult;
import core.DriverManager;


public class ListenerTest extends Helper implements ITestListener {
    

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test Failed: " + result.getTestName() + " - " + result.getName());

        // 2. Thực hiện chụp màn hình
        if (DriverManager.getDriver() != null) {
            captureScreenshot(result.getName()+"_onTestFailure");
        }
    }

}