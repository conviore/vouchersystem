package com.tiger.summary.report.test.service;

import java.io.InputStream;
import java.io.OutputStream;

public interface ExcelBo {

	void prepareExcel();
	
	InputStream getExcelFile();
	
}
