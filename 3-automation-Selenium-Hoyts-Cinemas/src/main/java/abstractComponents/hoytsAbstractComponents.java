package abstractComponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

public class hoytsAbstractComponents {

    WebDriver driver;

    public hoytsAbstractComponents(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ================= LOCATORS =================

    By hamburgerMenu = By.cssSelector(".header__hamburger-icon");
    By openedMenu = By.id("dash__nav");

    // MOBILE
    By searchButtonMobile = By.cssSelector(".dash__search-input");
    By mobileResults = By.cssSelector(".dash__search-item");
    By mobileTitle = By.cssSelector(".dash__search-title");
    By mobileDescription = By.cssSelector(".dash__search-description");
    By mobileNoResultsMessage = By.cssSelector(".dash__search-heading");

    // DESKTOP
    By searchButtonDesktop = By.cssSelector(".header__search-input");
    By desktopResults = By.cssSelector(".header__search-item");
    By desktopTitle = By.cssSelector(".header__search-title");
    By desktopDescription = By.cssSelector(".header__search-description");
    By desktopNoResultsMessage = By.cssSelector(".header__search-heading");

    // ================= VIEW DETECTION =================

    public boolean hasHamburgerMenu() {
        int viewportWidth = ((Long)((JavascriptExecutor)driver)
                                .executeScript("return window.innerWidth;"))
                                .intValue();

        System.out.println("Viewport width: " + viewportWidth);

        return viewportWidth <= 1240;
    }
    // ================= SEARCH =================
    public void searchMovie(String movieName) {

        boolean burgerMenu = hasHamburgerMenu();
        By searchField;

        if (burgerMenu) {
            System.out.println("Using MOBILE search");
            // Open hamburger menu every time
            try {
            	waitForElementToBeVisible(hamburgerMenu).click();
                waitForElementToBeVisible(openedMenu);
            } catch (Exception ignored) {}

            searchField = searchButtonMobile;

            // Wait for visibility
            waitForElementToBeVisible(searchField);

        } else {
            System.out.println("Using DESKTOP search");

            searchField = searchButtonDesktop;

            try {
            	waitForElementToBeVisible(searchField).click();
            } catch (Exception e) {
            	System.out.println("Search click failed:" + e.getMessage());
            }
            waitForElementToBeVisible(searchField);
        }

        WebElement element = waitForElementToBeVisible(searchField);
        //Perform Search
        element.clear();
        element.sendKeys(movieName);
        element.sendKeys(Keys.ENTER);
    }
    // ================= CONFIRM MOVIE =================

    public Boolean confirmMovie(String movieName) {

        boolean hasHamburgerMenu = hasHamburgerMenu();
        String searchText = movieName.toLowerCase();

        By resultsLocator = hasHamburgerMenu? mobileResults : desktopResults;
        By titleLocator = hasHamburgerMenu ? mobileTitle : desktopTitle;
        By descLocator = hasHamburgerMenu ? mobileDescription : desktopDescription;

        waitForElementToBeVisible(resultsLocator);

        List<WebElement> results = driver.findElements(resultsLocator);

        if (results.isEmpty()) {
            return false;
        }

        return results.stream().allMatch(movie -> {
            String title = movie.findElement(titleLocator).getText().toLowerCase();
            String desc = movie.findElement(descLocator).getText().toLowerCase();

            return title.contains(searchText) || desc.contains(searchText);
        });
    }
    // ================= NO RESULTS =================

    public String confirmNoResultsMessage() {

        By messageLocator = hasHamburgerMenu()
                ? mobileNoResultsMessage
                : desktopNoResultsMessage;

        waitForElementToBeVisible(messageLocator);

        return driver.findElement(messageLocator).getText();
    }

    // ================= WAITS =================

    public WebElement waitForElementToBeVisible(By messageLocator) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(messageLocator));
       
    }


}