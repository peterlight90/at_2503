package pages.automationtesting;

import core.BasePage;

public class InteractionPage extends BasePage {
    private final String URL = "https://www.automationtesting.co.uk/interaction.html";

    public void openPage() {
        openSite(URL);
    }
    
}
