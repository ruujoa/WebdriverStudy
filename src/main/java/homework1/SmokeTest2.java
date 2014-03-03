package homework1;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SmokeTest2 {
	
	@BeforeClass
	public void start() {
		Action.init("http://www.126.com", BrowsersType.chrome);
	}
	
	@Test
	public void toOutbox() {
		Action.login("ruujoa1982", "luyao1982");
		Action.to("�ѷ���");
		Assert.assertTrue(Action.verify("�ѷ���"));
		Action.logout();
	}
	
	@AfterClass
	public void end() {
		Action.close();
	}
}
