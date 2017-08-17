package org.mps.degruyter.common;

import org.apache.log4j.Logger;
import org.mps.degruyter.constant.GlobalConstatnts;
import org.mps.degruyter.ingest.IngestionService;
import org.mps.degruyter.modules.AuditTrail;
import org.mps.degruyter.modules.DegruyterSystemLogin;
import org.mps.degruyter.modules.EmailTemplateEditor;
import org.mps.degruyter.modules.ManageElements;
import org.mps.degruyter.modules.ManageUsers;
import org.mps.degruyter.modules.MyTasksDynamicSelection;
import org.mps.degruyter.modules.MyTasksSelection;
import org.mps.degruyter.util.CommonUtils;
import org.mps.degruyter.util.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

/**
 ****************************************************************************************
 * Created By : Siddhartha Mahakur
 * Created Date : April 27,2017
 * Last Modified By : Siddhartha Mahakur
 * Last Modified Date: 
 ****************************************************************************************
 *
 */

public class TestCasesRunner extends CommonUtils 
{
	final static Logger logger = Logger.getLogger(TestCasesRunner.class);

	WebDriver driver = DriverManager.invokeApp(browserType, GlobalConstatnts.URL);

	DegruyterSystemLogin loginPage = PageFactory.initElements(driver, DegruyterSystemLogin.class);

	ManageUsers mngUsr = PageFactory.initElements(driver, ManageUsers.class);

	ManageElements mngEle = PageFactory.initElements(driver, ManageElements.class);

	IngestionService job = PageFactory.initElements(driver, IngestionService.class);

	AuditTrail at = PageFactory.initElements(driver, AuditTrail.class);

	MyTasksDynamicSelection dynMyTasks = PageFactory.initElements(driver, MyTasksDynamicSelection.class);
	
	MyTasksSelection myTask = PageFactory.initElements(driver, MyTasksSelection.class);		
	
	EmailTemplateEditor emailTemplate=PageFactory.initElements(driver, EmailTemplateEditor.class);
	
	@Test(priority = 1, enabled = true)
	private void Login() throws Exception 
	{
		loginPage.deGruyterLogin();
	}

	@Test(priority = 2, enabled = true)
	private void ManageUsers() throws Exception 
	{		
		mngUsr.addManageUsers();
		
		mngUsr.editManageUsers();	
		
		mngUsr.deleteManageUsers(userId);
		
		mngUsr.activeDeactiveManageUsers(userId + "test");
	}

	@Test(priority = 3, enabled = true)
	private void ManageElements() throws Exception
	{		
		mngEle.addManageElements();
		
		mngEle.editManageElements();	
		
		mngEle.activeDeactiveManageElements();
		
		mngEle.deleteManageElements();
		
		mngEle.journalIssueMakeUpManageElements("ZNA1", 13);
	}

	@Test(priority = 4, enabled = false)
	private void Tasks() throws Exception
	{		
		myTask.myTasksArticlesManagement();
		
		myTask.myTasksIssuesManagement();
	}
	
	@Test(priority = 5, enabled = true)
	private void emailTemplateEditor() throws Exception 
	{		
		emailTemplate.journalEmailTemplateCreate();
	
		emailTemplate.journalEmailTemplateEdit();
		
		emailTemplate.journalEmailTemplateDelete();
	}

	@Test(priority = 6, enabled = false)
	private void Ingestion() throws Exception 
	{		
		job.ingestionService();
	}

	@AfterTest
	private void tearDown() throws Exception 
	{		
		extent.endTest(test);
		
		DriverManager.driverClose();
	}	
}
