package com.seleniumadvanced.github;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class NegativeTests {
	WebDriver driver;


	@Parameters({ "browser" })
	@BeforeMethod
	private void setUp(@Optional("chrome") String browser) {
		// Create driver
		System.out.println("Create driver: " + browser);

		if (browser == "chrome") {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/webdrivers/chromedriver.exe");
			driver = new ChromeDriver();
		}else if(browser == "firefox") {
			System.setProperty("webdriver.gecko.driver", "src/test/resources/webdrivers/geckodriver.exe");
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
	}


	@Parameters({ "username", "password", "expectedMessage" })
	@Test(priority = 1)
	public void negativeTest(String username, String password, String expectedErrorMessage) {
		System.out.println("Starting negativeTest");

		// open main page
		String url = "http://the-internet.herokuapp.com/";
		driver.get(url);
		System.out.println("Main page is opened.");

		// Click on Form Authentication link
		driver.findElement(By.linkText("Form Authentication")).click();

		// enter username and password
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);

		// push log in button
		driver.findElement(By.className("radius")).click();

		// Verification
		String actualErrorMessage = driver.findElement(By.id("flash")).getText();
		
		System.out.println(actualErrorMessage);
		System.out.println(expectedErrorMessage);
		Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
				"actualErrorMessage does not contain expectedErrorMessage\nexpectedErrorMessage: "
						+ expectedErrorMessage + "\nactualErrorMessage: " + actualErrorMessage);
	}


	@AfterMethod
	private void tearDown() {
		System.out.println("Close driver");
		// Close browser
		//driver.quit();
	}

}
