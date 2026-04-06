package hoytsQaTest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class standAlone {
	@Test
	public void pickGiftCard() throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver.get("https://shop.hoyts.com.au");
		driver.manage().window().maximize();
		String title = "Super Mario";
		driver.findElement(By.xpath(String.format("//div[contains(@class, 'product-item') and .//a[contains(@title, '%s')]] "
				+ "//div[contains(@class, 'select-wrapper--desktop')]", title))).click();
		driver.findElement(By.xpath(String.format("//div[contains(@class, 'product-item') and .//a[contains(@title, '%s')]] //ul[contains(@class, 'options')] "
				+ "//li[contains(@class, 'option') and normalize-space() = '$50']", title))).click();
		driver.findElement(By.xpath(String.format("//div[contains(@class, 'product-item') and .//a[contains(@title, '%s')]] //button[@type = 'submit']", title))).click();
		driver.findElement(By.cssSelector("span.icon-cart")).click();
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,500)");
		String chosenTitle = driver.findElement(By.xpath(String.format("//section[contains(@class, 'minicart__list-wrapper')] "
				+ "//a[contains(normalize-space(), '%s')]", title))).getText();
		Assert.assertTrue(chosenTitle.contains(title));
		driver.quit();
	}


}
