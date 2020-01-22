package ExtentReports;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.utils.FileUtil;

public class NopCommerseTest 
{
	public WebDriver driver;

	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;


	@BeforeMethod
	public void setUp()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Projects\\AutomationTesting\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.nopcommerce.com/");
	}

	@BeforeTest
	public void setExtent()
	{
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/EReportFour.html");
		htmlReporter.config().setDocumentTitle("ERFour");
		htmlReporter.config().setReportName("Functional Testing Report");
		htmlReporter.config().setTheme(Theme.DARK);


		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name","OPSI-LT-137");
		extent.setSystemInfo("OS","Windows 10");
		extent.setSystemInfo("Author","Prasad.Nunna");
		extent.setSystemInfo("Browsers","Chrome,FF and Edge");
	}
	@Test
	public void TitleTest()
	{
		test = extent.createTest("TitleTest");
		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertEquals(title, "Free and open-source eCommerce platform. ASP.NET based shopping cart. - nopCommerce");	
	}
	@Test
	public void logoTest()
	{
		test = extent.createTest("LogoTest");
		Boolean status = driver.findElement(By.xpath("//img[@title='nopCommerce']")).isDisplayed();
		Assert.assertTrue(status);

	}

	@Test
	public void Logintest()
	{
		test = extent.createTest("LoginTest");
		test.createNode("Valid Credentionals");
		Assert.assertTrue(true);
		test.createNode("Invalid Credentionals");
		Assert.assertTrue(true);
	}
	@AfterTest
	public void endReport()
	{
		extent.flush();
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException	
	{
		if(result.getStatus() == ITestResult.FAILURE)
		{
			test.log(Status.FAIL, "Testcase Failed :"+result.getName());
			test.log(Status.FAIL, "Testcase Failed :"+result.getThrowable());

			String screenShot = NopCommerseTest.getScreenShot(driver, result.getName());
			test.addScreenCaptureFromPath(screenShot);
		}
		else if(result.getStatus() == ITestResult.SKIP ){

			test.log(Status.SKIP, "Testcase Skipped :"+result.getName());
		}
		else if(result.getStatus() == ITestResult.SUCCESS)
		{
			test.log(Status.PASS, "Testcase Passed :"+result.getName());
		}
		driver.quit();
	}

	public static String getScreenShot(WebDriver driver, String imgName)throws IOException
	{
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot)driver;
		File  source = ts.getScreenshotAs(OutputType.FILE);


		String Destination = System.getProperty("user.dir")+"/ScreenShots"+imgName+dateName+".Png";
		File finalDestination = new File(Destination);
		FileUtils.copyFile(source,finalDestination);
		return Destination;
	}


}
