package homework1;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Action {
	
	private static WebDriver driver;
	private static Browsers browser;
	
	
	private static void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void init(String URL) {
		Random random = new Random(System.currentTimeMillis());
		int i = random.nextInt(BrowsersType.values().length);
		browser = new Browsers(BrowsersType.values()[i]);
		driver = browser.driver;
		driver.get(URL);
		sleep(3000);
	}
	
	public static void init(String URL, BrowsersType browserToExclude) {
		Random random = new Random(System.currentTimeMillis());
		int i = 0;
		while(true) {
			i = random.nextInt(BrowsersType.values().length);
			if (BrowsersType.values()[i] != browserToExclude) {
				break;
			}
		}
		browser = new Browsers(BrowsersType.values()[i]);
		driver = browser.driver;
		driver.get(URL);
		sleep(3000);
	}
	
	public static void testUE(String content) {
		Actions actions = new Actions(driver);
		actions.sendKeys(content).perform();
		sleep(3000);
	}
	
	public static void login(String username, String password) {
		driver.findElement(By.xpath("//*[@id='idInput']")).clear();
		driver.findElement(By.xpath("//*[@id='idInput']")).sendKeys(username);
		driver.findElement(By.xpath("//*[@id='pwdInput']")).clear();
		driver.findElement(By.xpath("//*[@id='pwdInput']")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id='loginBtn']")).click();
		sleep(3000);
	}
	
	public static void to(String where) {
		driver.findElement(
			By.xpath("//li[descendant::span[text()='" + where + "']]")).click();
		sleep(5000);
	}
	
	public static void checkMail(String title, int index) {
		driver.findElements(By
			.xpath("//div[contains(@id,'_ContentDiv')]/descendant::div[contains(text(),'" + title + "')]"))
			.get(index)
			.click();
		sleep(5000);
		
	}
	
	public static boolean sendMail(String title, String content, List<String> recipients) {
		Actions actions = new Actions(driver);
		driver.findElement(By
			.xpath("//*[text()='д ��']"))
			.click();
		
		for (String recipient : recipients) {
			driver.findElement(By
					.xpath("//a[text()='�ռ���']/following-sibling::div/descendant::input[last()]"))
					.sendKeys(recipient);
			sleep(2000);
			actions.sendKeys(Keys.TAB).perform();
		}
		
		sleep(2000);
		
		driver.findElement(By
			.xpath("//*[text()='������']/following-sibling::div/descendant::input"))
			.sendKeys(title);
		
		sleep(2000);
		actions.sendKeys(Keys.TAB).perform();
		sleep(2000);
		actions.sendKeys(content).perform();
		sleep(2000);
		try {
			driver.findElement(By
					.xpath("//span[contains(text(), '�� ��') and parent::div[@tabindex=2]]"))
					.click();
		} catch (NoSuchElementException e) {
			return false;
		}
		
		try {
			WebElement prompt = driver.findElement(By.xpath("//div[text()='������֤']"));
			if (prompt.isDisplayed()) {
				System.out.println( "There is a prompt." );
				return false;
			}
		} catch (NoSuchElementException e) {
			System.out.println( "An exception#1 happened: " + e.getMessage() );
		}
		
		sleep(2000);
		
		WebElement message = null;
		
		try {
			message = driver.findElement(By.xpath("//*[text()='���ͳɹ�']"));
		} catch (NoSuchElementException e) {
			System.out.println( "An exception#2 happened: " + e.getMessage() );
			return false;
		}
		
		return message.isDisplayed();
	}
	
	public static boolean verify(String expected) {
		return driver.findElement(
			By.xpath("//*[@title='" + expected + "' and contains(@class, 'selected')]")).isDisplayed();
	}
	
	public static void logout() {
		driver.findElement(By.xpath("//a[text()='�˳�']")).click();
	}
	
	public static void close() {
		driver.quit();
	}
}
