package com.toranotec.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePageObjects {
	// Constructor
	public HomePageObjects(WebDriver driver) {
		// Initialise Elements
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@data-task='signin']")
	public WebElement signInButton;


}
