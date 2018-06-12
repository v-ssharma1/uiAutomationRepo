package com.test.automation.uiAutomation.AuthPageValidation;

import org.testng.annotations.Test;

import com.test.automation.uiAutomation.ActaulValidationStrings.ActaulResultsStrings;
import com.test.automation.uiAutomation.testBase.TestBase;
import com.test.automation.uiAutomation.uiActions.AuthenticationPage;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

public class SignInValidation extends TestBase {
	AuthenticationPage AuthenticationPage;
	public static final Logger log = Logger.getLogger(SignInValidation.class.getName());

	@DataProvider(name = "LoginData")
	public String[][] getTestData() {
		String[][] testrecords = getData("TestData.xlsx", "LoginTestData");
		return testrecords;
	}

	@BeforeClass
	public void setup() throws Exception {
		init();
	}

	@Test(dataProvider = "LoginData")
	public void verifySuccessfulLandingOnAuthenticationPage(String UserName, String Password, String RunMode) {
		log.info("=================Start Authentication Page Test====================");
		AuthenticationPage = new AuthenticationPage(driver);
		getScreenShot("LoginWithValidCredentials_" + UserName);
		Assert.assertEquals(AuthenticationPage.getAuththentcateionPageText(),
				ActaulResultsStrings.actualAuththentcateionPageText);
		log.info("Test case successfully validated.");
		log.info("=================End Authentication Page Test=======================");
	}

	@Test(dataProvider = "LoginData")
	public void verifyLoginWithInvalidCredentials(String UserName, String Password, String RunMode) {
		if (RunMode.equalsIgnoreCase("n")) {
			throw new SkipException("User has marked this test as no run.");
		} else {
			log.info("=================Start Login With Invalid Credentials Test====================");
			AuthenticationPage = new AuthenticationPage(driver);
			AuthenticationPage.loginToApp(UserName, Password);
			getScreenShot("LoginWithValidCredentials_" + UserName);
			Assert.assertEquals(AuthenticationPage.getInvalidAuthText(),
					ActaulResultsStrings.authenticationfailActaulMessage);
			log.info("Test case successfully validated.");
			log.info("=================End Login With Invalid Credentials Test======================");
		}
	}

	@Test(dataProvider = "LoginData")
	public void verifyLoginWithValidCredentials(String UserName, String Password, String RunMode) {
		if (RunMode.equalsIgnoreCase("n")) {
			throw new SkipException("User has marked this test as no run.");
		} else {
			log.info("=================Start Login With valid Credentials Test====================");
			AuthenticationPage = new AuthenticationPage(driver);
			AuthenticationPage.loginToApp(UserName, Password);
			getScreenShot("LoginWithValidCredentials_" + UserName);
			Assert.assertEquals(AuthenticationPage.getInvalidAuthText(),
					ActaulResultsStrings.authenticationpassActaulMessage);
			log.info("Test case successfully validated.");
			log.info("=================End Login With valid Credentials Test======================");
		}
	}
}
