package com.test.automation.uiAutomation.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
//import com.test.automation.uiAutomation.customListener.Listener;
import com.test.automation.uiAutomation.excelReader.Excel_Reader;

public class TestBase {
	public static final Logger log = Logger.getLogger(TestBase.class.getName());

	public WebDriver driver;
	public Properties prop = new Properties();
	Excel_Reader excel;
	public static ExtentReports extent;
	public static ExtentTest test;

	// init function to initialize browser, url and log4j
	public void init() throws Exception {
		String log4jConfPath = "log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		loadData();
		selectBrowser(prop.getProperty("browser"));
		getURL(prop.getProperty("url"));
	}

	// Code for selecting browser and OS.
	public void selectBrowser(String browser) throws Exception {
		if (System.getProperty("os.name").contains("Window")) {
			// Check if parameter passed from TestNG is 'firefox'
			if (browser.equalsIgnoreCase("firefox")) {
				// create firefox instance
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/drivers/geckodriver.exe");
				// FirefoxOptions options = new FirefoxOptions();
				// options.setLogLevel(FirefoxDriverLogLevel.DEBUG);
				// driver = new FirefoxDriver(options);
				log.info("Creating the object of " + browser);
				driver = new FirefoxDriver();
			}
			// Check if parameter passed as 'chrome'
			else if (browser.equalsIgnoreCase("chrome")) {
				// set path to chromedriver.exe
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/drivers/chromedriver.exe");
				log.info("Creating the object of " + browser);
				driver = new ChromeDriver();
			}
			// headless browser
			else if (browser.equalsIgnoreCase("HeadlessChrome")) {
				// headless browser
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/drivers/chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("headless");
				options.addArguments("window-size=1200x600");
				driver = new ChromeDriver(options);
			} else if (browser.equalsIgnoreCase("HeadlessHtmlUnitDriver")) {
				// Declaring and initialising the HtmlUnitWebDriver
				driver = new HtmlUnitDriver();
				// code to enable java script
				// driver = new HtmlUnitDriver(true);
				// or
				// ((HtmlUnitDriver) driver).setJavascriptEnabled(true);

				// Testing on different browser versions. : Declaring and initialising the
				// HtmlUnitWebDriver
				// driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
			}
		} else if (System.getProperty("os.name").contains("Mac OS X")) {
			// Check if parameter passed from TestNG is 'firefox'
			if (browser.equalsIgnoreCase("firefox")) {
				// create firefox instance
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver");
				// FirefoxOptions options = new FirefoxOptions();
				// options.setLogLevel(FirefoxDriverLogLevel.DEBUG);
				// driver = new FirefoxDriver(options);
				log.info("Creating the object of " + browser);
				driver = new FirefoxDriver();
			}
			// Check if parameter passed as 'chrome'
			else if (browser.equalsIgnoreCase("chrome")) {
				// set path to chromedriver.exe
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver");
				log.info("Creating the object of " + browser);
				driver = new ChromeDriver();
			}
			// headless browser
			else if (browser.equalsIgnoreCase("HeadlessChrome")) {
				// headless browser
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("headless");
				options.addArguments("window-size=1200x600");
				driver = new ChromeDriver(options);
			} else if (browser.equalsIgnoreCase("HeadlessHtmlUnitDriver")) {
				// Declaring and initialising the HtmlUnitWebDriver
				driver = new HtmlUnitDriver();
				// code to enable java script
				// driver = new HtmlUnitDriver(true);
				// or
				// ((HtmlUnitDriver) driver).setJavascriptEnabled(true);

				// Testing on different browser versions. : Declaring and initialising the
				// HtmlUnitWebDriver
				// driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
			}
		}
	}

	// get url and maximize screen)
	public void getURL(String URL) {
		log.info("Navigating to :-" + URL);
		driver.manage().window().maximize();
		driver.get(URL);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	// Read data from Excel
	public String[][] getData(String excelName, String sheetName) {
		String path = System.getProperty("user.dir") + "/src/main/java/com/test/automation/uiAutomation/data/"
				+ excelName;
		excel = new Excel_Reader(path);
		String[][] data = excel.getDataFromSheet(sheetName, excelName);
		return data;
	}

	// Read Property file
	public void loadData() throws IOException {
		String filepath = System.getProperty("user.dir")
				+ "/src/main/java/com/test/automation/uiAutomation/config/config.properties";
		try {
			FileInputStream inputStream = new FileInputStream(filepath);
			prop.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Explicit wait
	public void waitForElement(WebDriver driver, int timeOutInSeconds, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	// Get Screenshots
	public void getScreenShot(String name) {

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath()
					+ "/src/main/java/com/test/automation/uiAutomation/screenshot/";

			File destFile = new File(
					(String) reportDirectory + name + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			System.out.println("destFile getAbsolutePath:  " + destFile.getAbsolutePath());
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath()
					+ "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Static block to make single new Extent report for every class
	static {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		// here you are passing the path of the folder where you want to create Extent
		// report and boolean value true means you wants to override your report for
		// every run.
		extent = new ExtentReports(System.getProperty("user.dir")
				+ "/src/main/java/com/test/automation/uiAutomation/AdvancedExtentReports/AdvancedExtentReports"
				+ formater.format(calendar.getTime()) + ".html", true);
	}

	// Capture screenshot for Extent report.
	public String captureScreen(String fileName) {
		if (fileName == "") {
			fileName = "blank";
		}
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath()
					+ "/src/main/java/com/test/automation/uiAutomation/screenshot/";
			destFile = new File(
					(String) reportDirectory + fileName + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath()
					+ "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.toString();
	}

	//
	// get result method for extent class
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, result.getName() + " test is passed.");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP,
					result.getName() + " test is skiped and the reason is :--> " + result.getThrowable());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.ERROR,
					result.getName() + " test is failed and the reason is :-->" + result.getThrowable());
			test.log(LogStatus.FAIL, test.addScreenCapture(captureScreen("")));
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + " test is started.");
		}
	}

	@AfterMethod()
	public synchronized void afterMethod(ITestResult result) {
		getResult(result);
	}

	@BeforeMethod()
	public synchronized void beforeMethod(Method result) {
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + " test Started");
	}

	@AfterClass(alwaysRun = true)
	public synchronized void endTest() {
		closeBrowser();
	}

	public synchronized void closeBrowser() {
		driver.quit();
		log.info("browser closed");
		extent.endTest(test);
		extent.flush();
	}
}
