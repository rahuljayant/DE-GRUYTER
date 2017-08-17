package org.mps.degruyter.report;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.mps.degruyter.constant.GlobalConstatnts;
import org.mps.degruyter.util.CommonUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.NetworkMode;

public class ExtentManager extends CommonUtils
{
	final static Logger logger = Logger.getLogger(ExtentManager.class);
	
	private static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
	
	private static ExtentReports extent=null;  
	
	private static int counter=1;	  
	
	public synchronized static ExtentReports getReporter(String filePath)
    {		
		try 
		{		
			CommonUtils.loadLogger();			
			
			logger.info("------------------------------------------------------------------------");
			
			logger.info("Loading De Gruyter Project Setup");
			
			logger.info("------------------------------------------------------------------------");	
			
			deleteDirectory(filePath);
			
	    	extent = new ExtentReports(filePath, true, NetworkMode.ONLINE);
	    	
//	    	extent.startReporter(ReporterType.DB, (new File(filePath)).getParent() + File.separator + "extent.db");
//	    	
//	    	extent.x();
//	    	
//	    	extent.x("localhost", 27017);    	
	    	
	    	extent.loadConfig(GlobalConstatnts.EXTENTCONFIG_XML);      
	    	
	    	extent.addSystemInfo("Host Name", "Siddharth Mahakur").addSystemInfo("Environment", "QA");
	    	
	    	extent.assignProject("De-Gruyter"); 	    	
	   
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	 	return extent;    	
    }
    
    public synchronized static ExtentReports getReporter() 
    {
    	return extent;
    } 
    
    public static synchronized ExtentTest startTest(String testName) 
    {
		return startTest(testName, "");
    }

    public static synchronized ExtentTest startTest(String testName, String desc) 
    {
        ExtentTest test = extent.startTest("TC"+counter+++"-"+testName, desc);
        
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);       
        
        return test;
    }   
}