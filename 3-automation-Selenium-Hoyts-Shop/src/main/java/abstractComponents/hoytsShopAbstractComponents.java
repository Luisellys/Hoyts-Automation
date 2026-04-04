package abstractComponents;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import hoytsShopPageObjects.giftCardsPageHoytsShop;

public class hoytsShopAbstractComponents {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public hoytsShopAbstractComponents(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // ================= LOCATORS =================
    By hamburgerMenu = By.cssSelector("span.icon-menu");
    By openedMenu = By.cssSelector("nav.header__nav");
    By giftCards = By.xpath("//span[normalize-space()='Gift Cards']");
    By openedCart = By.cssSelector("div.minicart");
    By cartMobile = By.cssSelector("span.header__count");
    By cartDesktop = By.cssSelector("button.header__cart-btn");
    
    // =================== Locator Helpers =====================
    // == Product Container ====================================
    protected By productContainer(String title) {
        return By.xpath("//main[@id='MainContent']//div[contains(@class,'product-item') " +
                "and .//a[contains(@title,'" + title + "')]]");
    }
    // == Selectors ============================================
    protected By dropdownSelector() {
        return By.cssSelector("div[data-label='Value:']");
    }

    protected By giftCardAmountSelector(String value) {
        return By.xpath(".//ul[contains(@class,'options')]//li[contains(@class,'option') and contains(text(),'$" + value + "')]");
    }

    protected By addToCartButtonSelector() {
        return By.cssSelector("button[type='submit']");
    }
    // ====================== Utilities ========================
    // == Viewport Detection ===================================
    public boolean hasHamburgerMenu() {
        int viewportWidth = ((Long)((JavascriptExecutor)driver)
                .executeScript("return window.innerWidth;")).intValue();

        System.out.println("Viewport width: " + viewportWidth);
        return viewportWidth <= 1278;
    }

    // == Safe Click ===========================================
    public void safeClick(By locator) {
        WebElement element = waitForElementToBeVisible(driver.findElement(locator));

        try {
            waitForElementToBeClickable(element);
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
            waitForElementToBeClickable(element);
            element.click();
        }
    }

    // == Scrolling ==============================================
    public WebElement scrollUntilProductVisible(String title) {

        By containerLocator = productContainer(title);

        for (int i = 0; i < 15; i++) {

            if (driver.findElements(containerLocator).size() > 0) {
                WebElement container = driver.findElement(containerLocator);

                if (container.isDisplayed()) {
                    ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].scrollIntoView({block:'center'});", container);
                    return container;
                }
            }

            ((JavascriptExecutor) driver)
                    .executeScript("window.scrollBy(0, window.innerHeight)");
            
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }

        throw new RuntimeException("Gift Card: '" + title + "' not available");
    }
    
    // ========================= Action Methods =======================

    // == Go to GiftCards Page ========================================
    public giftCardsPageHoytsShop goToGiftCards() {

        if (hasHamburgerMenu()) {
            try {
                safeClick(hamburgerMenu);
                waitForElementToBeVisible(driver.findElement(openedMenu));
            } catch (Exception e) {}
        }

        safeClick(giftCards);

        waitForURL15("gift-cards");
        By firstProduct = By.cssSelector("div.product-item");
        waitForElementToBeVisible(driver.findElement(firstProduct));

        ((JavascriptExecutor) driver)
                .executeScript("window.scrollBy(0,100)");
        
        giftCardsPageHoytsShop giftCardsPage = new giftCardsPageHoytsShop(driver);
    	return giftCardsPage;
    	
    }
    // == Select Gift Cards ==============================================
    public void selectGiftCard(WebElement container, String value) {
        int viewportWidth = ((Long)((JavascriptExecutor) driver)
                .executeScript("return window.innerWidth;")).intValue();

        // If <select> is visible, use standard select dropdown
        if (viewportWidth <= 1023) {

            WebElement select = container.findElement(By.cssSelector("select.select"));
            waitForElementToBeVisible(select);

            Select dropdown = new Select(select);
            dropdown.selectByValue("$" + value + "|1");

        } else {

            WebElement dropdown = container.findElement(dropdownSelector());
            dropdown.click();

            WebElement amount = container.findElement(giftCardAmountSelector(value));
            waitForElementToBeVisible(amount);
            waitForElementToBeClickable(amount);

            amount.click();
        }

        WebElement addToCart = container.findElement(addToCartButtonSelector());

        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView({block:'center'});", addToCart);

        waitForElementToBeClickable(addToCart);
        addToCart.click();
    }

    // == Confirm GiftCard ==============================================
    public String confirmGiftCard(String title) {

        By cartSelector = hasHamburgerMenu() ? cartMobile : cartDesktop;

        safeClick(cartSelector);

        waitForElementToBeVisible(driver.findElement(openedCart));

        WebElement item = waitForElementToBeVisible(driver.findElement(By.xpath(
        		"//section[contains(@class,'minicart')]//a[contains(normalize-space(),'" + title + "')]")
                ))
                ;

        String text = item.getText();

        if (text.trim().isEmpty()) {
            throw new RuntimeException("Gift Card: '" + title + "' not found in cart");
        }

        return text;
    }
    // ============================= Waits ===============================
    public WebElement waitForElementToBeVisible(WebElement Locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(15))
                 .until(ExpectedConditions.visibilityOf(Locator));  
     }
     
    public WebElement waitForElementToBeClickable(WebElement Locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(Locator));  
    }
    
    public void waitForURL15(String URL) {
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains(URL));
    }
}