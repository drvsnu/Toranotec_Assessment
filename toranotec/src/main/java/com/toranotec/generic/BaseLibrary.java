package com.toranotec.generic;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class BaseLibrary {
	public static ObjectPropertyConnection opc;
	public static WebDriver driver;
	// builds a new report using the html template
	public static ExtentHtmlReporter htmlReporter;

	public static ExtentReports extent;
	// helps to generate the logs in test report.
	public static ExtentTest test;

	@BeforeSuite
	public void initializeApplication() throws Exception {
		opc = new ObjectPropertyConnection();
		// initialize the HtmlReporter
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");

		// initialize ExtentReports and attach the HtmlReporter
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		// To add system or environment info by using the setSystemInfo method.
		extent.setSystemInfo("OS", opc.getObjectRepository("Config.properties").getProperty("os"));
		extent.setSystemInfo("Browser", opc.getObjectRepository("Config.properties").getProperty("browser"));
		extent.setSystemInfo("Environment", opc.getObjectRepository("Config.properties").getProperty("environment"));
		extent.setSystemInfo("User Name", opc.getObjectRepository("Config.properties").getProperty("username"));

		// configuration items to change the look and feel
		// add content, manage tests etc
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Selenium: Toranotec Assessment");
		htmlReporter.config().setReportName("Selenium: Toranotec Assessment :: Test Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		openBrowserConfig();
	}

//	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:",
					ExtentColor.RED));
			// To capture screenshot path and store the path of the screenshot
			// in the string "screenshotPath"
			// We do pass the path captured by this mehtod in to the extent
			// reports using "logger.addScreenCapture" method.
			String screenshotPath = getScreenshot(driver, result.getName());
			// To add it in the extent report
			test.log(Status.FAIL, (Markup) test.addScreenCaptureFromPath(screenshotPath));
			test.fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			System.out.println(result.getName());
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
		} else {
			test.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
			test.skip(result.getThrowable());
		}
	}

	@AfterSuite
	public void tearDown() {
		extent.flush();
		driver.quit();
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots"
		// under src folder
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public void openBrowserConfig() throws Exception {
		opc = new ObjectPropertyConnection();
		String browserName = opc.getObjectRepository("Config.properties").getProperty("browser");
		System.out.println("User selected browser as :: " + browserName);
		try {
			if (browserName.equalsIgnoreCase("Firefox")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/BrowserDrivers/geckodriver.exe");
				driver = new FirefoxDriver();
			} else if (browserName.equalsIgnoreCase("Chrome")) {
				// Chrome version - 72.0.3626.69
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/BrowserDrivers/chromedriver.exe");
				driver = new ChromeDriver();
			} else if (browserName.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "/BrowserDrivers/IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(browserName + " :: Browser has been launched");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(opc.getObjectRepository("Config.properties").getProperty("url"));

	}
}
