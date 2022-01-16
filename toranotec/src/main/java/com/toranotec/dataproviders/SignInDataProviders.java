package com.toranotec.dataproviders;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import com.toranotec.generic.ExcelLibrary;



public class SignInDataProviders {

	@DataProvider(name = "TORANOTEC_TC_001")
	public Object[][] TORANOTEC_TC_001TestData() throws Exception {
		FileInputStream inputstream = new FileInputStream(
				System.getProperty("user.dir") + "/TestData/TD_TestData.xlsx");
		Workbook book = new XSSFWorkbook(inputstream);
		Sheet sheet = book.getSheetAt(0);
		return ExcelLibrary.getTableArray(sheet, "Test Case ID");
	}

	@DataProvider(name = "TORANOTEC_TC_002")
	public Object[][] TORANOTEC_TC_002TestData() throws Exception {
		FileInputStream inputstream = new FileInputStream(
				System.getProperty("user.dir") + "/TestData/TD_TestData.xlsx");
		Workbook book = new XSSFWorkbook(inputstream);
		Sheet sheet = book.getSheetAt(1);
		return ExcelLibrary.getTableArray(sheet, "Test Case ID");
	}
}
