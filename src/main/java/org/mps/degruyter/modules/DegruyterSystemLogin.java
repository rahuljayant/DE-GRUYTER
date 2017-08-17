package org.mps.degruyter.modules;

import org.apache.log4j.Logger;
import org.mps.degruyter.report.ExtentManager;
import org.mps.degruyter.util.CommonUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

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
public class DegruyterSystemLogin  extends CommonUtils{

	final static Logger logger = Logger.getLogger(DegruyterSystemLogin.class);

	public DegruyterSystemLogin(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver driver;

	@FindBy(id = "userId")
	public static WebElement UserId;

	@FindBy(id = "pwd")
	public static WebElement Pwd;

	@FindBy(css = ".btn-middle.btn.btn-inverse.pull-right")
	public static WebElement Login;

	@CacheLookup
	@FindBy(linkText = "Logout")
	public static WebElement LogOutLink;

	@FindBy(css = ".alert-error.error-login>center")
	public static WebElement errorLogin;
	
	public void deGruyterLogin() throws Exception 
	{			
		try 
		{		
			logger.info("------------------------------------------------------------------------");
			
			logger.info("User Login");
			
			logger.info("------------------------------------------------------------------------");
			
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
			
			test.assignCategory(browserType);
			
			UserId.sendKeys(sysUsername);
			
			Pwd.sendKeys(sysPassword);
			
			Login.click();				
			
			if(LogOutLink.getText().toString().equals("Logout"))
			{				
				screenShot(LogStatus.PASS,"Successfully Logged in ?:"+!LogOutLink.getText().toString().isEmpty());					
								
				Assert.assertEquals(test.getRunStatus(), LogStatus.PASS);
			}
			else if(errorLogin.getText().toString().contentEquals("Invalid Username/Password. Please try again."))
			{		
				screenShot(LogStatus.FAIL,": Error Message :"+errorLogin.getText().toString());		
				
				Assert.assertEquals(test.getRunStatus(), LogStatus.FAIL);
			}			
		} 
		catch(AssertionError e)
		{		
			assertionError(e);
			
			screenShot(LogStatus.FAIL, driver.getTitle().toString());
		}
		finally 
		{
			extent.flush();
		}
	}
}
