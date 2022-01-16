package com.toranotec.generic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.toranotec.pages.HomePageObjects;
import com.toranotec.pages.SignInPageObjects;

public class UIOperations extends BaseLibrary {
	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(UIOperations.class);
	SoftAssert softAssert = new SoftAssert();
	public void veifySignInNegativeFlow(String testCaseID, String email, String password, String errorMessage) throws Exception {
		LOGGER.info(testCaseID+" - Started");
		HomePageObjects home = new HomePageObjects(driver);
		SignInPageObjects signInPageObjects = new SignInPageObjects(driver);
		// Click on Signin button
		home.signInButton.click();
		isClickable(signInPageObjects.emailInputBox);
		signInPageObjects.emailInputBox.clear();
		signInPageObjects.emailInputBox.sendKeys(email);
		signInPageObjects.nextButton.click();
		if (isElementDisplayed(signInPageObjects.usernameErrorMessage)) {
			String userNameError = signInPageObjects.usernameErrorMessage.getText();
			softAssert.assertTrue(userNameError.contains(errorMessage));
		}
		else {
			isElementDisplayed(signInPageObjects.passwordInputBox);
			signInPageObjects.passwordInputBox.clear();
			signInPageObjects.passwordInputBox.sendKeys(password);
			signInPageObjects.nextButton.click();
			String passwordErrorMessage = signInPageObjects.passwordErrorMessage.getText();
			softAssert.assertTrue(passwordErrorMessage.contains(errorMessage));
		}

		LOGGER.info(testCaseID+" - Completed");
		driver.get(opc.getObjectRepository("Config.properties").getProperty("url"));
		softAssert.assertAll();
		
	}

	public void veifySignInPositiveFlow(String testCaseID, String email, String password) {
		LOGGER.info(testCaseID+" - Started");
		HomePageObjects home = new HomePageObjects(driver);
		SignInPageObjects signInPageObjects = new SignInPageObjects(driver);
		// Click on Signin button
		home.signInButton.click();
		isClickable(signInPageObjects.emailInputBox);
		signInPageObjects.emailInputBox.clear();
		signInPageObjects.emailInputBox.sendKeys(email);
		signInPageObjects.nextButton.click();
		isClickable(signInPageObjects.passwordInputBox);
		signInPageObjects.passwordInputBox.clear();
		signInPageObjects.passwordInputBox.sendKeys(password);
		signInPageObjects.nextButton.click();
		if(isElementDisplayed(signInPageObjects.staySignedInModal)) {
			signInPageObjects.nextButton.click();
		}
		String currentUrl = driver.getCurrentUrl();
		softAssert.assertTrue(currentUrl.contains("/mail/"));
		LOGGER.info(testCaseID+" - Completed");
		softAssert.assertAll();
		
	}
	
	
	private void mouseHoverOnElement(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	private void alertAccept() {
		driver.switchTo().alert().accept();
	}

	private String getAlertText() {
		return driver.switchTo().alert().getText().toString();

	}

	public String getAttributeValue(WebElement element, String attributeKey) {
		return element.getAttribute(attributeKey).toString();
	}

	public static boolean isClickable(WebElement elementToCheck) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.elementToBeClickable(elementToCheck));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isElementDisplayed(WebElement elementToCheck) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOf(elementToCheck));
			return elementToCheck.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		}
	}

	public static void switchToChildWindow() {
		String mainWindow = driver.getWindowHandle();
		// To handle all new opened window.
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();
		while (i1.hasNext()) {
			String childWindow = i1.next();
			if (!mainWindow.equalsIgnoreCase(childWindow)) {
				// Switching to Child window
				driver.switchTo().window(childWindow);
				break;
			}
		}
	}

	public static void switchToParentWindow(WebDriver driver) {
		Set<String> allWindowHandles = driver.getWindowHandles();
		String parent = (String) allWindowHandles.toArray()[0];
		driver.switchTo().window(parent);
	}

	private String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("E dd MMM yyyy");
		return sdf.format(new Date()).toString();
	}

	private String getCookies() {
		return driver.manage().getCookies().toString();
	}
}
