package homework2;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SmokeTest {
	
	private List<BrowsersType> excludes = null;
	private Action action = new Action();
	private long now = 0;
	private final Logger logger = Logger.getLogger( getClass() );
	
	@BeforeTest
	@Parameters({"browsersToExclude"})
	public void prepare(String excludes) {
		PropertyConfigurator.configure( System.getProperty( "user.dir" ) + "/tool/log4j.properties" );
		logger.info( "The test starts..." );
		now = System.currentTimeMillis();
	    this.excludes = new ArrayList<BrowsersType>();
	    if ( excludes.indexOf(',') > -1 ) {
	    	String[] items = excludes.split(",");
	    	for (int i = 0; i < items.length; i++) {
				this.excludes.add(BrowsersType.valueOf(items[i]));
			}
	    } else {
	    	this.excludes.add(BrowsersType.valueOf(excludes));
	    }
	}
	
	@BeforeClass
	@Parameters({"url"})
	public void start(String url) {
		action.init(url, this.excludes);
	}
	
	@Test
	@Parameters({"username", "password", "where"})
	public void test(String username, String password
		, String where) {
		action.login(username, password);
		action.to(where);
		int actual = action.checkCountOfMails();
		action.to(where);
		int expected = action.getCountFromPage();
		Assert.assertEquals( actual, 
				expected );
	}
	
	@AfterClass
	public void end() {
		action.logout();
		action.close();
		
	}
	
	@AfterTest
	public void log() {
		logger.info( "The test ends..." );
		logger.info( "The test costs " 
			+ (System.currentTimeMillis() - now)/1000
			+ " seconds.\n" );
	}
}
