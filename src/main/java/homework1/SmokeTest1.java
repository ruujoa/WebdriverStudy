package homework1;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SmokeTest1 {
	
	@BeforeClass
	public void start() {
		Action.init("http://www.126.com");
	}
	
	@Test
	public void toInbox() {
		Action.login("ruujoa1982", "luyao1982");
		Action.to("收件箱");
		Assert.assertTrue(Action.verify("收件箱"));
		Action.checkMail("test", 0);
		Assert.assertTrue(Action.verify("test"));
		Action.logout();
	}
	
	@AfterClass
	public void end() {
		Action.close();
	}
}
