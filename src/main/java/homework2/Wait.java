package homework2;

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
		(new WebDriverWait(driver, timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
	}
	
	public void waitForElementIsEnable(String locator, int timeout){
		(new WebDriverWait(driver, timeout)).until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
	}
	
	public void waitForTitleIsDisplayed(String title, int timeout) {
		(new WebDriverWait(driver, timeout)).until(ExpectedConditions.titleContains(title));
	}
	
	public void waitFor(long timeout){
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	

}
