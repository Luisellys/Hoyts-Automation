package QA_Assessment.hoyts_qa_test;

import org.testng.Assert;
import org.testng.annotations.Test;

import hoytsShopPageObjects.giftCardsPage;
import testComponents.hoytsShopBaseTest;

public class giftCardTests extends hoytsShopBaseTest {

    @Test
    public void pickMostPopularGiftCardFromMainPage() {

        String giftCardTitle = "Easter E-Gift Card";
        String giftCardValue = "50";
        mainPage.pickPopularGiftCard(giftCardTitle, giftCardValue);
        String chosenGiftCard = mainPage.confirmGiftCard(giftCardTitle);
        Assert.assertEquals(chosenGiftCard, giftCardTitle);
    }
    @Test
    public void pickFromGiftCardsPage() {

        String giftCardTitle = "Birthday E-Gift Card";
        String giftCardValue = "250";
        giftCardsPage giftCardsPage = mainPage.goToGiftCards();
        giftCardsPage.pickGiftCard(giftCardTitle, giftCardValue);
        String chosenGiftCard = giftCardsPage.confirmGiftCard(giftCardTitle);
        Assert.assertEquals(chosenGiftCard, giftCardTitle);
    }
}