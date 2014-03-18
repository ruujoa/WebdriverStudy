package homework3;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SmokeTest {
	
	private Action action = new Action();
	
	@BeforeClass
	@Parameters({"url1"})
	public void start(String url) {
		action.init(url);
	}
	
	@Test
	public void test() {
		action.toSpecificFrame();
		action.dragAndDrop();
	}
	
	@AfterClass
	public void end() {
		action.close();
		action.clear();
	}
}
