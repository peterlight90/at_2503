package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import static automationtesting.Constants.DOWNLOAD_FOLDER_PATH;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Helper {
    protected final static Logger logger = LogManager.getLogger("at_2503");

    public Helper(){
        try {
            logger.debug("Environment configuration loaded from .env file");
        } catch (Exception e) {
            logger.error("Failed to load .env configuration", e);
            throw new RuntimeException("Environment configuration failed", e);
        }
    }
    /**
     * Load JSON file and store as JSONObject or JSONArray
     * @param filePath path to JSON file
     */
    public static JSONObject loadJsonFile(String filePath) {
        try {
            String content = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
            return new JSONObject(content);
        } catch (Exception e) {
            logger.error("Failed to load JSON file: {}", filePath, e);
            return null;
        }
    }
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);

    public static LocalDate convertStringToDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (Exception e) {
            logger.error("Failed to parse date string: {}", dateStr, e);
            throw new IllegalArgumentException("Invalid date format: " + dateStr, e);
        }
    }

    public int convertMonthNameToNumber(String monthName) {
        try {
            DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH);
            return monthFormatter.parse(monthName).get(java.time.temporal.ChronoField.MONTH_OF_YEAR);
        } catch (Exception e) {
            logger.error("Failed to convert month name '{}' to number", monthName, e);
            throw new IllegalArgumentException("Invalid month name: " + monthName, e);
        }
    }

    public String readFileContent(String filePath) {
        try {
            return Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("Failed to read file content from: {}", filePath, e);
            return null;
        }
    }

    private void waitForFileExists(String filePath, int timeoutInSeconds) {
        logger.info("Waiting for file to exist: {} with timeout: {} seconds", filePath, timeoutInSeconds);
        int waited = 0;
        while (waited < timeoutInSeconds) {
            if (Files.exists(Paths.get(filePath))) {
                logger.info("File found: {}", filePath);
                return;
            }
            try {
//                Sleep 1 second before next check
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Thread interrupted while waiting for file: {}", filePath, e);
            }
            waited++;
        }
        logger.warn("Timeout reached. File not found: {}", filePath);
        throw new RuntimeException("File not found within timeout: " + filePath);
    }

    public void waitForFileDownload(String fileName, int timeoutInSeconds) {
        logger.info("Waiting for file download: {} with timeout: {} seconds", fileName, timeoutInSeconds);
        String downloadedFilePath = Paths.get(DOWNLOAD_FOLDER_PATH, fileName).toString();
        waitForFileExists(downloadedFilePath, timeoutInSeconds);
    }
}
