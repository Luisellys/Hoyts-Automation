package hoytsPageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractComponents.hoytsShopAbstractComponents;

public class mainPage extends hoytsShopAbstractComponents {

    WebDriver driver;

    public mainPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
 // ================= LOCATORS =================


 // ================= LOCATOR HELPER METHODS =================
     
    private By getDropdown(String title) {
        return By.xpath(String.format(
            "//div[contains(@class,'product-item') and .//a[contains(@title,'%s')]] //div[@class='select' and normalize-space() = 'Value: $30']",
            title
        ));
    }

    private By getGiftCardAmount(String title, String value) {
        return By.xpath(String.format(
            "//div[contains(@class,'product-item') and .//a[contains(@title,'%s')]]" +
            " //li[contains(@class,'option') and normalize-space() = '$%s']",
            title, value
        ));
    }

    private By getAddToCartButton(String title) {
        return By.xpath(String.format(
            "//div[contains(@class, 'product-item') and .//a[contains(@title, '%s')]] //button[@type='submit']",
            title
        ));
    }

// ================= SELECT MOST POPULAR GIFTCARD =================
    public void pickPopularGiftCard(String title, String value) {

        By dropdown = getDropdown(title);
        waitForElementToBeVisible(dropdown);
        driver.findElement(dropdown).click();

        By giftCardAmount = getGiftCardAmount(title, value);
        waitForElementToBeVisible(giftCardAmount);
        driver.findElement(giftCardAmount).click();

        By addToCart = getAddToCartButton(title);
        driver.findElement(addToCart).click();
    }
        
    
 // ================= GO TO WEBSITE =================

    
    public void goTo() {
        driver.get("https://shop.hoyts.com.au/");
    }
    
    
    
    
}