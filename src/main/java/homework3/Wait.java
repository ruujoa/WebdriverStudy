package homework3;

import java.io.UnsupportedEncodingException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait {
	private WebDriver driver;	

	
	public Wait(WebDriver driver){
		this.driver = driver;	
        PageFactory .initElements(driver, this);		
	}
	
	public void waitForElementPresent(String locator, int timeout){
		(new WebDriverWait(driver, timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(
			this.convert(locator))));
	}
	
	public void waitForElementIsVisible(String locator, int timeout) {
		(new WebDriverWait(driver, timeout)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
			this.convert(locator))));
	}
	
	public void waitForElementIsEnable(String locator, int timeout){
		(new WebDriverWait(driver, timeout)).until(ExpectedConditions.elementToBeClickable(By.xpath(
			this.convert(locator))));
	}
	
	public void waitForTitleIsDisplayed(String title, int timeout) {
		(new WebDriverWait(driver, timeout)).until(ExpectedConditions.titleContains(
			this.convert(title)));
	}
	
	public void waitFor(long timeout){
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public String convert(String toConvert) {
		String result = "";
		try {
			result = new String(toConvert.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
