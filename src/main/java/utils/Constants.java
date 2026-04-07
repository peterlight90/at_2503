package automationtesting;

import java.nio.file.Paths;

public final class Constants {
    // Project Path Constants
    public static final String PROJECT_ROOT_DIR = System.getProperty("user.dir");
    public static final String PROJECT_ROOT_PATH = Paths.get(PROJECT_ROOT_DIR).toString();
    public static final String RESOURCES_PATH = Paths.get(PROJECT_ROOT_PATH, "src", "main", "resources").toString();
    public static final String JSON_DATA_PATH = Paths.get(RESOURCES_PATH, "TestData.json").toString();

    // XML Helper Constants
    public static final String CONFIG_FILE_PATH = Paths.get(RESOURCES_PATH, "config.xml").toString();
    public static final String BOOK_FILE_PATH = Paths.get(RESOURCES_PATH, "book.xml").toString();

    // CSV Helper Constants
    public static final String ADDRESSES_FILE_PATH = Paths.get(RESOURCES_PATH, "addresses.csv").toString();
    public static final String CSV_DELIMITER = ",";
    
    // Log Path Constants
    public static final String LOG_DIR = Paths.get(PROJECT_ROOT_PATH, "target", "logs").toString();
    public static final String AUTOMATION_LOG = Paths.get(LOG_DIR, "automation.log").toString();
    public static final String ERROR_LOG = Paths.get(LOG_DIR, "errors.log").toString();
    public static final String TEST_LOG = Paths.get(LOG_DIR, "test-execution.log").toString();

    // GURU99 Site Constants
    public static final String GURU99_BASE_URL = "https://demo.guru99.com";
    public static final String GURU99_TOOLTIPS_URL = GURU99_BASE_URL + "/test/tooltip.html";
    public static final String GURU99_DRAG_DROP_URL = GURU99_BASE_URL + "/test/drag_drop.html";

    // Automation Demo Site Constants
    public static final String AUTOMATION_DEMO_ALERTS_URL = "https://demo.automationtesting.in/Alerts.html";
    public static final String AUTOMATION_DEMO_DATE_PICKER_URL = "https://demo.automationtesting.in/Datepicker.html";

    // Upload File Constants
    public static final String SAMPLE_FILE_PATH = Paths.get(RESOURCES_PATH, "sample.jpg").toString();
    public static final String CURRENT_USERNAME = System.getProperty("user.name");
    public static final String DOWNLOAD_FOLDER_PATH = "/"+Paths.get("","Users", CURRENT_USERNAME,"Downloads").toString();
}
