package org.mps.degruyter.modules;

import java.util.Random;
import java.util.Set;
import org.apache.log4j.Logger;
import org.mps.degruyter.constant.GlobalConstatnts;
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
 * Created Date : May 08,2017 
 * Last Modified By : Siddhartha Mahakur 
 * Last Modified Date:
 ****************************************************************************************
 *
 */
public class MyTasksDynamicSelection extends CommonUtils 
{
	final static Logger logger = Logger.getLogger(MyTasksDynamicSelection.class);
	
	final public static String afterTaskCompleteMsg="Task Completed Successfully.";
	
	final public static String beforeTaskCompleteMsg="Please Complete the Below Action to Proceed";

	public MyTasksDynamicSelection(WebDriver driver)
	{
		this.driver = driver;
	}

	public WebDriver driver;

	
	//My Tasks Articles - Management

	@FindBy(linkText = "Tasks")
	public static WebElement tasksLink;


	@FindBy(linkText = "My Tasks")
	public static WebElement myTasksLink;


	@FindBy(linkText = "Articles")
	public static WebElement articlesLink;


	@FindBy(partialLinkText = "Issues")
	public static WebElement issuesLink;

	@FindBy(id = "gbox_userGrid")
	public static WebElement articleMgtGrid;

	@FindBy(css = ".ui-pg-selbox")
	public static WebElement Pagination;

	
	
	//My Tasks Issues - Management
	@FindBy(id = "frm1Submit")
	public static WebElement Submit;

	@FindBy(id = "gbox_userGrid")
	public static WebElement issuesMgtGrid;
	
	@FindBy(id = "completeTask")
	public static WebElement issueCompleteTask;
	
	@FindBy(id = "completeTask1")
	public static WebElement articleCompleteTask;
	
	@FindBy(id = "fancybox-frame")
	public static WebElement fancyBoxFrame;	

	@FindBy(id="toComplete")
	public static WebElement fancyBoxCompleteButton;	
	
	// Home Page 	
	@FindBy(linkText = "Home")
	public static WebElement homeLink;
	
	@FindBy(id = "gbox_remoteIssueGridUrl")
	public static WebElement taskDashBoardIssueGrid;
	
	@FindBy(id="exportfeed")
	public static WebElement articleAssignToUser;

	@FindBy(id = "exportfeedIssue")
	public static WebElement issueAssignToUser;

	@FindBy(xpath = "//td[@id='userGrid_pager_center']//select")
	public static WebElement articleTasksPagination;

	@FindBy(xpath = "//td[@id='remoteIssueGridUrl_pager_center']//select")
	public static WebElement issueTasksPagination;

	@FindBy(id = "fancybox-frame")
	public static WebElement fancyboxframe;	
	
	@FindBy(name="flag")
	public static WebElement assignButton;		
			
	@FindBy(id="completeTaskIssueDashboardId")
	public static WebElement completeTaskIssueDashboard;		
	
	@FindBy(id="EDIT")
	public static WebElement editButton;		
	
	@FindBy(id="defaultSearch")
	public static WebElement defaultFilter;		
	
	//Login Application
	@FindBy(id = "userId")
	public static WebElement userId;

	@FindBy(id = "pwd")
	public static WebElement Pwd;

	@FindBy(css = ".btn-middle.btn.btn-inverse.pull-right")
	public static WebElement login;

	@FindBy(linkText = "Logout")
	public static WebElement LogOutLink;	
	
	
	//Manage Users

	@FindBy(linkText = "Manage Users")
	public static WebElement manageUserLink;		

	@FindBy(linkText = "Admin")
	public static WebElement adminLink;
	
	@FindBy(id = "divGrid")
	public static WebElement Grid;
	
	@FindBy(id = "changePassword1")
	public static WebElement changePassword;
	
	@FindBy(id="npwd")
	public static WebElement newPassword;
	
	@FindBy(id="n2pwd")
	public static WebElement enterNewPassword;		
		
	@FindBy(id="button_id")
	public static WebElement confirmButton;				
	
	@FindBy(id = "save_id")
	public static WebElement saveButton;

	String screenShotPath = null;
	
	String selectUserToAssign=null;
	
	String alertMsg=null;
	
	String result = null;
	
	String groupName=null;	
	
	String isVolume=null;
	
	String isIssue=null;
	
	String vol=null;
	
	String iss=null;
	
	String manuscriptNumber=null;
	
	String manuscript=null;
	
	String assignee=null;
	
	int rowsAfterComplete=0;
	
	int rows =0;	
	
	Boolean isPresent=false;	
	
	Boolean articleFlag=false;	
	

	public void myTasks(int sub_menu) throws Exception 
	{
		new Actions(driver).moveToElement(tasksLink).build().perform();

		switch (sub_menu) 
		{
			case 1:
				new Actions(driver).moveToElement(myTasksLink).contextClick(articlesLink).build().perform();					
				articlesLink.click();				
				break;
	
			case 2:
				new Actions(driver).moveToElement(myTasksLink).contextClick(articlesLink).contextClick(issuesLink).build().perform();			
				issuesLink.click();				
				break;
				
			case 3:
				new Actions(driver).moveToElement(adminLink).contextClick(manageUserLink).build().perform();
				manageUserLink.click();		
				
				break;		
			
			case 4:				
				homeLink.click();					
				break;				
			
	
			default:
				logger.info("Invalide sub-menu enter:[" + sub_menu + "]");				
				break;
		}
	}
	
	/*
	 * Login Wwith Adim credential at admin Home Page myTasksIssuesManagement()
	 * perform the assign to user operation and find out the Manage users First
	 * Name.
	 * 
	 */
	public void myTasksIssuesManagementDynamicSelection() throws Exception
	{		
		try 
		{	
			logger.info("------------------------------------------------------------------------");
			
			logger.info("Tasks>My Tasks>Issues");
			
			logger.info("------------------------------------------------------------------------");				
			
			myTasks(4);
			
			isPresent=defaultFilter.isSelected();
			
			if(isPresent==true)
			{
				editButton.click();
			}
			
			rows = DriverManager.gridHandler(taskDashBoardIssueGrid, "load_remoteIssueGridUrl",issueTasksPagination);
			
			if (rows > 0)
			{
				int i=new Random().nextInt(rows);
				
				logger.info("Selction Possition from Article Tasks LIst :"+i);	
				
				log(LogStatus.INFO, "Selction Possition from Issue Tasks LIst :"+i);

				groupName = driver.findElement(By.xpath("//table[@id='remoteIssueGridUrl']/tbody/tr[" + i + "]/td[16]/a")).getText().trim();	
								
				isVolume=driver.findElement(By.xpath("//table[@id='remoteIssueGridUrl']/tbody/tr[" + i + "]/td[10]/a")).getText().trim();
				
				isIssue=driver.findElement(By.xpath("//table[@id='remoteIssueGridUrl']/tbody/tr[" + i + "]/td[11]/a")).getText().trim();	
				
				driver.findElement(By.xpath("//table[@id='remoteIssueGridUrl']/tbody/tr[" + i + "]/td[1]/input")).click();
				
				log(LogStatus.INFO, "Group Name :"+groupName+" :Volume :"+isVolume+" :Issue :"+isIssue);
				
				logger.info("Group Name :"+groupName+" :Volume :"+isVolume+" :Issue :"+isIssue);
				
				issueAssignToUser.click();

				DriverManager.switchToFrame(fancyboxframe);

				selectUserToAssign = DriverManager.randomlyselectId("publisherIdID");
				
				logger.info(selectUserToAssign);
				

				if (selectUserToAssign.indexOf(" ") > 0) 
				{
					String subStr = selectUserToAssign.substring(0, selectUserToAssign.indexOf(" "));

					logger.info(subStr);
					
					assignButton.click();			

					editManageUsersPassword(subStr);			
				}	
			}	
		} 
		catch (AssertionError e)
		{
			screenShot(LogStatus.FAIL, e.getMessage());		
		}
	}
	
	/*
	 * 
	 * At Manage Users editManageUsersPassword() reset the password and
	 * 
	 * @return UserId, password.
	 * 
	 * completeTask() complete the assigned user task
	 * 
	 */
	private void editManageUsersPassword(String subStr) throws Exception 
	{
		try
		{				
			myTasks(3);				
			
			rows =DriverManager.gridHandler(Grid, "load_userGrid", Pagination);
			
			if (rows > 0)
			{
				for (int i = 1; i <= rows; i++) 
				{
					result = driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[7]")).getText().trim();								
					
					if(subStr.equals(result))
					{
						log(LogStatus.INFO, "Search for User [" + i + "] :" + result);
						
						driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[1]/a/img")).click();
						
						String user=userId.getAttribute("value");
						
						changePassword.click();
						
						String parentWindow = driver.getWindowHandle();
						
						Set<String> handles =  driver.getWindowHandles();
						
					    for(String windowHandle  : handles)
					    {
						   
					       if(!windowHandle.equals(parentWindow))
					        {		    	   
					    	     driver.switchTo().window(windowHandle);
					    	       
					    	     newPassword.clear();
					    	        
					    	     newPassword.sendKeys("Mps@1234");
					    	        
					    	     enterNewPassword.sendKeys("Mps@1234");
					    	        
					    	     confirmButton.click();				    	        
					    	     
					    	     alertMsg=DriverManager.isAlertPresent();
					    	     
					    	     logger.info(alertMsg); 
					    	     
					    	     driver.switchTo().window(parentWindow);
					         }
					    }			    
					    
					    saveButton.click();				    
					    
					    alertMsg=DriverManager.isAlertPresent();
					    
					    logger.info("Reset Password :"+alertMsg); 
					    
					    log(LogStatus.INFO, "Reset Password :"+alertMsg);					   
					    
					    completeTask(user, "Mps@1234");				  
					    
						break;
					}		
				}
			}			
		} 
		catch (AssertionError e)
		{
			screenShot(LogStatus.FAIL, e.getMessage());		
		}
	}		


	/*
	 * issueCompleteTask() will complete
	 * the assigned issue of assigned user.
	 */
	private void completeTask(String UserName, String Password) throws Exception
	{
		try 
		{		
			applicationReLogin(UserName,Password);
			
			logger.info("Group Name :"+groupName);
			
			if (groupName.equals("Typesetting Vendor") || groupName.equals("CopyEditor Vendor") || groupName.equals("External Editor"))
			{	
				
				if (articleFlag == true)
				{					
					rows=DriverManager.gridHandler(Grid, "load_userGrid", articleTasksPagination);
					
					logger.info("rows for "+groupName +" : "+rows);
					
					screenShotPath = DriverManager.getScreenShot(GlobalConstatnts.SUCCESS_SCREENSHOT_PATH);		

					if (rows > 0)
					{			
						for (int i = 1; i <= rows; i++) 
						{							
							manuscript = driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[12]/a")).getText().trim();	
							
							if (manuscriptNumber.equals(manuscript))
							{
								log(LogStatus.INFO, "Manuscript Number  :" + manuscript +" are going to complete");
								
								assignee=driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[20]/a")).getText().trim();	
								
								driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[1]/input")).click();															
								
								
								
								

								break;							
							}	
						}
					}
					else
					{
						logger.info("There is no items in article tasks List");
						
						log(LogStatus.PASS,"Tasks Article Management Grid items :" + rows + test.addScreenCapture(screenShotPath));
					}					
				}
				else
				{
					rows=DriverManager.gridHandler(taskDashBoardIssueGrid, "load_remoteIssueGridUrl",issueTasksPagination);
					
					logger.info("rows for "+groupName +" : "+rows);
					
					screenShotPath = DriverManager.getScreenShot(GlobalConstatnts.SUCCESS_SCREENSHOT_PATH);
					
					if (rows > 0)
					{			
						for (int i = 1; i <= rows; i++) 
						{					
							vol=driver.findElement(By.xpath("//table[@id='remoteIssueGridUrl']/tbody/tr[" + i + "]/td[10]/a")).getText().trim();
							
							iss=driver.findElement(By.xpath("//table[@id='remoteIssueGridUrl']/tbody/tr[" + i + "]/td[11]/a")).getText().trim();	
							
							if (isVolume.equals(vol) && isIssue.equals(iss))
							{					
								log(LogStatus.INFO, "Volume Number  :" + vol + "Issue Number  :" + iss + " are going to complete");
								
								driver.findElement(By.xpath("//table[@id='remoteIssueGridUrl']/tbody/tr[" + i + "]/td[1]/input")).click();
								
								completeTaskIssueDashboard.click();
								
								DriverManager.switchToFrame(fancyBoxFrame);
								
								DriverManager.scrollUp(DriverManager.getY(fancyBoxCompleteButton));
								
								fancyBoxCompleteButton.click();							
								
								break;
							}						
						}
					}
					else
					{
						logger.info("There is no items in issue tasks List");
						
						log(LogStatus.PASS,"Tasks Article Management Grid items :" + rows + test.addScreenCapture(screenShotPath));
					}
				}				
			}
			else
			{
				if (articleFlag == true)
				{
					myTasks(1);//article sub -menu
					
					rows = DriverManager.gridHandler(issuesMgtGrid, "load_userGrid", Pagination);
					
					screenShotPath = DriverManager.getScreenShot(GlobalConstatnts.SUCCESS_SCREENSHOT_PATH);
			
					log(LogStatus.PASS,"Tasks Article Management Grid items :" + rows + test.addScreenCapture(screenShotPath));
			
					if(rows > 0)
					{
						for (int i = 1; i <= rows; i++) 
						{
							manuscript = driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[12]/a")).getText().trim();							
				
							if (manuscriptNumber.equals(manuscript))
							{
								log(LogStatus.INFO, "Manuscript Number  :" + manuscript +" are going to complete");
								
								driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[1]/input")).click();
								
								articleCompleteTask.click();
								
								DriverManager.switchToFrame(fancyBoxFrame);						
								
								boolean btn=beforeTaskCompleteMsg.isEmpty();							
									
								if(btn==true)
								{
									DriverManager.scrollUp(DriverManager.getY(fancyBoxCompleteButton));
									
									fancyBoxCompleteButton.click();	
								}
								else
								{
									DriverManager.fancyBoxClose("fancybox-close");
								}								
								DriverManager.verifyLinkTextPresent("My Tasks Article");
								
								break;							
							}		
						}					
					}
					else
					{
						logger.info("There is no items in artilce tasks List");
						
						log(LogStatus.PASS,"Tasks Article Management Grid items :" + rows + test.addScreenCapture(screenShotPath));
					}			
					
				}
				else
				{
					myTasks(2);// issue sub-menu
					
					rows = DriverManager.gridHandler(issuesMgtGrid, "load_userGrid", Pagination);
					
					screenShotPath = DriverManager.getScreenShot(GlobalConstatnts.SUCCESS_SCREENSHOT_PATH);
			
					log(LogStatus.PASS,"Tasks Article Management Grid items :" + rows + test.addScreenCapture(screenShotPath));
			
					if(rows > 0)
					{
						for (int i = 1; i <= rows; i++) 
						{
							vol = driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[10]/a")).getText().trim();
							
							iss = driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[11]/a")).getText().trim();
				
							if (isVolume.equals(vol) && isIssue.equals(iss))
							{
								log(LogStatus.INFO, "Volume Number  :" + vol + "Issue Number  :" + iss + " are going to complete");
								
								driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr/td[1]/input")).click();
								
								issueCompleteTask.click();
								
								DriverManager.switchToFrame(fancyBoxFrame);
								
								DriverManager.scrollUp(DriverManager.getY(fancyBoxCompleteButton));
								
								fancyBoxCompleteButton.click();								
								
								DriverManager.verifyLinkTextPresent("My Tasks Issue");
								
								break;							
							}		
						}					
					}
					else
					{
						logger.info("There is no items in issue tasks List");
						
						log(LogStatus.PASS,"Tasks Issue Management Grid items :" + rows + test.addScreenCapture(screenShotPath));
					}
				}			
			}			
		} 
		catch (AssertionError e)
		{
			screenShot(LogStatus.FAIL, e.getMessage());		
		}
	}
	
	

	private void applicationReLogin(String userName, String passWord)
	{
		LogOutLink.click();
		
		userId.sendKeys(userName);
		
		Pwd.sendKeys(passWord);
		
		login.click();
	}
	
	
	
	public void myTasksArticlesManagementDynamicSelection() throws Exception 
	{
		
		logger.info("------------------------------------------------------------------------");
		
		logger.info("Tasks>My Tasks>Articles");
		
		logger.info("------------------------------------------------------------------------");
		
		myTasks(4);
		
		isPresent=defaultFilter.isSelected();
		
		if(isPresent==true)
		{
			editButton.click();
		}
		
		rows=DriverManager.gridHandler(Grid,"load_userGrid",articleTasksPagination);
		
		if(rows >0)
		{
			int i=new Random().nextInt(rows);
			
			logger.info("Selction Possition from Issue Tasks LIst :"+i);	
			
			log(LogStatus.INFO, "Selction Possition from Issue Tasks LIst :"+i);
			
			manuscriptNumber=driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[12]/a")).getText().trim();
			
			logger.info("Manuscript Number :"+manuscriptNumber);
			
			groupName=driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[22]/a")).getText().trim();
			
			driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[1]/input")).click();
			
			articleAssignToUser.click();
			
			DriverManager.switchToFrame(fancyboxframe);
			
			selectUserToAssign = DriverManager.randomlyselectId("publisherIdID");
			
			logger.info(selectUserToAssign);

			if (selectUserToAssign.indexOf(" ") > 0) 
			{
				String subStr = selectUserToAssign.substring(0, selectUserToAssign.indexOf(" "));

				logger.info(subStr);
				
				assignButton.click();			
				
				articleFlag=true;
				
				editManageUsersPassword(subStr);			
			}	
		}
	}
}

