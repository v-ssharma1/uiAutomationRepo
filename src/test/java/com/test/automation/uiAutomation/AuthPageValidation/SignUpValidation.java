package com.test.automation.uiAutomation.AuthPageValidation;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.automation.uiAutomation.ActaulValidationStrings.ActaulResultsStrings;
import com.test.automation.uiAutomation.testBase.TestBase;
import com.test.automation.uiAutomation.uiActions.AuthenticationPage;

public class SignUpValidation extends TestBase {
	AuthenticationPage AuthenticationPage;
	public static final Logger log = Logger.getLogger(SignUpValidation.class.getName());

	@DataProvider(name = "SignUpData")
	public String[][] getTestData() {
		String[][] testrecords = getData("TestData.xlsx", "LoginTestData");
		return testrecords;
	}

	@BeforeClass
	public void setup() throws Exception {
		init();
	}

	@Test(dataProvider = "SignUpData")
	public void verifyLandingOnNewAccountCreationInfoPage(String UserName, String Password, String RunMode) {
		if (RunMode.equalsIgnoreCase("n")) {
			throw new SkipException("User has marked this test as no run.");
		} else {
			log.info("=================Start AccountCreation Page Test====================");
			AuthenticationPage = new AuthenticationPage(driver);
			AuthenticationPage.signUpToApp(UserName);
			getScreenShot("LoginWithValidCredentials_" + UserName);
			Assert.assertEquals(AuthenticationPage.getAccountCreationPageText(),
					ActaulResultsStrings.actualAccountCreationPageText);
			log.info("Successfully landed on account information Page.");
			log.info("=================End AccountCreation Page Test======================");
		}
	}
}
