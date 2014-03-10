package homework1;

import java.util.ArrayList;
import java.util.Arrays;
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
	private static final String OS_NAME = System.getProperty("os.name");
	
	
	private static void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void init(String URL) {
		Random random = new Random(System.currentTimeMillis());
		List<BrowsersType> list = new ArrayList<BrowsersType>(Arrays.asList(BrowsersType.values()));
		
		if ( !OS_NAME.toUpperCase().contains("WINDOWS") ) {
			list.remove(BrowsersType.ie);
		}
		int i = random.nextInt(list.size());
		browser = new Browsers(list.get(i));
		driver = browser.driver;
		driver.get(URL);
		sleep(3000);
	}
	
	public static void init(String URL, List<BrowsersType> browsersToExclude) {
		if( !OS_NAME.toUpperCase().contains("WINDOWS") ) browsersToExclude.add(BrowsersType.ie);
		Random random = new Random(System.currentTimeMillis());
		int i = 0; 
		while(true) {
			i = random.nextInt(BrowsersType.values().length);
			if (!browsersToExclude.contains(BrowsersType.values()[i])) break;
			
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
			.xpath("//*[text()='写 信']"))
			.click();
		
		for (String recipient : recipients) {
			driver.findElement(By
					.xpath("//a[text()='收件人']/following-sibling::div/descendant::input[last()]"))
					.sendKeys(recipient);
			sleep(2000);
			actions.sendKeys(Keys.TAB).perform();
		}
		
		sleep(2000);
		
		driver.findElement(By
			.xpath("//*[text()='主　题']/following-sibling::div/descendant::input"))
			.sendKeys(title);
		
		sleep(2000);
		actions.sendKeys(Keys.TAB).perform();
		sleep(2000);
		actions.sendKeys(content).perform();
		sleep(2000);
		try {
			driver.findElement(By
					.xpath("//span[contains(text(), '发 送') and parent::div[@tabindex=2]]"))
					.click();
		} catch (NoSuchElementException e) {
			return false;
		}
		
		try {
			WebElement prompt = driver.findElement(By.xpath("//div[text()='发信验证']"));
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
			message = driver.findElement(By.xpath("//*[text()='发送成功']"));
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
		driver.findElement(By.xpath("//a[text()='退出']")).click();
	}
	
	public static void close() {
		driver.quit();
	}
}
