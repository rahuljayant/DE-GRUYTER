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
import org.openqa.selenium.support.ui.Select;
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
public class ManageUsers extends CommonUtils
{
	final static Logger logger = Logger.getLogger(ManageUsers.class);	
	
	public ManageUsers(WebDriver driver) 
	{
        this.driver = driver;
    }

	public WebDriver driver;

	@FindBy(id = "divGrid")
	public static WebElement Grid;

	@FindBy(id = "userId")
	public static WebElement UserId;

	@FindBy(id = "pwd")
	public static WebElement Pwd;

	@FindBy(css = ".btn-middle.btn.btn-inverse.pull-right")
	public static WebElement Login;

	@FindBy(linkText = "Admin")
	public static WebElement AdminLink;

	@FindBy(css = ".ui-pg-selbox")
	public static WebElement Pagination;

	@FindBy(linkText = "Manage Users")
	public static WebElement ManageUserLink;

	@FindBy(linkText = "Logout")
	public static WebElement LogOutLink;

	@FindBy(linkText = "+Add")
	public static WebElement AddButton;

	@FindBy(id = "firstName")
	public static WebElement firstName;

	@FindBy(id = "lastName")
	public static WebElement lastName;

	@FindBy(id = "designation")
	public static WebElement Designation;

	@FindBy(id = "password")
	public static WebElement password;

	@FindBy(id = "countyId")
	public static WebElement County;

	@FindBy(id = "email")
	public static WebElement email;

	@FindBy(id = "userGroup")
	public static WebElement UserGroup;

	@FindBy(id = "save_id")
	public static WebElement SaveButton;

	@FindBy(id = "copy_id")
	public static WebElement CopyUserButton;

	@FindBy(id = "fancybox-frame")
	public static WebElement Fancybox;

	@FindBy(id = "Username")
	public static WebElement UserList;

	@FindBy(id = "go")
	public static WebElement GoButton;

	@FindBy(css = ".alert-error.error-login>center")
	public static WebElement InvalidUsernamePassword;

	@FindBy(css = ".ui-paging-info")
	public static WebElement UiPagingInfo;
	
	public static String saveUserAlertMessage = null;

	public static String editUserAlertMsg = null;

	public static String deleteUserAlertMsg = null;

	public static String activeDeactiveUserAlertMsg = null;

	public static String result = null;
	
	public void adminMenu(int sub_menu)throws Exception
	{	
		new Actions(driver).moveToElement(AdminLink).perform();
		
		switch (sub_menu) 
		{
			case 1:	
				
					ManageUserLink.click();										
					break;
			
			default:
				
					logger.info("Invalide sub-menu enter:" + sub_menu);				
					break;
		}
	}	
	
	public void addManageUsers() throws Exception 
	{		
		try
		{			
			logger.info("------------------------------------------------------------------------");
			
			logger.info("Admin>ManageUsers>Add User Details");
			
			logger.info("------------------------------------------------------------------------");	
			
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());	
			
			adminMenu(1);			
			
			new Actions(driver).click(AddButton).perform();
			
			UserId.clear();	
			
			password.clear();
			
			UserId.sendKeys(userId);
			
			DriverManager.randomlyselectId("designation");			
			
			firstName.sendKeys(firstname);
			
			lastName.sendKeys(lastname);			
			
			password.sendKeys(defaultPassword);
			
			DriverManager.randomlyselectId("countyId");			
			
			email.sendKeys(emailAddress);			
			
			new Select(UserGroup).selectByVisibleText(userGroup);
			
			SaveButton.click();
			
			saveUserAlertMessage = DriverManager.isAlertPresent();					
			
			log(LogStatus.PASS, "First Name  :"+firstname+" Last Name  :"+lastname+" Default Password  ::"+defaultPassword+" Email  :"+emailAddress+" : "+saveUserAlertMessage);			
			
			logger.info(saveUserAlertMessage);		
		
		}
		catch (AssertionError e) 
		{
			assertionError(e);
			
			screenShot(LogStatus.FAIL, "");
		}
		finally 
		{
			extent.flush();
		}
	}	
	
	public void editManageUsers() throws Exception 
	{	
		try 
		{		
			logger.info("------------------------------------------------------------------------");
			
			logger.info("Admin>ManageUsers>Edit User Details");
			
			logger.info("------------------------------------------------------------------------");	
			
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());	
			
			adminMenu(1);			
			
			new Actions(driver).click(AddButton).perform();
			
			String editStr = userId + " is " + userGroup;
			
			logger.info("Edit for User:" + editStr);
			
			CopyUserButton.click();
			
			DriverManager.switchToFrame(Fancybox);					
			
			new Select(UserList).selectByVisibleText(editStr);
			
			GoButton.click();
			
			firstName.clear();
			
			lastName.clear();
			
			UserId.clear();
			
			password.clear();
			
			email.clear();				
			
			firstName.sendKeys(firstname + "test");	
			
			lastName.sendKeys(lastname + "test");	
			
			UserId.sendKeys(userId + "test");	
			
			password.sendKeys(defaultPassword);
			
			email.sendKeys("test" + emailAddress);			
			
			SaveButton.click();
			
			editUserAlertMsg = DriverManager.isAlertPresent();	
			
			log(LogStatus.PASS, "First Name  :"+firstname+" Last Name  :"+lastname+" Default Password  ::"+defaultPassword+" Email  :"+emailAddress+" : "+editUserAlertMsg);	
			
			if (editUserAlertMsg.contains("Duplicate User ID not allowed.")) 
			{		
				Assert.assertEquals("Duplicate User ID not allowed.", editUserAlertMsg);
				
				logger.info("Pass :: Duplicate User ID not allowed.");						
			} 
			else if (editUserAlertMsg.contains("User Id can't be same as previous"))
			{				
				logger.info("Pass :: User Id can't be same as previous");				
			}				
		} 
		catch (AssertionError e)
		{
			assertionError(e);
			
			screenShot(LogStatus.FAIL, "");
		}		
		finally 
		{
			extent.flush();
		}
	}	
	
	public void deleteManageUsers(String selecteditem) throws Exception
	{	
		try 
		{		
			logger.info("------------------------------------------------------------------------");
			
			logger.info("Admin>ManageUsers>Delete User Details");
			
			logger.info("------------------------------------------------------------------------");		
			
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());	
			
			adminMenu(1);			
			
			int rows = DriverManager.gridHandler(Grid, "load_userGrid", Pagination);
			
			logger.info("Total no of rows in users grid " + rows);
			
			for (int i = 1; i <= rows; i++) 
			{			
				result = driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[5]")).getText().trim();
				
				logger.info("Search User ID [" + i + "] :" + result);
	
				if (selecteditem.equalsIgnoreCase(result)) 
				{				
					driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[2]/a/img")).click();
					
					deleteUserAlertMsg = DriverManager.isAlertPresent();
					
					if (deleteUserAlertMsg.equalsIgnoreCase("User is assigned to a Article task.So User can not be deleted")) 
					{				
						Assert.assertEquals("User is assigned to a Article task.So User can not be deleted", deleteUserAlertMsg);
						
						logger.info("Pass :: for User is assigned to a Article task.So User can not be deleted "+ selecteditem);						
					} 
					else if (deleteUserAlertMsg.equalsIgnoreCase("You cannot delete Super User.")) 
					{					
						Assert.assertEquals("You cannot delete Super User.", deleteUserAlertMsg);
						
						logger.info("Pass :: You cannot delete Super User." + selecteditem);						
					} 
					else if (deleteUserAlertMsg.contains("Do you want to delete User")) 
					{					
						Assert.assertEquals("Do you want to delete User " + firstname + " " + lastname + "?", deleteUserAlertMsg);
						
						deleteUserAlertMsg = DriverManager.isAlertPresent();
						
						if (deleteUserAlertMsg.contains("User deleted successfully.")) 
						{
							Assert.assertEquals("User deleted successfully.", deleteUserAlertMsg);
							
							logger.info("Pass :: User deleted successfully	:" + selecteditem);
						}
					}					
					log(LogStatus.PASS, result + " : " + deleteUserAlertMsg);
					
					break;
				}
			}		
		} 
		catch (AssertionError e) 
		{
			assertionError(e);
			
			screenShot(LogStatus.FAIL, "");	
		}
		finally 
		{
			extent.flush();
		}
	}
	
	
	
	public void activeDeactiveManageUsers(String selecteditem) throws Exception 
	{	
		try 
		{		
			logger.info("------------------------------------------------------------------------");
			
			logger.info("Admin>ManageUsers>Active/Deactivate User Details");
			
			logger.info("------------------------------------------------------------------------");
			
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());	
			
			adminMenu(1);			
			
			int rows =DriverManager.gridHandler(Grid, "load_userGrid", Pagination);
			
			logger.info("Total no of rows in users grid " + rows);
			
			for (int i = 1; i <= rows; i++) 
			{
				result = driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[5]")).getText().trim();
				
				logger.info("Search User ID [" + i + "] :" + result);
	
				if (selecteditem.equalsIgnoreCase(result))
				{				
					WebElement checkforActiveDeactiveUser=driver.findElement(By.xpath("//table[@id='userGrid']/tbody/tr[" + i + "]/td[4]/a/u"));
					
					if (checkforActiveDeactiveUser.getText().trim().contentEquals("Activate")) 
					{					
						checkforActiveDeactiveUser.click();	
						
						activeDeactiveUserAlertMsg = DriverManager.isAlertPresent();
						
						if (activeDeactiveUserAlertMsg.contains("User state updated successfully"))
						{						
							Assert.assertEquals("User state updated successfully",activeDeactiveUserAlertMsg);
							
							logger.info("Pass :: User successfully Activated  :" + selecteditem);						
						}
						
						//System Re-Login & login with Activated User						
						
						applicationReLogin(userId + "test",defaultPassword);
						
						String title=driver.getTitle().toString();					
											
						Assert.assertEquals(":: De Gruyter :: Production Tracking System ::", title);
						
						logger.info("Pass: Successfully logged in with Acivated User :" + userId + "test");							
						
						applicationReLogin(sysUsername,sysPassword);	
						
						log(LogStatus.PASS, result + " : " + activeDeactiveUserAlertMsg);
						
					}
					else 
					{					
						checkforActiveDeactiveUser.click();		
						
						activeDeactiveUserAlertMsg = DriverManager.isAlertPresent();
						
						if (activeDeactiveUserAlertMsg != null && activeDeactiveUserAlertMsg.equalsIgnoreCase("If User will be Inactivate ,all the task and journal level role will be unassigned (if any)")) 
						{			
							Assert.assertEquals("If User will be Inactivate ,all the task and journal level role will be unassigned (if any)",activeDeactiveUserAlertMsg);
							
							activeDeactiveUserAlertMsg = DriverManager.isAlertPresent();
							
							if (activeDeactiveUserAlertMsg.contains("User state updated successfully")) 
							{					
								Assert.assertEquals("User state updated successfully",activeDeactiveUserAlertMsg);
								
								logger.info("Pass :: User successfully Deactivated  :" + selecteditem);								
							}
						}
						
						//System Re-Login & login with De-activated User
						
						applicationReLogin(userId + "test",defaultPassword);						
						
						if (InvalidUsernamePassword.getText().trim().contentEquals("Invalid Username/Password. Please try again.")) 
						{
							logger.info("Pass :: Inactive User the should not be able to login :" + userId + "test");							
							
							UserId.sendKeys(sysUsername);
							
							Pwd.sendKeys(sysPassword);	
							
							Login.click();	
							
							log(LogStatus.PASS, result + " : " + activeDeactiveUserAlertMsg);
							
						} 
						else 
						{						
							logger.info("Fail :: Inactive User the should be able to login :" + userId + "test");
							
							log(LogStatus.FAIL, "Inactive User the should not be able to login ");
						}
					}				
					break;
				 }
			  }
		} 
		catch (AssertionError e) 
		{
			assertionError(e);
			
			screenShot(LogStatus.FAIL, "");			
		}
		finally 
		{
			extent.flush();
		}
	}

	private void applicationReLogin(String userName,String passWord) 
	{
		LogOutLink.click();
		
		UserId.sendKeys(userName);
		
		Pwd.sendKeys(passWord);
		
		Login.click();			
	}	
}
