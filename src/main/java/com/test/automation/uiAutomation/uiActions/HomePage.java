package com.test.automation.uiAutomation.uiActions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	public static final Logger log = Logger.getLogger(HomePage.class.getName());
	WebDriver driver;

	@FindBy(xpath = "//a[@class='login']")
	WebElement signIN;

	@FindBy(xpath = "//input[@id='email']")
	WebElement emailAddress;

	@FindBy(xpath = "//input[@id='passwd']")
	WebElement password;

	@FindBy(xpath = "//button[@id='SubmitLogin']//span")
	WebElement signInButtonButton;

	@FindBy(xpath = "//li[contains(text(),'Invalid password.')]")
	WebElement authenticationfailMessage;

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void loginToApp(String email, String pass) {
		signIN.click();
		log.info("Clicked on signin and object is: " + signIN.toString());
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
}
