package homework1;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SmokeTest2 {
	
	@BeforeClass
	public void start() {
		Action.init("www.126.com", BrowsersType.ie);
	}
	
	@Test
	public void toOutbox() {
		Action.login("ruujoa1982", "luyao1982");
		Action.to("已发送", 4, " and not(parent::ul[@style])");
		Assert.assertTrue(Action.verify("已发送"));
		Action.logout();
	}
	
	@AfterClass
	public void end() {
		Action.close();
	}
}
