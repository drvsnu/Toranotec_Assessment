package com.toranotec.generic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class ExcelLibrary {
	public static Object[][] getTableArray(Sheet sheet, String columnName) throws Exception {
		Object[][] tableDataArray = null;

		List<List<Object>> tableDataList = getTableListData(sheet, columnName);
		tableDataArray = new Object[tableDataList.size()][tableDataList.get(0).size()];

		for (int i = 0; i < tableDataList.size(); i++) {
			for (int j = 0; j < tableDataList.get(i).size(); j++) {
				tableDataArray[i][j] = tableDataList.get(i).get(j).toString();
			}
		}

		return tableDataArray;

	}

	public static List<List<Object>> getTableListData(Sheet sheet, String columnName) throws Exception {
		List<List<Object>> tableList = new ArrayList<List<Object>>();

		XSSFCell cell = null;
		String cellValue = "";
		int rowNo = getRowNo(columnName, sheet) + 1;

		for (int i = rowNo;; i++) {
			XSSFRow row = (XSSFRow) sheet.getRow(i);
			if (row == null || row.getCell(0) == null) {
				break;
			}

			List<Object> cellList = new ArrayList<Object>();
			for (int j = 0;; j++) {
				cell = row.getCell(j);
				if (cell != null) {
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if (!cell.getStringCellValue().isEmpty()) {
							cellValue = cell.getStringCellValue();
							break;

						}
					case Cell.CELL_TYPE_BOOLEAN:
						cellValue = cell.getBooleanCellValue() + "";
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cellValue = (long) Double.parseDouble(cell.getNumericCellValue() + "") + "";

						break;
					}

				} else {
					break;
				}

				cellList.add(cellValue);

			}

			tableList.add(cellList);

		}
		return tableList;

	}

	public static int getRowNo(String colmnName, Sheet sheet) {
		for (int i = 0;; i++) {
			XSSFRow row = (XSSFRow) sheet.getRow(i);
			// System.out.println("rows"+row.getLastCellNum());
			// System.out.println(row.getOutlineLevel());
			if (row != null && row.getCell(0) != null
					&& row.getCell(0).getStringCellValue().equalsIgnoreCase(colmnName)) {
				return i;
			}
		}

	}

}
