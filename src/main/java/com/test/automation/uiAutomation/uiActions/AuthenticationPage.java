package com.test.automation.uiAutomation.uiActions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.automation.uiAutomation.testBase.TestBase;

public class AuthenticationPage extends TestBase {
	public static final Logger log = Logger.getLogger(AuthenticationPage.class.getName());
	WebDriver driver;

	@FindBy(xpath = "//a[@class='login']")
	WebElement signIN;

	@FindBy(xpath = "//span[@class='navigation_page']")
	WebElement AuthenticationPageText;

	@FindBy(xpath = "//input[@id='email']")
	WebElement emailAddress;

	@FindBy(xpath = "//input[@id='passwd']")
	WebElement password;

	@FindBy(xpath = "//button[@id='SubmitLogin']//span")
	WebElement signInButtonButton;

	@FindBy(xpath = "//li[contains(text(),'Authentication failed.')]")
	WebElement authenticationfailMessage;

	@FindBy(xpath = "//input[@id='email_create']")
	WebElement emailAddressForAccountCreation;

	@FindBy(xpath = "//button[@id='SubmitCreate']//span")
	WebElement accountCreationButton;

	@FindBy(xpath = "//h1[contains(text(), 'Create an account')]")
	WebElement accountCreationPageText;

	public AuthenticationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void loginToApp(String email, String pass) {
		signIN.click();
		log.info("Successfully landed to :" + AuthenticationPageText.getText());
		emailAddress.sendKeys(email);
		log.info("Enetered email is: " + email + " and object is: " + emailAddress.toString());
		password.sendKeys(pass);
		log.info("Enetered password is: " + pass + " and object is: " + password.toString());
		signInButtonButton.click();
		log.info("Clicked on Submit button and the object is : " + signInButtonButton.toString());
	}

	public String getInvalidAuthText() {
		log.info("Error message is :" + authenticationfailMessage.toString());
		return authenticationfailMessage.getText();
	}

	public String getAuththentcateionPageText() {
		signIN.click();
		log.info("Successfully landed to :" + AuthenticationPageText.getText());
		return AuthenticationPageText.getText();
	}

	public void signUpToApp(String email) {
		signIN.click();
		log.info("Successfully landed to :" + AuthenticationPageText.getText());
		emailAddressForAccountCreation.sendKeys(email);
		accountCreationButton.click();
	}

	public String getAccountCreationPageText() {
		return accountCreationPageText.getText();
	}

}
