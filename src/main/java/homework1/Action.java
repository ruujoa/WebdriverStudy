package homework1;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Action {
	
	private static WebDriver driver;
	private static Browsers browser;
	
	public static void init(String URL) {
		Random random = new Random(System.currentTimeMillis());
		int i = random.nextInt(BrowsersType.values().length);
		browser = new Browsers(BrowsersType.values()[i]);
		driver = browser.driver;
		driver.get(URL);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void login(String username, String password) {
		driver.findElement(By.xpath("//*[@id='idInput']")).clear();
		driver.findElement(By.xpath("//*[@id='idInput']")).sendKeys(username);
		driver.findElement(By.xpath("//*[@id='pwdInput']")).clear();
		driver.findElement(By.xpath("//*[@id='pwdInput']")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id='loginBtn']")).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void to(String where) {
		driver.findElement(
			By.xpath("//li[descendant::span[text()='" + where + "']]")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean verify(String expected) {
		return driver.findElement(
			By.xpath("//*[@title='" + expected + "' and contains(@class, 'selected')]")).isDisplayed();
	}
	
	public static void logout() {
		driver.findElement(By.xpath("//a[text()='ÍË³ö']")).click();
	}
	
	public static void close() {
		driver.quit();
	}
}
