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

    By hamburgerMenu = By.cssSelector("button[aria-label='Menu']");
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

    public boolean isMobileView() {
        try {
            return driver.findElement(hamburgerMenu).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ================= SEARCH =================
    public void searchMovie(String movieName) {

        boolean isMobile = isMobileView();
        By searchField;

        if (isMobile) {
            System.out.println("Using MOBILE search");
            waitForElementToAppear(hamburgerMenu);
            // Open hamburger menu every time
            try {
                driver.findElement(hamburgerMenu).click();
                waitForElementToBeVisible(openedMenu);
            } catch (Exception ignored) {}

            searchField = searchButtonMobile;

            // Wait for visibility
            waitForElementToBeVisible(searchField);

        } else {
            System.out.println("Using DESKTOP search");

            searchField = searchButtonDesktop;

            try {
                driver.findElement(searchField).click();
            } catch (Exception ignored) {}

            waitForElementToBeVisible(searchField);
        }

        WebElement element = driver.findElement(searchField);
        //Perform Search
        element.clear();
        element.sendKeys(movieName);
        element.sendKeys(Keys.ENTER);
    }
    // ================= CONFIRM MOVIE =================

    public Boolean confirmMovie(String movieName) {

        boolean isMobile = isMobileView();
        String searchText = movieName.toLowerCase();

        By resultsLocator = isMobile ? mobileResults : desktopResults;
        By titleLocator = isMobile ? mobileTitle : desktopTitle;
        By descLocator = isMobile ? mobileDescription : desktopDescription;

        waitForElementToBeVisible(resultsLocator);

        List<WebElement> results = driver.findElements(resultsLocator);

        if (results.isEmpty()) {
            return false;
        }

        return results.stream().anyMatch(movie -> {
            String title = movie.findElement(titleLocator).getText().toLowerCase();
            String desc = movie.findElement(descLocator).getText().toLowerCase();

            return title.contains(searchText) || desc.contains(searchText);
        });
    }
    // ================= NO RESULTS =================

    public String confirmNoResultsMessage() {

        By messageLocator = isMobileView()
                ? mobileNoResultsMessage
                : desktopNoResultsMessage;

        waitForElementToBeVisible(messageLocator);

        return driver.findElement(messageLocator).getText();
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


}