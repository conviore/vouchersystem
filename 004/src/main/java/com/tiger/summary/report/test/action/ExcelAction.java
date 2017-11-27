package com.tiger.summary.report.test.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.tiger.BaseAction;
import com.tiger.summary.report.test.service.ExcelBo;

public class ExcelAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9041550959846466124L;

	@Autowired
	private ExcelBo excelBo;
	
	private String filename;
	
	private InputStream excelFile;
	
	private InputStream downloadStream;
	
	public String exportExcel(){
		
		this.downloadStream=excelBo.getExcelFile();
		this.filename="test.xls";
		return Action.SUCCESS;
		
	}

	/**
	 * @return the excelFile
	 */
	public InputStream getExcelFile() {
		return excelFile;
	}

	/**
	 * @param excelFile the excelFile to set
	 */
	public void setExcelFile(InputStream excelFile) {
		this.excelFile = excelFile;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the downloadStream
	 */
	public InputStream getDownloadStream() {
		return downloadStream;
	}

	/**
	 * @param downloadStream the downloadStream to set
	 */
	public void setDownloadStream(InputStream downloadStream) {
		this.downloadStream = downloadStream;
	}

	
}
