package com.toranotec.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignInPageObjects {
	// Constructor
	public SignInPageObjects(WebDriver driver) {
		// Initialise Elements
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@type='email']")
	public WebElement emailInputBox;

	@FindBy(xpath = "//input[@type='submit']")
	public WebElement nextButton;

	@FindBy(xpath = "//input[@type='password']")
	public WebElement passwordInputBox;
	
	@FindBy(xpath = "//div[@id='usernameError']")
	public WebElement usernameErrorMessage;
	
	@FindBy(xpath = "//div[@id='passwordError']")
	public WebElement passwordErrorMessage;
	
	@FindBy(xpath = "//div[@id='lightbox']")
	public WebElement staySignedInModal;
	

}
