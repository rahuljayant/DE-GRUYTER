package org.mps.degruyter.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.mps.degruyter.constant.GlobalConstatnts;
import org.mps.degruyter.report.ExtentManager;
import org.testng.Reporter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
/**
 ****************************************************************************************
 * Created By : Siddhartha Mahakur
 * Created Date : April 27,2017
 * Last Modified By : Siddhartha Mahakur
 * Last Modified Date: 
 ****************************************************************************************
 *
 */

public class CommonUtils
{
	final static Logger logger = Logger.getLogger(CommonUtils.class);

	final public static Properties prop = loadpropertiesFile(GlobalConstatnts.PROPERTIES_FILE);

	final public static String browserType = prop.getProperty("Browser_Type").trim();

	final public static String typesettingVendor = prop.getProperty("Typesetting_Vendor").trim();

	final public static String managingEditor = prop.getProperty("Managing_Editor").trim();

	final public static String myTaskRolePassword = prop.getProperty("MyTask_Role_Password").trim();

	final public static String externalEditor = prop.getProperty("External_Editor").trim();

	final public static String copyEditorVendor = prop.getProperty("CopyEditor_Vendor").trim();

	final public static String productionEditor = prop.getProperty("Production_Editor").trim();

	final public static String sysUsername = prop.getProperty("Username").trim();

	final public static String sysPassword = prop.getProperty("Password").trim();

	final public static String ftpHost = prop.getProperty("ftpHost").trim();

	final public static String ftpPort = prop.getProperty("ftpPort").trim();

	final public static String ftpUser = prop.getProperty("ftpUser").trim();

	final public static String ftpPass = prop.getProperty("ftpPass").trim();

	final public static String firstname = prop.getProperty("First_Name").trim();

	final public static String lastname = prop.getProperty("Last_Name").trim();

	final public static String userId = prop.getProperty("User_ID").trim();

	final public static String defaultPassword = prop.getProperty("Default_Password").trim();

	final public static String emailAddress = prop.getProperty("Email_Address").trim();

	final public static String userGroup = prop.getProperty("User_Group").trim();

	final public static int element = Integer.parseInt(prop.getProperty("Element_Number"));

	final public static String auditTrailToDate = prop.getProperty("Audit_Trail_To_Date_Range").trim();
	
	final public static String title = prop.getProperty("Title").trim();
	
	final public static String notes = prop.getProperty("Notes").trim();
	
	final public static int page = Integer.parseInt(prop.getProperty("Page"));

	final public static String auditTrailFromDate = prop.getProperty("Audit_Trail_From_Date_Range").trim();

	final public static String[] nameOfArticlesList = prop.getProperty("Articles_Name").trim().split(",");

	final public static String[] auditTrailType = prop.getProperty("Audit_Trail_Type").trim().split(",");

	final public static String[] auditTrailSubType = prop.getProperty("Audit_Trail_Sub_Type").trim().split(",");

	final public static ExtentReports extent = ExtentManager.getReporter(GlobalConstatnts.REPORT);

	final public static String alphaNumericRadomCharacter = alphaNumericRadomCharacter();

	final public static String fileSeparator = fileSeparator();

	final public static String lineSeparator = lineSeparator();

	public static ExtentTest test = null;

	public static SecureRandom random = null;
    
	public static void loadLogger() 
	{
		DOMConfigurator.configure(GlobalConstatnts.LOG4J_XML);
	}
	
	public static void log(LogStatus status,String message) 
	{	
		Reporter.log(message);  
		
		if(status==LogStatus.PASS)
		{
			test.log(LogStatus.PASS, message);			
		}
		else if(status==LogStatus.FAIL)
		{
			test.log(LogStatus.FAIL, message);
		}
		else if(status==LogStatus.INFO)
		{
			test.log(LogStatus.INFO, message);
		}		
		else if(status==LogStatus.WARNING)
		{
			test.log(LogStatus.WARNING, message);
		}
		else if(status==LogStatus.FATAL)
		{
			test.log(LogStatus.FATAL, message);
		}
		else if(status==LogStatus.SKIP)
		{
			test.log(LogStatus.SKIP, message);			
		}			
	}
	
	public static void screenShot(LogStatus status,String message) throws Exception 
	{	
		Reporter.log(message);  
		
		if(status==LogStatus.PASS)
		{
			test.log(LogStatus.PASS, message+":"+test.addScreenCapture(DriverManager.getScreenShot(GlobalConstatnts.SUCCESS_SCREENSHOT_PATH)));			
		}
		else if(status==LogStatus.FAIL)
		{
			test.log(LogStatus.FAIL, message+":"+test.addScreenCapture(DriverManager.getScreenShot(GlobalConstatnts.FAILURE_SCREENSHOT_PATH)));
		}		
	}
	
	public static void assertionError(AssertionError e ) 
	{		
        Reporter.log(""+e+"");
        
        test.log(LogStatus.ERROR, e);
	}
	
	public static Properties loadpropertiesFile(String FilePath)
	{
		FileInputStream fis = null;
		
		Properties propobj = new Properties();
		try 
		{
			fis = new FileInputStream(FilePath);
			
			propobj.load(fis);
		}
		catch (Exception e)
		{
			logger.info("Exception in file Loading");
		}
		return propobj;
	}
	
	public final static String searchFile(File directory, String filename) throws IOException 
    {
        File dir = new File(directory, filename);

        if (dir.exists()) 
        {
            return filename;
        } 
        else 
        {
        	logger.info("File doesn't Exists on Given location:" + dir);     
        	
            return null;
        }
    }
	
	public final static String today_DateOnly() 
	{
        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstatnts.DATE_FORMAT_ONE);
        
        Date dt = new Date();
        
        String dateObj = dateFormat.format(dt);
        
        return dateObj;
    }	
	
	public static final String fileSeparator() 
	{
		return System.getProperty("file.separator");
	}

	public static final String lineSeparator() 
	{
		return System.getProperty("line.separator");
	}	 
	
	public static String imgRelativePath(String path,int pos)
	{
		String trimStr =null;
		
		int count=0;
		
		String match = "/";	
			
		path=path.replace("\\", "/");
		
		//logger.info("current image Location	 :"+path);
		
		int index = path.indexOf(match);
		
		while (index >= 0) 
		{		
			if (count == pos)
			{
				trimStr = path.substring(index, path.length());	
				
				trimStr="."+trimStr;		
			}
			
			index = path.indexOf(match, index + 1);		
			
			count=count+1;
		}
		return trimStr;	
	}
	
	public static List<String> multiTokenizer(String str)
	{
		ArrayList<String> list = new ArrayList<String>();
		
		StringTokenizer multiTokenizer = new StringTokenizer(str, "[-+.^:;,\\]");
		
		while (multiTokenizer.hasMoreTokens())
		{
		   list.add(multiTokenizer.nextToken());
		}
		return list;
	}	
	
	public static String alphaNumericRadomCharacter()
	{
		random = new SecureRandom();
		
		return new BigInteger(128, random).toString(16);  
	}
	
	public static void deleteDirectory(String filePath) throws IOException
	{		
		FileUtils.cleanDirectory(new File(filePath.substring(0, 9))); 
		
		logger.info(filePath.substring(0, 9)+" is removed");		
	}
}