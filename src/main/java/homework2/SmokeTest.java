package homework2;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SmokeTest {
	
	private List<BrowsersType> excludes = null;
	private Action action = new Action();
	
	@BeforeClass
	@Parameters({"url", "browsersToExclude"})
	public void start(String url, String excludes) {
	    this.excludes = new ArrayList<BrowsersType>();
	    if ( excludes.indexOf(',') > -1 ) {
	    	String[] items = excludes.split(",");
	    	for (int i = 0; i < items.length; i++) {
				this.excludes.add(BrowsersType.valueOf(items[i]));
			}
	    } else {
	    	this.excludes.add(BrowsersType.valueOf(excludes));
	    }
	    action.init(url, this.excludes);
	}
	
	@Test
	@Parameters({"username", "password", "where"})
	public void test(String username, String password
		, String where) {
		action.login(username, password);
		action.to(where);
		Assert.assertEquals( action.checkCountOfMails(), 
				action.getCountFromPage() );
	}
	
	@AfterClass
	public void end() {
		action.close();
	}
}
