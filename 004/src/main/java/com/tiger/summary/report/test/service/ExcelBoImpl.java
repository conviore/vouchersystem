package com.tiger.summary.report.test.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelBoImpl implements ExcelBo {

	public void prepareExcel() {
		// TODO Auto-generated method stub

	}

	public InputStream getExcelFile() {
		// TODO Auto-generated method stub
		HSSFWorkbook workbook = new HSSFWorkbook();  
        HSSFSheet sheet = workbook.createSheet("sheet1");  
        {  
            // 创建表头  
            HSSFRow row = sheet.createRow(0);  
            HSSFCell cell = row.createCell((short) 0);  
            cell.setCellValue("id");  
            cell = row.createCell((short) 1);  
            cell.setCellValue("姓");  
            cell = row.createCell((short) 2);  
            cell.setCellValue("名");  
            cell = row.createCell((short) 3);  
            cell.setCellValue("年龄");  
 
            // 创建数据  
            // 第一行  
            row = sheet.createRow(1);  
            cell = row.createCell((short) 0);  
            cell.setCellValue("1");  
            cell = row.createCell((short) 1);  
            cell.setCellValue("张");  
            cell = row.createCell((short) 2);  
            cell.setCellValue("四");  
            cell = row.createCell((short) 3);  
            cell.setCellValue("23");  
 
            // 第二行  
            row = sheet.createRow(2);  
            cell = row.createCell((short) 0);  
            cell.setCellValue("2");  
            cell = row.createCell((short) 1);  
            cell.setCellValue("李");  
            cell = row.createCell((short) 2);  
            cell.setCellValue("六");  
            cell = row.createCell((short) 3);  
            cell.setCellValue("30");  
        }  
 
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        try {  
            workbook.write(baos);  
            baos.flush();
            baos.close();
            byte[] ba = baos.toByteArray();  
            ByteArrayInputStream bais = new ByteArrayInputStream(ba);  
            return bais;  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            return null;
        }  
       
      
	}

}
