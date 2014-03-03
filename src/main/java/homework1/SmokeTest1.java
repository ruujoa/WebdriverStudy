package homework1;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SmokeTest1 {
	
	@BeforeClass
	public void start() {
		Action.init("www.126.com", BrowsersType.ie);
	}
	
	@Test
	public void toInbox() {
		Action.login("ruujoa1982", "luyao1982");
		Action.to("�ռ���", 1, " and not(parent::ul[@style])");
		Assert.assertTrue(Action.verify("�ռ���"));
		Action.logout();
	}
	
	@AfterClass
	public void end() {
		Action.close();
	}
}
