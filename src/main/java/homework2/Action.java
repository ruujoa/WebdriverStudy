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
	
	private WebDriver driver;
	private Browsers browser;
	private final String OS_NAME = System.getProperty("os.name");
	private String projectpath = System.getProperty("user.dir");
	private Properties props = null; 
	private Wait wait = null;
	private String page_count = "";
	
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
	
	/*
	private void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	*/
	
	public void init(String URL) {
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
	
	public void init(String URL, List<BrowsersType> browsersToExclude) {
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
	
	public void testUE(String content) {
		Actions actions = new Actions(driver);
		actions.sendKeys(content).perform();
		wait.waitFor(3000);
	}
	
	public void login(String username, String password) {
		driver.findElement(By.xpath(props.getProperty("UsernameInput"))).clear();
		driver.findElement(By.xpath(props.getProperty("UsernameInput"))).sendKeys(username);
		driver.findElement(By.xpath(props.getProperty("PasswordInput"))).clear();
		driver.findElement(By.xpath(props.getProperty("PasswordInput"))).sendKeys(password);
		driver.findElement(By.xpath(props.getProperty("LoginButton"))).click();
		wait.waitForTitleIsDisplayed(
			new String( props.getProperty("MainTitle") ), 
			Integer.parseInt(props.getProperty("timeout")));
		
	}
	
	public void to(String where) {
		driver.findElement(
			By.xpath(props.getProperty("NavTree").replace("where", where))).click();
		wait.waitFor(3000);
	}
	
	public int checkCountOfMails() {
		int count = 0;
		page_count = driver.findElement(By
			.xpath(props.getProperty("PageNumber"))).getText();
		int index = page_count.lastIndexOf("/");
		page_count = page_count.substring(index+1);
		
		for (int i = 0; i < Integer.parseInt(page_count); i++) {
			List<WebElement> nexts = driver.findElements(By.xpath(
					wait.convert(props.getProperty("NextPageLink"))
					));
			if ( 0 < nexts.size() ) {
				WebElement next = nexts.get(nexts.size()-1);
				next.click();
				wait.waitFor(2000);
			}
			count = driver.findElements(By
					.xpath(props.getProperty("CountOfHaveBeenSent"))).size();
		}
		return count;
	}
	
	public int getCountFromPage() {
		int count = 0;
		
		for (int i = 0; i < Integer.parseInt(page_count); i++) {
			List<WebElement> nexts = driver.findElements(By.xpath(
					wait.convert(props.getProperty("NextPageLink"))
					));
			
			List<WebElement> numbers = driver.findElements(By
				.xpath(props.getProperty("CountOfMails")));
			for (int j = 0; j < numbers.size(); j++) {
				count += Integer.parseInt( 
					numbers.get(j).getText()
					.replaceAll("\\D+", ""));
			}
			if ( 0 < nexts.size() ) {
				WebElement next = nexts.get(nexts.size()-1);
				next.click();
				wait.waitFor(2000);
			}
		}
		return count;
	}
	
	
	public boolean sendMail(String title, String content, List<String> recipients) {
		Actions actions = new Actions(driver);
		wait.waitForElementIsVisible(props.getProperty("ButtonOfWritingMail"), 10);
		driver.findElement(By
			.xpath(wait.convert(props.getProperty("ButtonOfWritingMail"))))
			.click();
		
		for (String recipient : recipients) {
			driver.findElement(By
					.xpath(wait.convert(props.getProperty("RecipientsInput"))))
					.sendKeys(recipient + ";");
			wait.waitFor(2000);
		}
		actions.sendKeys(Keys.TAB).perform();
		wait.waitFor(2000);
		
		driver.findElement(By
			.xpath(wait.convert(props.getProperty("SubjectInput"))))
			.sendKeys(title);
		
		wait.waitFor(2000);
		actions.sendKeys(Keys.TAB).perform();
		wait.waitFor(2000);
		actions.sendKeys(content).perform();
		wait.waitFor(2000);
		try {
			driver.findElement(By
					.xpath(wait.convert(props.getProperty("SendButton"))))
					.click();
		} catch (NoSuchElementException e) {
			return false;
		}
		
		try {
			WebElement prompt = driver.findElement(By.xpath(wait.convert(props.getProperty("FlagToValidate"))));
			if (prompt.isDisplayed()) {
				System.out.println( "There is a prompt." );
				return false;
			}
		} catch (NoSuchElementException e) {
			System.out.println( "An exception#1 happened: " + e.getMessage() );
		}
		
		wait.waitFor(2000);
		
		WebElement message = null;
		
		try {
			message = driver.findElement(By.xpath(wait.convert(props.getProperty("FlagOfSuccess"))));
		} catch (NoSuchElementException e) {
			System.out.println( "An exception#2 happened: " + e.getMessage() );
			return false;
		}
		
		return message.isDisplayed();
	}
	
	public boolean verify(String expected) {
		return driver.findElement(
			By.xpath("//*[@title='" + expected + "' and contains(@class, 'selected')]")).isDisplayed();
	}
	
	public void logout() {
		driver.findElement(By.xpath("//a[text()='退出']")).click();
	}
	
	public void close() {
		driver.quit();
	}
}
