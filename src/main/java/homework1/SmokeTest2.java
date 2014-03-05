package homework1;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SmokeTest2 {
	
	private List<String> recipients = new ArrayList<String>();
	
	@BeforeClass
	public void start() {
		Action.init("http://www.126.com", BrowsersType.safari);
		recipients.add("yao3000vlove1999@hotmail.com");
	}
	
	@Test
	public void toOutbox() {
		Action.login("ruujoa1982", "luyao1982");
		Assert.assertTrue(Action.sendMail("test", 
				"This is a test mail for studying Selenium WebDriver", 
				recipients));
		Action.to("已发送");
		Assert.assertTrue(Action.verify("已发送"));
		Action.logout();
	}
	
	@AfterClass
	public void end() {
		Action.close();
	}
}
