package TestRunners;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Utils.Config;
import Utils.XlUtil;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

/** Runner is the part where we provide options for Cucumber like where to Look for features,
 * bindings for these features, tags and any advanced reporting ways etc.
 * 
 * @author irali
 */

@CucumberOptions(
        features = "src/main/java/Features",
        glue = {"StepDefinitions"},
        tags = {"~@Ignore", "~@facebook"},
        format = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        })
public class TestNgTestRunner {
	private Config config = Config.getInstance();
	private static List<LinkedHashMap<String,String>> dataFromExcel = null;
	private TestNGCucumberRunner testNGCucumberRunner;
	private static TestNgTestRunner tsRunner;
	 
    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }
    
    /** to be used when Singleton instance is needed
     * 
     * @return TestNgTestRunner instance
     * @throws Exception
     */
    public static TestNgTestRunner getInstance() throws Exception{
    	if(tsRunner == null){
    		tsRunner= new TestNgTestRunner();
    		return tsRunner;
    	}
    	return tsRunner;
    }
    
    /** Cucumber annotation, taking advantage to get all necessary information
     * before test execution starts
     * 
     * @throws Exception
     */
    @BeforeTest
    public static void readInputData() throws Exception{
    	//reading data excel sheet from resources if any present
    	//to be used for future purpose when decided to pass data in excel
    	File testDataFile = new File(TestNgTestRunner.class.getClassLoader().getResource(Config.getInstance().getProperty("excelWorkbook")).toExternalForm());
    	String path = testDataFile.getAbsolutePath();
    	if(testDataFile.exists()){
    		dataFromExcel = new XlUtil().readExcel(path);
    	}
    }
    
    public List<LinkedHashMap<String, String>> getTestDataInputs(){
    	return dataFromExcel;
    }
    
    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper cucumberFeature) {
        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }
 
    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideFeatures();
    }
 
    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
    }
    
}
