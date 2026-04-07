package pages.automation_demo;
import org.openqa.selenium.By;
import core.BasePage;

import static automationtesting.Constants.AUTOMATION_DEMO_DATE_PICKER_URL;

import java.time.LocalDate;

class DatePickerPageSelector {
    // Disabled Date Picker Textbox
    public static final By txtDateDisable = By.id("datepicker1");
    public static final By btnNextMonth = By.xpath("//a[@title='Next']");
    public static final By btnPrevMonth = By.xpath("//a[@title='Prev']");
    public static final By lblMonthYear = By.className("ui-datepicker-title");
    public static final By tblDatePicker = By.className("ui-datepicker-calendar");
    public static final By itmDatePickerDay(int day) {
        return By.xpath("//table[@class='ui-datepicker-calendar']//a[text()='" + day + "']");
    }
}

public class DatePickerPage extends BasePage {

    public DatePickerPage() {
        super();
        openSite(AUTOMATION_DEMO_DATE_PICKER_URL);
    }

    /**
     * Select a date from the disabled date picker
     * @param date The date to select in format "MM/DD/YYYY" (parsed to LocalDate)
     */
    public void selectDateDisable(String date) {
        logger.info("Selecting date: {}", date);
        // Get expected date
        LocalDate localDate = convertStringToDate(date);
        
        // Open the date picker by clicking on the textbox
        clickButton(DatePickerPageSelector.txtDateDisable);

        // Navigate to the correct month and year
        while (true) {
            String[] monthYear = getElementText(DatePickerPageSelector.lblMonthYear).split(" ");
            int displayedMonth = convertMonthNameToNumber(monthYear[0]);
            int displayedYear = Integer.parseInt(monthYear[1]);

            if (displayedYear > localDate.getYear() || 
                (displayedYear == localDate.getYear() && displayedMonth > localDate.getMonthValue())) {
                clickButton(DatePickerPageSelector.btnPrevMonth);
            } else if (displayedYear < localDate.getYear() || displayedMonth < localDate.getMonthValue()) {
                clickButton(DatePickerPageSelector.btnNextMonth);
            } else {
                break;
            }
        }
        // Select the day
        clickButton(DatePickerPageSelector.itmDatePickerDay(localDate.getDayOfMonth()));
        waitForElementInvisible(DatePickerPageSelector.tblDatePicker);
        logger.info("Date selected: {}", date);
    }

    
    public void verifySelectedDateDisable(String date) {
        logger.info("Verifying selected date is disabled: {}", date);
       String crrDate = getElementValue(DatePickerPageSelector.txtDateDisable);
       verifyEquals(date, crrDate, String.format("The date %s is not disabled as expected, current date is %s", date, crrDate));
    }
}