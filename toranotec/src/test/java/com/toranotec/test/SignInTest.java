package com.toranotec.test;

import org.testng.annotations.Test;

import com.toranotec.dataproviders.SignInDataProviders;
import com.toranotec.generic.UIOperations;

public class SignInTest extends UIOperations {
	@Test(dataProvider = "TORANOTEC_TC_001", dataProviderClass = SignInDataProviders.class, priority = 0, groups={"Smoke"})
	public void verifySignInInvalidTest(String testCaseID, String email, String password, String errorMessage) throws Exception {
		try {
			veifySignInNegativeFlow(testCaseID, email, password, errorMessage);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Test(dataProvider = "TORANOTEC_TC_002", dataProviderClass = SignInDataProviders.class, priority = 1, groups={"Smoke"})
	public void verifySignInValidTest(String testCaseID, String email, String password) throws Exception {
		try {
			veifySignInPositiveFlow(testCaseID, email, password);
		} catch (Exception e) {
			System.out.println(e);
		}
	}


}
