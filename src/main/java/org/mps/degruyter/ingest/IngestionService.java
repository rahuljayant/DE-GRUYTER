package org.mps.degruyter.ingest;

import java.io.File;
import org.apache.log4j.Logger;
import org.mps.degruyter.constant.GlobalConstatnts;
import org.mps.degruyter.exception.ExceptionMessageBuilder;
import org.mps.degruyter.report.ExtentManager;
import org.mps.degruyter.util.CommonUtils;
import org.mps.degruyter.util.FTPUtil;

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
public class IngestionService extends CommonUtils
{
	final static Logger logger = Logger.getLogger(IngestionService.class);
	
	FTPUtil uploadObj = new FTPUtil();
	
	boolean flag=false;
	
	public void ingestionService() throws InterruptedException
	{
		logger.info("------------------------------------------------------------------------");
		
		logger.info("Ingestion Service Start");
		
		logger.info("------------------------------------------------------------------------");	
		
		test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());			
		
		for(String Article:nameOfArticlesList)
		{
			logger.info("Article upload for ::"+Article);
			
			File[] files = new File(GlobalConstatnts.PROCESSED_DIR+fileSeparator()).listFiles();	
			
			for (File file : files)
	        {
	        	logger.info("File in Directory :-  "+file.getName());
	        	
	        	if(file.getName().endsWith(".zip"))
	        	{
	        		try
					{					
						uploadObj.setHostname(ftpHost);
						
						uploadObj.setUsername(ftpUser);	
						
						uploadObj.setPassword(ftpPass);
						
						uploadObj.setPort(ftpPort);
						
						logger.info(GlobalConstatnts.QA_PATH+fileSeparator()+Article +"  :  "+GlobalConstatnts.PROCESSED_DIR+fileSeparator()+"  :   "+file.getName());
						
						log(LogStatus.INFO,GlobalConstatnts.QA_PATH+fileSeparator()+Article +"  :  "+GlobalConstatnts.PROCESSED_DIR+fileSeparator()+"  :   "+file.getName());
						
						uploadObj.uploadFileToQAFTP(GlobalConstatnts.QA_PATH+fileSeparator()+Article, GlobalConstatnts.PROCESSED_DIR+fileSeparator(),file.getName());			
						
					} 
					catch (Exception e) 
					{						
						logger.info("Exception in userLogin :" + ExceptionMessageBuilder.trace(Thread.currentThread().getStackTrace(), e));
					}	        		       		
	        		flag=true;
	        	}	        	
	        }
			if(!flag)
			{
				logger.info("Directory is empty ingestion process won't start");		
				
				log(LogStatus.INFO,"Directory is empty ingestion process won't start");	
			}			
		}	
		extent.flush();		 
	 }
}
