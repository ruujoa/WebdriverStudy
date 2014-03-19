package homework3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class Action {
	
	private WebDriver driver;
	private Browsers browser;
	private final String OS_NAME = System.getProperty("os.name");
	private String projectpath = System.getProperty("user.dir");
	private Properties props = null;
	private final Logger logger = Logger.getLogger( getClass() );
	private List<BrowsersType> excludes = null;
	private long now = 0;
	private Wait wait = null;
	
	public Action() {
		props = new Properties();
		try {
			props.load(new FileInputStream( projectpath + "/tool/locators.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure( System.getProperty( "user.dir" ) + "/tool/log4j.properties" );
	}
	
	private void prepare() {
		logger.info( "The test starts..." );
		now = System.currentTimeMillis();
	    excludes = new ArrayList<BrowsersType>();
	    String toExclude = props.getProperty("toExclude");
	    if ( toExclude != null && toExclude.isEmpty() ) {
	    	if ( toExclude.indexOf(',') > -1 ) {
		    	String[] items = toExclude.split(",");
		    	for (int i = 0; i < items.length; i++) {
		    		excludes.add(BrowsersType.valueOf(items[i].trim()));
				}
		    } else {
		    	excludes.add(BrowsersType.valueOf(toExclude.trim()));
		    }
		}
	}
	
	public void init(String URL) {
		prepare();
		Random random = new Random(System.currentTimeMillis());
		List<BrowsersType> list = new ArrayList<BrowsersType>(Arrays.asList(BrowsersType.values()));
		
		if ( !OS_NAME.toUpperCase().contains("WINDOWS") ) {
			list.remove(BrowsersType.ie);
		}
		if ( OS_NAME.toUpperCase().contains("LINUX") ) {
			list.remove(BrowsersType.safari);
		}
		int i = 0; 
		if (excludes != null && excludes.size() > 0) {
			while(true) {
				i = random.nextInt(BrowsersType.values().length);
				if (!excludes.contains(BrowsersType.values()[i])) break;	
			}
		} else { 
			i = random.nextInt(list.size());
		}
		browser = new Browsers(list.get(i));
		logger.info("Test on " + list.get(i).name() + " this time.");
		driver = browser.driver;
		wait = new Wait(driver);
		driver.get(URL);
	}
	
	public void toSpecificFrame() {
		wait.waitForElementPresent(props.getProperty("jQueryHome"), 
			Integer.parseInt(props.getProperty("timeout")));
		driver.switchTo().frame(driver.findElement(By.xpath(props.getProperty("iFramePath"))));
	}
	
	public void dragAndDrop() {
		Point initialPoint= driver.findElement(By.xpath(props.getProperty("Slider"))).getLocation();
		System.out.println( "pre: " + initialPoint );
		
        Actions dragger = new Actions(driver);
        
        try {
        	dragger.dragAndDropBy(driver.findElement(By.xpath(props.getProperty("Slider"))), 
                	initialPoint.getX()+680, initialPoint.getY()).build().perform();
		} catch (Exception e) {
			System.out.println( "out of the range!");
		} finally {
			
		}
        
        wait.waitFor(5000);

        driver.switchTo().defaultContent();
        driver.findElement(By.xpath(props.getProperty("Draggable"))).click();
		wait.waitFor(3000);
	}
	
	public void scroll() {
		wait.waitForElementPresent(props.getProperty("YiXunLogo"), 
				Integer.parseInt(props.getProperty("timeout")));
		Point F1= driver.findElement(By.xpath(props.getProperty("F1_Position"))).getLocation();
		((JavascriptExecutor) driver).executeScript( "window.scrollBy(" + F1.getX() + "," + 
				F1.getY() + ")");
        wait.waitFor(5000);	
	}
	
	public void close() {
		driver.quit();
	}
	
    public void dragAndDrop2(){
        driver.get("http://reg.163.com/agreement.shtml");
        int numberOfPixelsToDragTheScrollbarDown = 500;
        Actions dragger = new Actions(driver);
        dragger.moveToElement(driver.findElement(By.xpath("//p[contains(text(),'网易通行证服务条款')]"))).clickAndHold().moveByOffset(  0,numberOfPixelsToDragTheScrollbarDown).release().perform();
        wait.waitFor(5000);
    }
	
	public void clear() {
		logger.info( "The test ends..." );
		logger.info( "The test costs " 
			+ (System.currentTimeMillis() - now)/1000
			+ " seconds.\n" );
	}
}
