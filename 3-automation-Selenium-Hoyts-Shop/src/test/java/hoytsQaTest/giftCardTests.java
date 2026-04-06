package hoytsQaTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import hoytsShopPageObjects.giftCardsPageHoytsShop;
import testComponents.hoytsShopBaseTest;

public class giftCardTests extends hoytsShopBaseTest {

    @Test
    public void pickMostPopularGiftCardFromMainPage() {

        String giftCardTitle = "Super Mario";
        String giftCardValue = "50";
        mainPage.pickPopularGiftCard(giftCardTitle, giftCardValue);
        String chosenGiftCard = mainPage.confirmGiftCard(giftCardTitle);
        Assert.assertTrue(chosenGiftCard.contains(giftCardTitle));
    }
    @Test
    public void pickFromGiftCardsPage() {

        String giftCardTitle = "Michael E-Gift Card";
        String giftCardValue = "250";
        giftCardsPageHoytsShop giftCardsPage = mainPage.goToGiftCards();
        giftCardsPage.pickGiftCard(giftCardTitle, giftCardValue);
        String chosenGiftCard = giftCardsPage.confirmGiftCard(giftCardTitle);
        Assert.assertTrue(chosenGiftCard.contains(giftCardTitle));
    }
}