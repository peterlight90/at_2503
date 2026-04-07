package pages.guru;

import org.openqa.selenium.By;

import automationtesting.Constants;
import core.BasePage;


class DragDropSelector {
    public static By getAmountXpath(String amount) {
        return By.xpath(String.format("(//a[contains(text(),'%s')])[last()]", amount));
    }

    public static By areCardAmount(String cardType) {
        return By.xpath(String.format("//td[h3[contains(text(), '%s')]]//div[@class='shoppingCart' and h3[contains(text(), 'Amount')]]//ol", cardType));
    }

    public static By areCardAccount(String cardType) {
        return By.xpath(String.format("//td[h3[contains(text(), '%s')]]//div[@class='shoppingCart' and h3[contains(text(), 'Account')]]//ol", cardType));
    }

    public static By lblAmountInCard(String cardType) {
        return By.xpath(String.format("//td[h3[contains(text(), '%s')]]//div[@class='shoppingCart' and h3[contains(text(), 'Amount')]]//li", cardType));
    }
}

public class DragDropPage extends BasePage {

    public DragDropPage() {
        super();
        openSite(Constants.GURU99_DRAG_DROP_URL);
    }

    public void dragDropAmountToCardType(String amount, String cardType) {
        logger.info("Dragging amount {} to card type {}", amount, cardType);
        dragAndDrop(DragDropSelector.getAmountXpath(amount), DragDropSelector.areCardAmount(cardType));
    }

    public void verifyAmountInCardType(String amount, String cardType) {
        logger.info("Verifying amount {} is displayed in card type {}", amount, cardType);
        String crrAmount = getElementText(DragDropSelector.lblAmountInCard(cardType)).trim();
        String errorMessage = String.format("Expected amount '%s' in card type '%s', but found '%s'", amount, cardType, crrAmount);
        verifyEquals(amount, crrAmount, errorMessage);
    }
    
}
