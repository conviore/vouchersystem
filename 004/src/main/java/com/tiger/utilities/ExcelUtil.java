package com.tiger.utilities;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelUtil {

	public static HSSFCellStyle getColHeaderStyle(HSSFWorkbook wb){
		  HSSFCellStyle cellStyle= wb.createCellStyle();
		  cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		  cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		  cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		  cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		  cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		  cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		  cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		  cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		  cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		  return cellStyle;
	}
	
	public static HSSFCellStyle getColContentStyle(HSSFWorkbook wb){
		  HSSFCellStyle cellStyle= wb.createCellStyle();
		  cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		  cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		  cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		  cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		  cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		  cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		  cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		  cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		  cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		  return cellStyle;
	}
	
	public static HSSFCellStyle getColContentMonStyle(HSSFWorkbook wb){
		  HSSFCellStyle cellStyle= wb.createCellStyle();
		  cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		  cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		  cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		  cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		  cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		  cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		  cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		  cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		  cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		  return cellStyle;
	}
	
}
