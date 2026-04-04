package hoytsShopPageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import abstractComponents.hoytsShopAbstractComponents;

public class giftCardsPageHoytsShop extends hoytsShopAbstractComponents {

    public giftCardsPageHoytsShop(WebDriver driver) {
        super(driver);
    }

    
    // ========================= Action Methods =======================

    public void pickGiftCard(String title, String value) {
        WebElement container = scrollUntilProductVisible(title);
        selectGiftCard(container, value);
    }
}