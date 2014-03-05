package homework1;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SmokeTest3 {
	@BeforeClass
	public void start() {
		Action.init(
			"http://ueditor.baidu.com/website/onlinedemo.html",
			BrowsersType.safari);
	}
	
	@Test
	public void test() {
		Action.testUE("Test");
	}
	
	@AfterClass
	public void end() {
		Action.close();
	}
}
