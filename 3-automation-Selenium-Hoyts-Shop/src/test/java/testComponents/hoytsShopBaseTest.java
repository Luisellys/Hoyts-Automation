package testComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import hoytsShopPageObjects.mainPageHoytsShop;
import io.github.bonigarcia.wdm.WebDriverManager;

public class hoytsShopBaseTest {

    public WebDriver driver;
    public mainPageHoytsShop mainPage;
    public Properties prop;
    

    public WebDriver initializedDriver() throws IOException {

        prop = new Properties();
        FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "\\src\\main\\java\\resources\\globalData.properties"
        );
        prop.load(fis);

        String browserName = prop.getProperty("browser");

        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize(); 
        //Mobile Chrome/ Pixel 5 Viewport Width:  393
        //driver.manage().window().setSize(new Dimension(393, 851));
        //Mobile Safari / iPhone 12 - Viewport Width:  390
        //driver.manage().window().setSize(new Dimension(390, 844));

        return driver;
    }

    @BeforeMethod
    public mainPageHoytsShop launchApplication() throws IOException {
        driver = initializedDriver();
        driver.manage().deleteAllCookies();
        mainPage = new mainPageHoytsShop(driver);
        mainPage.goTo();
        return mainPage;
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.quit();
        }
    }
}