package abstractComponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import hoytsShopPageObjects.giftCardsPage;

public class hoytsShopAbstractComponents {

    WebDriver driver;

    public hoytsShopAbstractComponents(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ================= LOCATORS =================

    
    //HAMBURGER MENU
    By hamburgerMenu = By.cssSelector("span.icon-menu");
    By openedMenu = By.cssSelector("nav.header__nav");
    //MENU OPTIONS
    @FindBy(xpath = "//span[text()='Gift Cards']")
    WebElement giftCards;
    //CART
    By openedCart = By.xpath("//div[@class='minicart']");
    //MOBILE
    By cartMobile = By.cssSelector("span.header__count");
    //DESKTOP
    By cartDesktop = By.cssSelector("button.header__cart-btn");

    // ================= VIEW DETECTION =================

    public boolean isMobileView() {
        int viewportWidth = ((Long)((JavascriptExecutor)driver)
                                .executeScript("return window.innerWidth;"))
                                .intValue();

        System.out.println("Viewport width: " + viewportWidth);

        return viewportWidth <= 1278;
    }
    // ================= GO TO GIFTCARDS PAGE =================
    public giftCardsPage goToGiftCards() {
    	Boolean isMobile = isMobileView();
    	if(isMobile) {
    		try {
    			waitForElementToBeClickable(hamburgerMenu);
    			driver.findElement(hamburgerMenu).click();
    	    	waitForElementToBeVisible(openedMenu);
    		}
    		catch(Exception e) {	
    		}
    	}
    	giftCards.click();
    	giftCardsPage giftCardsPage = new giftCardsPage(driver);
    	return giftCardsPage;
    	
    }
    
    
    // ================= CONFIRM GIFTCARD =================
    public String confirmGiftCard(String title) {
    	Boolean isMobile = isMobileView();
    	By cartButton = isMobile ? cartMobile : cartDesktop;
    	waitForElementToBeClickable(cartButton);
    	driver.findElement(cartButton).click();
    	waitForElementToBeVisible(openedCart);
    	return driver.findElement(By.xpath(String.format("//section[contains(@class,'minicart__list-wrapper')] "
    			+ "//a[contains(normalize-space(),'%s')]", title))).getText();
    }
    

    // ================= WAITS =================

    public By waitForElementToAppear(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        return locator;
    }

    public By waitForElementToBeVisible(By messageLocator) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(messageLocator));
        return messageLocator;
    }
    public WebElement waitForElementToBeClickable(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }
  
    public void waitForElementToBeInvisible(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

}