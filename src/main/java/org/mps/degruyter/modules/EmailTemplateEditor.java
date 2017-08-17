package org.mps.degruyter.modules;

import org.apache.log4j.Logger;
import org.mps.degruyter.report.ExtentManager;
import org.mps.degruyter.util.CommonUtils;
import org.mps.degruyter.util.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import com.relevantcodes.extentreports.LogStatus;

/**
 ****************************************************************************************
 * Created By : Siddhartha Mahakur
 * Created Date : May 24,2017
 * Last Modified By : Siddhartha Mahakur
 * Last Modified Date: 
 ****************************************************************************************
 *
 */
public class EmailTemplateEditor extends CommonUtils
{
	final static Logger logger = Logger.getLogger(EmailTemplateEditor.class);

	public EmailTemplateEditor(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver driver;

	@FindBy(linkText = "Admin")
	public static WebElement AdminLink;

	@FindBy(linkText = "Email Template Editor")
	public static WebElement emailTemplateEditorLink;

	@FindBy(id = "jid")
	public static WebElement jid;

	@FindBy(linkText = "Journal")
	public static WebElement Journal;

	@FindBy(name = "find")
	public static WebElement find;

	@FindBy(id = "divGrid")
	public static WebElement grid;

	@FindBy(xpath = "//td[@id='dashboardGrid_pager_center']//select")
	public static WebElement pagination;
	
	@FindBy(xpath = "//td[@id='emailGrid_pager_center']//select")
	public static WebElement paginationEmailTemplate;

	@FindBy(id = "tab9")
	public static WebElement journalEmailTemplateLink;

	@FindBy(id = "userGroupId")
	public static WebElement userGroupId;

	@FindBy(id = "type")
	public static WebElement templateName;

	@FindBy(id = "emailAddressId")
	public static WebElement emailAddressId;

	@FindBy(id = "subjectId")
	public static WebElement subjectId;

	@FindBy(id = "cke_1_contents")
	public static WebElement content;

	@FindBy(id = "save")
	public static WebElement save;
	
	@FindBy(id = "divGrid")
	public static WebElement Grid;

	int rows = 0;
	
	int newRows = 0;

	String jr = null;
	
	String result = null;
	
	String alert=null;

	String tempName = "auto";

	String email = "auto@adi-mps.com";

	String subject = "auto";
	
	public void adminMenu(int sub_menu)throws Exception
	{	
		new Actions(driver).moveToElement(AdminLink).perform();
		
		switch (sub_menu) 
		{
			case 1:					
					emailTemplateEditorLink.click();										
					break;
			
			default:				
					logger.info("Invalide sub-menu enter:" + sub_menu);				
					break;
		}
	}
	
	public void journalEmailTemplateCreate() throws Exception
	{
		try 
		{	
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
			
			adminMenu(1);
			
			Journal.click();			
			
			DriverManager.randomlyselectId("jid");
			
			find.click();				
			
			if(driver.findElement(By.xpath("//table[@id='dashboardGrid']/tbody/tr[1]/td[6]")).getText().trim().equals("Drafted"))
			{				
				logger.info("Restart due to Drafted journal Status");
				
				journalEmailTemplateCreate();
			}
			else
			{
				logger.info("============================>Admin>Email Template Editor>Journal");
				
				jr=driver.findElement(By.xpath("//table[@id='dashboardGrid']/tbody/tr[1]/td[2]")).getText().trim();			  
				
				logger.info(jr);
				
				driver.findElement(By.xpath("//table[@id='dashboardGrid']/tbody/tr/td[2]/a")).click();	
				
				journalEmailTemplateLink.click();
				
				journalEmailTemplateCommonTask();	
				
				log(LogStatus.PASS,"Successfully created a template");
			}
						
		}
		catch (AssertionError e) 
		{
			assertionError(e);
			
			screenShot(LogStatus.FAIL, "journal Email Template Create");
		}
		finally 
		{
			extent.flush();
		}
	}
	
	public void journalEmailTemplateEdit() throws Exception
	{
		try 
		{	
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
			
			adminMenu(1);
			
			rows =	DriverManager.gridHandler(Grid, "load_emailGrid", paginationEmailTemplate);
			
			logger.info("Total no of rows in Email Template List " + rows);
			
			for (int i = 1; i <= rows; i++) 
			{			
				result = driver.findElement(By.xpath("//table[@id='emailGrid']/tbody/tr[" + i + "]/td[4]")).getText().trim();				
				
				if(tempName.equals(result))
				{
					logger.info("After Searched Template Name [" + i + "] :" + result);
					
					driver.findElement(By.xpath("//table[@id='emailGrid']/tbody/tr[" + i + "]/td[1]/a/img")).click();			
					
					journalEmailTemplateCommonTask();
					
					log(LogStatus.PASS,"Successfully edited a template");
				}				
			}						
		}
		catch (AssertionError e) 
		{
			assertionError(e);
			
			screenShot(LogStatus.FAIL, "journal Email Template Edit");
		}
		finally 
		{
			extent.flush();
		}
	}
	
	public void journalEmailTemplateDelete() throws Exception
	{
		try 
		{	
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
			
			adminMenu(1);
			
			rows =	DriverManager.gridHandler(Grid, "load_emailGrid", paginationEmailTemplate);
			
			logger.info("Total no of rows in Email Template List " + rows);
			
			for (int i = 1; i <= rows; i++) 
			{			
				result = driver.findElement(By.xpath("//table[@id='emailGrid']/tbody/tr[" + i + "]/td[4]")).getText().trim();				
				
				if(tempName.equals(result))
				{
					logger.info("After Searched Template Name [" + i + "] :" + result);
					
					driver.findElement(By.xpath("//table[@id='emailGrid']/tbody/tr[" + i + "]/td[2]/a/img")).click();
					
					alert=DriverManager.isAlertPresent();
					
					logger.info(alert);
					
					log(LogStatus.PASS,alert);
				}				
			}						
		}
		catch (AssertionError e) 
		{
			assertionError(e);
			
			screenShot(LogStatus.FAIL, "journal Email Template Delete");
		}
		finally 
		{
			extent.flush();
		}
	}

	private void journalEmailTemplateCommonTask() throws InterruptedException 
	{
		DriverManager.randomlyselectId("userGroupId");
		
		DriverManager.randomlyselectId("userGroupId");		
		
		templateName.clear();
		
		templateName.sendKeys(tempName);
		
		emailAddressId.clear();
		
		emailAddressId.sendKeys(email);

		subjectId.clear();
		
		subjectId.sendKeys(subject);	
		
		content.click();		
		
		DriverManager.randomlyselectId("placeHolderId");
		
		DriverManager.randomlyselectId("placeHolderId");
		
		DriverManager.randomlyselectId("placeHolderId");	
		
		save.click();			
	}	
}
