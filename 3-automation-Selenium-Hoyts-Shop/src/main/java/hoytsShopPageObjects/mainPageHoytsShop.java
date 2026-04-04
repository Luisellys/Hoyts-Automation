package hoytsShopPageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import abstractComponents.hoytsShopAbstractComponents;

public class mainPageHoytsShop extends hoytsShopAbstractComponents {

    public mainPageHoytsShop(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
  
    
  // ========================= Action Methods =======================

 // == Go to Website ================================================
    
    public void goTo() {
        driver.get("https://shop.hoyts.com.au/");
    }
    
// == Scroll to the Gift Card in Carousel (if applicable) =========================
    
    public void scrollInCarousel(String title, String carousel) {
    	int viewport = ((Long)((JavascriptExecutor)driver).executeScript("return window.innerWidth")).intValue();
    	if (viewport > 767) return;
     
    	String sectionTitle = "";
    	if (carousel.equalsIgnoreCase("gift card")) {
    		sectionTitle = "Most Popular Gift Cards";  // For Gift Cards
    	}
    	else if (carousel.equalsIgnoreCase("voucher")) {
            sectionTitle = "Most Popular Vouchers";  // For Vouchers
    	}
    	
    	WebElement section = waitForElementToBeVisible(driver.findElement(By.xpath(
    			"//section[.//header[contains(.,'" + sectionTitle + "')]]")));
    	((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", section);
    	
    	WebElement slider = section.findElement(By.cssSelector(".keen-slider"));
    	WebElement rightArrow = section.findElement(By.cssSelector(".keen-slider__arrow--right"));
    	WebElement leftArrow = section.findElement(By.cssSelector(".keen-slider__arrow--left"));
    	
    	By productLocator = By.xpath(".//a[contains(@title, '"+ title +"')]");
    	
    	for (int i = 0; i < 10; i++) {

    	    if (slider.findElements(productLocator).size() > 0) {
    	        WebElement product = slider.findElement(productLocator);

    	        if (product.isDisplayed()) {

    	            ((JavascriptExecutor) driver)
    	                .executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", product);

    	            return;
    	        }
    	    }

    	    ((JavascriptExecutor) driver)
    	        .executeScript("arguments[0].click();", rightArrow);

    	    try { Thread.sleep(500); } catch (InterruptedException e) {}
    	}
    	throw new RuntimeException("Product: "+ title + " not available");
    }


// == Pick Most Popular Gift Cards ===================================
    public void pickPopularGiftCard(String title, String value) {

        ((JavascriptExecutor) driver)
                .executeScript("window.scrollBy(0,100)");
        
        scrollInCarousel(title, "gift card");

        By containerLocator = productContainer(title);

        if (driver.findElements(containerLocator).size() == 0) {
            throw new RuntimeException("Gift Card '" + title + "' not found in most popular");
        }

        WebElement container = waitForElementToBeVisible(driver.findElement(containerLocator));

        selectGiftCard(container, value);
    }
}