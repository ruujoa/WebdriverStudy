package homework2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
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
	private static String projectpath = System.getProperty("user.dir");
	private static Properties props = null; 
	private static Wait wait = null;
	
	public Action() {
		props = new Properties();
		try {
			props.load(new FileInputStream( projectpath + "/tool/locators.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
		wait = new Wait(driver);
		driver.get(URL);
		wait.waitForElementPresent(props.getProperty("Logo"),
			   Integer.parseInt(props.getProperty("timeout")));
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
		wait = new Wait(driver);
		driver.get(URL);
		wait.waitForElementPresent(props.getProperty("Logo"), 
				Integer.parseInt(props.getProperty("timeout")));
	}
	
	public static void testUE(String content) {
		Actions actions = new Actions(driver);
		actions.sendKeys(content).perform();
		wait.waitFor(3000);
	}
	
	public static void login(String username, String password) {
		driver.findElement(By.xpath(props.getProperty("UsernameInput"))).clear();
		driver.findElement(By.xpath(props.getProperty("UsernameInput"))).sendKeys(username);
		driver.findElement(By.xpath(props.getProperty("PasswordInput"))).clear();
		driver.findElement(By.xpath(props.getProperty("PasswordInput"))).sendKeys(password);
		driver.findElement(By.xpath(props.getProperty("LoginButton"))).click();
		wait.waitForTitleIsDisplayed(props.getProperty("MainTitle"), Integer.parseInt(props.getProperty("timeout")));
	}
	
	public static void to(String where) {
		driver.findElement(
			By.xpath(props.getProperty("NavTree").replace("where", where))).click();
		wait.waitFor(3000);
	}
	
	public static int checkCountOfMails() {
		return driver.findElements(By
			.xpath(props.getProperty("CountOfHaveBeenSent"))).size() - 1;
	}
	
	public static int getCountFromPage() {
		return Integer.parseInt(driver.findElements(By
				.xpath(props.getProperty("CountOfHaveBeenSent")))
				.get(0)
				.getText()
				.replaceAll("\\D+", "")
				.trim());
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
