package homework1;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SmokeTest3 {
	
	private List<BrowsersType> browsersToExclude = new ArrayList<BrowsersType>();
	
	@BeforeClass
	public void start() {
		browsersToExclude.add(BrowsersType.safari);
		browsersToExclude.add(BrowsersType.firefox);
		Action.init(
			"http://ueditor.baidu.com/website/onlinedemo.html",
			browsersToExclude);
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
