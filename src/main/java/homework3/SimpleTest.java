package homework3;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SimpleTest {
private Action action = new Action();
	
	@BeforeClass
	@Parameters({"url2"})
	public void start(String url) {
		action.init(url);
	}
	
	@Test
	public void test() {
		action.scroll();
		action.dragAndDrop2();
	}
	
	@AfterClass
	public void end() {
		action.close();
		action.clear();
	}
}
