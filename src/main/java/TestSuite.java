import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;

public class TestSuite {

	protected WebDriver driver;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
		driver = new ChromeDriver();
//        Run chromedriver
		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	// login thanh cong
	@Test
	public void login_thanh_cong() {
		login("standard_user", "secret_sauce");
		// verify shopping cart is displayed
		WebElement shoppingCart = driver.findElement(By.id("shopping_cart_container"));
		Assert.assertTrue(shoppingCart.isDisplayed());

	}

	// username sai
	@Test
	public void login_voi_username_sai() {
		login("wrong_user", "secret_sauce");
		WebElement errorLabel = driver.findElement(By.xpath("//div[@class='login-box']//h3"));
		Assert.assertEquals("Epic sadface: Username and password do not match any user in this service",
				errorLabel.getText());
	}

	// password sai
	@Test
	public void login_voi_password_sai() {
		login("standard_user", "wrong_secret_sauce");
		WebElement errorLabel = driver.findElement(By.xpath("//div[@class='login-box']//h3"));
		Assert.assertEquals("Epic sadface: Username and password do not match any user in this service",
				errorLabel.getText());
	}

	// de trong username
	@Test
	public void detrong_username() {
		login("", "secret_sauce");
		WebElement errorLabel = driver.findElement(By.xpath("//div[@class='login-box']//h3"));
		Assert.assertEquals("Epic sadface: Username is required", errorLabel.getText());
	}

	// de trong password
	@Test
	public void detrong_password() {
		login("standard_user", "");
		WebElement errorLabel = driver.findElement(By.xpath("//div[@class='login-box']//h3"));
		Assert.assertEquals("Epic sadface: Password is required", errorLabel.getText());
	}

	// username lock
	@Test
	public void login_voi_username_lock() {
		login("locked_out_user", "secret_sauce");
		WebElement errorLabel = driver.findElement(By.xpath("//div[@class='login-box']//h3"));
		Assert.assertEquals("Epic sadface: Sorry, this user has been locked out.", errorLabel.getText());
	}

	// problem user
	@Test
	public void username_problem() {
		login("problem_user", "secret_sauce");
		// verify shopping cart is displayed
		WebElement shoppingCart = driver.findElement(By.id("shopping_cart_container"));
		Assert.assertTrue(shoppingCart.isDisplayed());
		//verify img source error
		WebElement img = driver.findElement(By.xpath("//img[@class='inventory_item_img']"));
		Assert.assertEquals("static/media/sl-404.168b1cce.jpg", img.getAttribute("src"));
	}
	
	//performance_glitch_user
	@Test
	public void performance_glitch_user() {
		login("performance_glitch_user", "secret_sauce");
		// verify shopping cart is displayed
		WebElement shoppingCart = driver.findElement(By.id("shopping_cart_container"));
		Assert.assertTrue(shoppingCart.isDisplayed());
	}
	
	
	@After
	public void tearDown() {
		driver.quit();
	}

	public void login(String username, String password) {
		WebElement txtUsername = driver.findElement(By.id("user-name"));
		WebElement txtPassword = driver.findElement(By.id("password"));
		WebElement btnLogin = driver.findElement(By.id("login-button"));
		// Thao tác với các elements
		txtUsername.sendKeys(username);
		txtPassword.sendKeys(password);
		btnLogin.click();
	}

}
