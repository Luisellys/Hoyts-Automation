package hoytsPageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import abstractComponents.hoytsAbstractComponents;

public class hoytsCinemaMainPage extends hoytsAbstractComponents {

    WebDriver driver;

    public hoytsCinemaMainPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void goTo() {
        driver.get("https://www.hoyts.co.nz/");
    }
}