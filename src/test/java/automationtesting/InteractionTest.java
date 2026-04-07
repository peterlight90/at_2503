package automationtesting;

public class InteractionTest extends BaseTest {
    private InteractionPage interactionPage;

    @Test
    public void testOpenInteractionPage() {
        interactionPage = new InteractionPage();
        interactionPage.openPage();
    }
}
