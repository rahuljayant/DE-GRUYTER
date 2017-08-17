package org.mps.degruyter.constant;

import java.io.File;

/**
 ****************************************************************************************
 * Created By : Siddhartha Mahakur 
 * Created Date : April 27,2017 
 * Last Modified By: Siddhartha Mahakur
 * Last Modified Date:
 ****************************************************************************************
 *
 */
public class GlobalConstatnts 
{
	public static final String DATE_FORMAT_ONE = "dd";
	
	public static final String DATE_FORMAT_TWO = "dd-MM-yyyy/dd_MM_yyyy";
	
	public static final String LOG4J_XML = "src/main/resources/conf/log4j.xml";
	public static final String PROPERTIES_FILE = "src/main/resources/conf/config.properties";
	public static final String CHROME_DRIVER = "src/main/resources/drivers/chrome_driver_old.exe";
	public static final String IE_DRIVER = "src/main/resources/drivers/IEDriverServer.exe";
	
	public static final String PROCESSED_DIR = "C:/DeGruyter";
	public static final String QA_PATH = "/DG";
	
	public static final String URL = "http://mpstrakdemo.dg.mpstechnologies.com/mpstrak/login/";

	public static final String SUCCESS_SCREENSHOT_PATH="reports/screenshots/success";
	public static final String FAILURE_SCREENSHOT_PATH="reports/screenshots/failure";
	
	public static final String REPORT = "./reports/extent.html";
	public static final File EXTENTCONFIG_XML = new File("src/main/resources/conf/extent-config.xml");	
}
