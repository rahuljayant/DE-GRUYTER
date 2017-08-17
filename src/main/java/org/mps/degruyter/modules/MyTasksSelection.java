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
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

/**
 ****************************************************************************************
 * Created By : Siddhartha Mahakur
 * Created Date : May 15,2017 
 * Last Modified By : Siddhartha Mahakur 
 * Last Modified Date:
 ****************************************************************************************
 *
 */
public class MyTasksSelection extends CommonUtils
{
	
	final static Logger logger = Logger.getLogger(MyTasksSelection.class);
	
	public MyTasksSelection(WebDriver driver)
	{
		this.driver = driver;
	}

	public WebDriver driver;

	@FindBy(linkText = "Tasks")
	public static WebElement tasksLink;

	@FindBy(linkText = "My Tasks")
	public static WebElement myTasksLink;

	@FindBy(linkText = "Articles")
	public static WebElement articlesLink;

	@FindBy(partialLinkText = "Issues")
	public static WebElement issuesLink;

	@FindBy(linkText = "Home")
	public static WebElement homeLink;

	@FindBy(linkText = "Logout")
	public static WebElement LogOutLink;

	@FindBy(linkText = "Send Back To Queue")
	public static WebElement sendBackToQueue;

	@FindBy(linkText = "Assign To Me")
	public static WebElement assignToMe;

	@FindBy(id = "userId")
	public static WebElement userId;

	@FindBy(id = "pwd")
	public static WebElement Pwd;

	@FindBy(css = ".btn-middle.btn.btn-inverse.pull-right")
	public static WebElement login;

	@FindBy(id = "defaultSearch")
	public static WebElement defaultFilter;

	@FindBy(id = "EDIT")
	public static WebElement editButton;

	@FindBy(id = "divGrid1")
	public static WebElement commonDashBoardIssueTasksList;

	@FindBy(id = "divGrid")
	public static WebElement commonDashBoardArticleTasksList;

	@FindBy(xpath = "//td[@id='userGrid_pager_center']//select")
	public static WebElement articleTasksPagination;

	@FindBy(xpath = "//td[@id='remoteIssueGridUrl_pager_center']//select")
	public static WebElement issueTasksPagination;		
	
	@FindBy(xpath="//input[@value='Ok']")
	public static WebElement okButton;

	int rows = 0;

	int newRows = 0;

	String assignee = null;

	String title = null;

	String journal = null;
	
	public void linkTravers(int menu) throws Exception 
	{
		new Actions(driver).moveToElement(tasksLink).build().perform();

		switch (menu) 
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
				
				homeLink.click();				
				break;				
			
	
			default:
				
				logger.info("Invalide sub-menu enter:[" + menu + "]");				
				break;
		}
	}
	
	boolean isDispayedDefaultFilter()
	{
		if (defaultFilter.isSelected() == true)
		{			
			editButton.click();
		}
		return defaultFilter.isSelected();
	}
	
	private void applicationReLogin(String userName, String passWord)
	{
		LogOutLink.click();
		
		userId.sendKeys(userName);
		
		Pwd.sendKeys(passWord);
		
		login.click();
	}
	
	
	public void myTasksArticlesManagement() throws Exception
	{	
		try
		{			
			logger.info("============================>Tasks>MyTasks>articles");			
			
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
			
			applicationReLogin(productionEditor,myTaskRolePassword);	
			
			linkTravers(3);		
			
			rows=DriverManager.gridHandler(commonDashBoardArticleTasksList,"load_userGrid", articleTasksPagination);
			
			if (rows > 0)
			{
				for (int i = 1; i <= rows; i++) 
				{					
					assignee=driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[20]/a")).getText().trim();							
					
					title=driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[15]/a")).getText().trim();	
					
					if (i == 1) 
					{						
						logger.info("Title Name	:"+title+"	Article Assigned To	:"+assignee);
						
						driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[15]/a")).click();						
						
						if(DriverManager.isElementPresent(By.linkText("Send Back To Queue")) == true)
						{
							DriverManager.scrollDown(DriverManager.getY(sendBackToQueue));
							
							sendBackToQueue.click();
						}
						else
						{
							DriverManager.scrollDown(DriverManager.getY(assignToMe));
							
							assignToMe.click();
							
							okButton.click();								
							
							DriverManager.scrollDown(DriverManager.getY(sendBackToQueue));
							
							sendBackToQueue.click();
						}	
										
						okButton.click();				
					}
				}
				
				linkTravers(3);		
				
				newRows=DriverManager.gridHandler(commonDashBoardArticleTasksList,"load_userGrid", articleTasksPagination);			
				
				Assert.assertEquals(newRows,rows);
				
				if (newRows == rows)
				{
					log(LogStatus.PASS,"No of Items in grid	:"+rows+"	No of Items in grid after send back to Queue  :"+newRows);
				}
			}
			else
			{
				log(LogStatus.PASS,"No of Items in grid	:"+rows+"	No of Items in grid after send back to Queue  :"+newRows);
			}			
		}
		catch(AssertionError e)
		{		
			assertionError(e); 
			
			screenShot(LogStatus.FAIL,"No of Items in grid	:"+rows+"	No of Items in grid after send back to Queue  :"+newRows);
		 }
		finally 
		{
			extent.flush();
		}
	}	
	
	
	
	public void myTasksIssuesManagement() throws Exception 
	{
		try 
		{			
			logger.info("============================>Tasks>MyTasks>issues");			
			
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());		
			
			applicationReLogin(productionEditor,myTaskRolePassword);	
			
			linkTravers(3);		
			
			rows=DriverManager.gridHandler(commonDashBoardIssueTasksList,"load_remoteIssueGridUrl", issueTasksPagination);
			
			if (rows > 0)
			{
				for (int i = 1; i <= rows; i++) 
				{				
					if (rows == i)
					{
						journal=driver.findElement(By.xpath("//table[@id='remoteIssueGridUrl']/tbody/tr[" + i + "]/td[8]/a")).getText().trim();
						
						assignee=driver.findElement(By.xpath("//table[@id='remoteIssueGridUrl']/tbody/tr[" + i + "]/td[14]/a")).getText().trim();	
						
						logger.info("Journal :"+journal+" Assigned To :"+assignee);	
						
						driver.findElement(By.xpath("//table[@id='remoteIssueGridUrl']/tbody/tr[" + i + "]/td[8]/a")).click();
						
						if(sendBackToQueue.isSelected())
						{
							DriverManager.scrollDown(DriverManager.getY(sendBackToQueue));
							
							sendBackToQueue.click();
						}
						else
						{
							DriverManager.scrollDown(DriverManager.getY(assignToMe));
							
							new Actions(driver).click(assignToMe).perform();						
							
							DriverManager.scrollDown(DriverManager.getY(sendBackToQueue));
							
							sendBackToQueue.click();
						}					
					}				
				}
				
				linkTravers(3);		
				
				newRows=DriverManager.gridHandler(commonDashBoardIssueTasksList,"load_remoteIssueGridUrl", issueTasksPagination);				
				
				Assert.assertEquals(newRows,rows);
				
				if (newRows == rows)
				{
					log(LogStatus.PASS,"No of Items in grid	:"+rows+"	No of Items in grid after send back to Queue  :"+newRows);
				}
			}
			else
			{
				log(LogStatus.PASS,"No of Items in grid	:"+rows+"	No of Items in grid after send back to Queue  :"+newRows);
			}
		}
		catch (AssertionError e) 
		{
			assertionError(e); 			
			
			screenShot(LogStatus.FAIL,"No of Items in grid	:"+rows+"	No of Items in grid after send back to Queue  :"+newRows);
		}
		finally 
		{
			extent.flush();
		}
	}
}
