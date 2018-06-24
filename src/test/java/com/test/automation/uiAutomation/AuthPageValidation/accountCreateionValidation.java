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
import com.test.automation.uiAutomation.uiActions.CreateAnAccountPage;

public class accountCreateionValidation extends TestBase {
	CreateAnAccountPage createAnAccountPage;
	AuthenticationPage authenticationPage;
	public static final Logger log = Logger.getLogger(accountCreateionValidation.class.getName());

	@DataProvider(name = "AccountCreationData")
	public String[][] getTestData() {
		String[][] testrecords = getData("TestData.xlsx", "AccountCreationData");
		return testrecords;
	}

	@BeforeClass
	public void setup() throws Exception {
		init();
	}

	@Test(dataProvider = "AccountCreationData")
	public void successfullAccountCreationValidation(String UserName, String Gender, String FirstName, String LastName, String Password,
			String DayOfDOB, String MonthOfDOB, String YearOfDOB, String FirstLineOfAddress, String City,
			String PostalCode, String StateName, String CountryName, String MobileNumber, String AddressAlias,
			String RunMode) {
		if (RunMode.equalsIgnoreCase("n")) {
			throw new SkipException("User has marked this test as no run.");
		} else {
			log.info("=================Start AccountCreation Test====================");
		    authenticationPage=new  AuthenticationPage(driver);
			authenticationPage.signUpToApp(UserName);
			createAnAccountPage = new CreateAnAccountPage(driver);
			createAnAccountPage.yourPersonalInfo(Gender, FirstName, LastName, Password, "22", "3",
					"1999");
			getScreenShot("EnterPersonalInfo :" + UserName);
			createAnAccountPage.yourAddress(FirstName, LastName, FirstLineOfAddress, City, "66061", StateName,
					"21", "3129753902", AddressAlias);
			getScreenShot("EnterAddressInfo :" + UserName);
			createAnAccountPage.clickOnRagisterButton();
			getScreenShot("WelcomeToYourAccount :" + UserName);
			Assert.assertEquals(createAnAccountPage.successfullRagistationMessage(),
					ActaulResultsStrings.accoutCreatedSuccessText);
			log.info("Account of " + FirstName + " " + LastName + " is succesfully created.");
			log.info("=================End AccountCreation Test======================");
		}
	}
}
