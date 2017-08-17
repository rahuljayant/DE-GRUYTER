package org.mps.degruyter.modules;

import java.util.List;
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
public class ManageElements extends CommonUtils 
{
	final static Logger logger = Logger.getLogger(ManageElements.class);

	public ManageElements(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(id = "addNewElement")
	public static WebElement add;

	@FindBy(id = "divGrid")
	public static WebElement grid;

	@FindBy(id = "gbox_journalIssueGridID")
	public static WebElement issueListGrid;

	@FindBy(id = "issueElementGrid")
	public static WebElement popUpGrid;

	@FindBy(linkText = "Admin")
	public static WebElement adminLink;

	@FindBy(linkText = "Manage Elements")
	public static WebElement manageElementsLink;

	@FindBy(css = ".ui-pg-selbox")
	public static WebElement pagination;

	@FindBy(linkText = "Issues")
	public static WebElement issues;

	@FindBy(id = "next")
	public static WebElement next;

	@FindBy(css = "#tab4>a")
	public static WebElement issueMakeup;

	@FindBy(name = "buttonclick")
	public static WebElement editButton;

	@FindBy(css = ".element1>img")
	public static WebElement elementOne;

	@FindBy(className = "element2")
	public static WebElement elementTwo;

	@FindBy(id = "fancybox-frame")
	public static WebElement fancyBoxFrame;

	@FindBy(id = "fancybox-close")
	public static WebElement fancyboxClose;

	@FindBy(id = "addElementId")
	public static WebElement addButton;

	@FindBy(css = ".ui-paging-info")
	public static WebElement uiPagingInfo;

	@FindBy(id = "elementTitle_id")
	public static WebElement elementDetailsTitle;

	@FindBy(id = "elementComments_id")
	public static WebElement elementDetailsNotes;

	@FindBy(id = "noOfPagesEleID")
	public static WebElement elementDetailsPages;

	@FindBy(css = ".btn.btn-inverse")
	public static WebElement saveUpdateButton;

	@FindBy(css = ".alert.alert-error")
	public static WebElement alertError;

	@FindBy(id = "cancel_id")
	public static WebElement cancelButton;

	@FindBy(id = "tableId0")
	public static WebElement prelimsEditBox;

	@FindBy(id = "tableId999")
	public static WebElement postlimsEditBox;

	@FindBy(xpath = "//input[@name='deleteselected']")
	public static WebElement deleteSelected;

	@FindBy(xpath = "//input[@name='savedata']")
	public static WebElement saveData;

	public WebDriver driver;

	boolean search_flag = false;

	boolean inactive_flag = false;

	WebElement Webtable = null;

	int rows = 0;

	private static int nxt_cnt = 0;

	int valueOfOne = 0;

	int valueOfTwo = 0;	

	int grid_items = 0;

	int fancyBox_items = 0;
	
	int issueGridValues = 0;

	String Prelims = null;

	String Postlims = null;

	String result = null;

	String alert_add = null;

	String alert_delete = null;	

	String alert_Postlims = null;
	
	String alert_ActivateDeactivate = null;
	
	String updateMsg = "Element updated successfully";	
		
	public void adminMenu(int sub_menu) throws Exception 
	{
		new Actions(driver).moveToElement(adminLink).perform();
		switch (sub_menu) 
		{
			case 1:
				
				manageElementsLink.click();	
				break;
	
			default:
				
				logger.info("Invalide sub-menu enter:" + sub_menu);	
				break;
		}
	}

	private static  int getPaginationNextCount()
	{
		DriverManager.verifyLinkTextPresent("view");
		
		String elementCount = uiPagingInfo.getText().substring(15);
		
		int num = Integer.parseInt(elementCount);
		
		nxt_cnt = num / 100;
		
		if (num % 100 != 0 && num >10) 
		{
			nxt_cnt = nxt_cnt + 1;
		}
		return nxt_cnt;
	 }
	
	private  int PrelimsEditBoxValue()
	{
		Webtable = prelimsEditBox;
		
		List<WebElement> TableUsers = Webtable.findElements(By.tagName("tr"));
		
		return TableUsers.size();
	}
	
	private  int PostlimsEditBoxValue()
	{
		Webtable = postlimsEditBox;
		
		List<WebElement> TableUsers = Webtable.findElements(By.tagName("tr"));
		
		return TableUsers.size();
	}
	
	public void addManageElements() throws Exception 
	{	
		try 
		{			
			logger.info("============================>Admin>ManageElements>Add Elements");		
			
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());	
			
			adminMenu(1);
			
			grid_items=DriverManager.gridHandler(grid, "load_elementGrid", pagination);			
			
			add.click();
			
			DriverManager.switchToFrame(fancyBoxFrame);	
			
			elementDetailsTitle.sendKeys(title);
			
			elementDetailsNotes.sendKeys(notes);
			
			elementDetailsPages.sendKeys(Integer.toString(page));	
			
			saveUpdateButton.click();
			
			alert_add=DriverManager.isAlertPresent();			
			
			logger.info("Add New Element :"+ alert_add);			
			
//			if (DriverManager.isElementPresent(By.cssSelector(".alert.alert-error")) == true)
//			{					
//				logger.info("Element is already exist");	
//				
//				Assert.assertEquals("Duplicate Element not allowed", alertError.getText().trim());	
//				
//				cancelButton.click();										
//			}
			
			log(LogStatus.PASS, "Title  :"+title+" Notes  :"+notes+" Pages  ::"+page);
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
	
	
	public void editManageElements() throws Exception 
	{
		try 
		{
			logger.info("============================>Admin>ManageElements>Edit Elements");

			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());

			adminMenu(1);

			rows = DriverManager.gridHandler(grid, "load_elementGrid", pagination);

			for (int i = 1; i <= rows; i++) 
			{
				result = driver.findElement(By.xpath("//table[@id='elementGrid']/tbody/tr[" + i + "]/td[4]")).getText().trim();
				
				if (title.equals(result)) 
				{
					logger.info("Edit Element[" + i + "] :" + result);

					String isDeactivate = driver.findElement(By.xpath("//table[@id='elementGrid']/tbody/tr[" + i + "]/td[3]/a/u")).getText().trim();

					if (isDeactivate.equals("Deactivate")) 
					{
						driver.findElement(By.xpath("//table[@id='elementGrid']/tbody/tr[" + i + "]/td[1]/a/img")).click();

						DriverManager.switchToFrame(fancyBoxFrame);

						elementDetailsTitle.clear();

						elementDetailsNotes.clear();

						elementDetailsPages.clear();

						elementDetailsTitle.sendKeys(title);

						elementDetailsNotes.sendKeys(notes);

						elementDetailsPages.sendKeys(Integer.toString(page));

						saveUpdateButton.click();

						Boolean isPresent = DriverManager.verifyLinkTextPresent(updateMsg);

						if (isPresent)
						{
							Assert.assertEquals(updateMsg, updateMsg);

							log(LogStatus.PASS,"Edited Values are Title :" + title + " Note :" + notes + " page :" + page);

							logger.info("Edited Values are Title :" + title + " Note :" + notes + " page :" + page);
						}
					} 
					else if (isDeactivate.equals("Activate")) 
					{						
						driver.findElement(By.xpath("//table[@id='elementGrid']/tbody/tr[" + i + "]/td[1]/a/img")).click();

						alert_ActivateDeactivate = DriverManager.isAlertPresent();

						logger.info(alert_ActivateDeactivate);

						if (alert_ActivateDeactivate.contains("This action cannot be perfomed on Inactive Elements.")) 
						{
							inactive_flag = true;

							Assert.assertEquals("This action cannot be perfomed on Inactive Elements.",alert_ActivateDeactivate);

							log(LogStatus.PASS, "This action cannot be perfomed on Inactive Elements.");

							logger.info("Pass :: This action cannot be perfomed on Inactive Elements.");
						}
					}
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
	
	
	public void deleteManageElements() throws Exception 
	{	
		try 
		{				
			logger.info("============================>Admin>ManageElements>Delete Elements");			
			
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());	
			
			adminMenu(1);
			
			rows=DriverManager.gridHandler(grid, "load_elementGrid", pagination);			
			
			for (int i = 1; i <= rows; i++)
			{		
				result = driver.findElement(By.xpath("//table[@id='elementGrid']/tbody/tr[" + i + "]/td[4]")).getText().trim();	
				
				if (title.equals(result))
				{						
					logger.info("Delete Element Position=[" + i + "] :" + result);					
					
					driver.findElement(By.xpath("//table[@id='elementGrid']/tbody/tr[" + i + "]/td[2]/a/img")).click();	
					
					alert_delete=DriverManager.isAlertPresent();
					
					logger.info(alert_delete);
					
					log(LogStatus.PASS,"Delete Element Position=[" + i + "]: " + result + " : " + alert_delete);				
					
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
	
	
	public void activeDeactiveManageElements() throws Exception 
	{	
		try 
		{			
			logger.info("============================>Admin>ManageElements>Active/Deactive Elements");			
			
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());	
			
			adminMenu(1);
			
			rows=DriverManager.gridHandler(grid, "load_elementGrid", pagination);			
			
			for (int i = 1; i <= rows; i++)
			{		
				result = driver.findElement(By.xpath("//table[@id='elementGrid']/tbody/tr[" + i + "]/td[4]")).getText().trim();	
				
				if (title.equals(result))
				{					
					logger.info("Deactivate Element Position=[" + i + "]  :" + result);					
					
					driver.findElement(By.xpath("//table[@id='elementGrid']/tbody/tr[" + i + "]/td[3]/a/u")).click(); 
					
					alert_ActivateDeactivate=DriverManager.isAlertPresent();
					
					logger.info(alert_ActivateDeactivate);
					
					log(LogStatus.PASS, "Activate/Deactivate Element Position=[" + i + "]:" + result+ alert_ActivateDeactivate);				
					
					alert_ActivateDeactivate=DriverManager.isAlertPresent();
					
					logger.info(alert_ActivateDeactivate);
					
					log(LogStatus.PASS, "Activate/Deactivate Element Position=[" + i + "]:" + result + " : "+ alert_ActivateDeactivate);			
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
	
	
	public void journalIssueMakeUpManageElements(String Journal,int Volume) throws Exception 
	{
		try 
		{	
			
			logger.info("============================>Admin>ManageElements>Check Elements Count");			
			
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());	
			
			DriverManager.verifyLinkTextPresent("Issues");
			
			issues.click();
			
			DriverManager.scrollDown(250);
			
			DriverManager.waitForWebElement(By.id("load_journalIssueGridID"), "style", "display: block;");
			
			int NextCount=getPaginationNextCount();
			
			logger.info("Total No of Next to be hit:"+NextCount);
			
			for (int nextButton = 1; nextButton <= NextCount; nextButton++)
			{
				new Select(pagination).selectByVisibleText("100");
				
				DriverManager.waitForWebElement(By.id("load_journalIssueGridID"), "style", "display: block;");				
				
				rows=DriverManager.gridHandler(issueListGrid, "load_journalIssueGridID", pagination);
				
				logger.info("Total no of rows ["+rows+"]in next button["+nextButton+"] of Issue List Grid");	
				
				for (int i = 1; i <= rows; i++)
				{
					String journalName = driver.findElement(By.xpath("//table[@id='journalIssueGridID']/tbody/tr[" + i + "]/td[5]")).getText().trim();					
					
					int isVolume=Integer.parseInt(driver.findElement(By.xpath("//table[@id='journalIssueGridID']/tbody/tr[" + i + "]/td[7]/a")).getText().trim());									
					
					if (Journal.equals(journalName) && Volume==isVolume) 
					{	
						log(LogStatus.INFO,"Required Journal found at postion=[" + i + "] :" + journalName +"& Volume at Position=[" + i + "] :" + isVolume);
						
						logger.info("Required Journal found at postion=[" + i + "] :" + journalName +"& Volume at Position=[" + i + "] :" + isVolume);
						
						logger.info("is journal active ? :"+driver.findElement(By.xpath("//table[@id='journalIssueGridID']/tbody/tr[" + i + "]/td[12]")).getText().equals("Active"));
						
						log(LogStatus.INFO,"is journal active ? :"+driver.findElement(By.xpath("//table[@id='journalIssueGridID']/tbody/tr[" + i + "]/td[12]")).getText().equals("Active"));
						
						driver.findElement(By.xpath("//table[@id='journalIssueGridID']/tbody/tr[" + i + "]/td[5]/a")).click();	
						
						search_flag=true;
						
						journalIssueMakeUpComplete(element);	
						
						return;
					}				
				}
				next.click();	
				
				DriverManager.waitForWebElement(By.id("load_journalIssueGridID"), "style", "display: block;");			
			}
			if (!search_flag) 
			{
				logger.info("Element not found in grid so we won't proceed further for journal Issue MakeUp");
				
				log(LogStatus.PASS,"Element not found in grid so we won't proceed further for journal Issue MakeUp");
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
	
	
	private void journalIssueMakeUpComplete( int num) throws Exception 
	{
		varifyElementsCount();
		
		addElementsToPrelims( num);
		
		addElementsToPostlims( num);
		
		deleteElementsPrelimsPostlims();
	}	
	
	
	private void varifyElementsCount() throws Exception 
	{	
		
		try 
		{	
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());	
			
			if (!inactive_flag) 
			{
				grid_items = grid_items - 1;
			}
			
			issueMakeup.click();
			
			editButton.click();	
			
			DriverManager.scrollDown(DriverManager.getY(elementOne));
			
			elementOne.click();		
			
			DriverManager.switchToFrame(fancyBoxFrame);	
			
			fancyBox_items=DriverManager.getFancyBoxElements(popUpGrid);			
			
			if (grid_items == fancyBox_items)
			{			
				DriverManager.fancyBoxClose("fancybox-close");	
				
				logger.info("No of elements in Grid :"+grid_items+" and No of elements in Prelims Grid :"+fancyBox_items);
				
				logger.info("Button one task has been completed");
				
				log(LogStatus.PASS, "No of elements in Grid :"+grid_items+" and No of elements in Prelims Grid :"+fancyBox_items);		
			}	
			else
			{
				log(LogStatus.FAIL, "No of elements in Grid :"+grid_items+" and No of elements in Prelims Grid :"+fancyBox_items);
			}			
			DriverManager.verifyLinkTextPresent("Journal Issue Details");	
			
			DriverManager.scrollDown(DriverManager.getY(elementTwo));
			
			DriverManager.verifyLinkTextPresent("Journal Issue Details");	
			
			elementTwo.click();		
			
			DriverManager.switchToFrame(fancyBoxFrame);	
			
			fancyBox_items= DriverManager.getFancyBoxElements(popUpGrid);	
			
			if (grid_items == fancyBox_items) 
			{
				DriverManager.fancyBoxClose("fancybox-close");
				
				logger.info("No of elements in Grid :"+grid_items+" and No of elements in Postlims Grid :"+fancyBox_items);
				
				logger.info("Button Two task has been completed");	
				
				log(LogStatus.PASS, "No of elements in Grid :"+grid_items+" and No of elements in Postlims Grid :"+fancyBox_items);				
			}	
			else
			{
				log(LogStatus.FAIL, "No of elements in Grid :"+grid_items+" and No of elements in Postlims Grid :"+fancyBox_items);	
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
	
	
	private void addElementsToPrelims( int num) throws Exception 
	{
		try
		{	
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());	
			
			DriverManager.verifyLinkTextPresent("Journal Issue Details");	
			
			DriverManager.scrollDown(DriverManager.getY(elementOne));
			
			DriverManager.verifyLinkTextPresent("Journal Issue Details");	
			
			elementOne.click();		
			
			DriverManager.switchToFrame(fancyBoxFrame);
			
			if (fancyBox_items > num && num != 0)
			{
				DriverManager.waitForWebElement(By.id("load_issueElementGrid"), "style", "display: block;");
				
				Prelims=driver.findElement(By.xpath("//table[@id='issueElementGrid']/tbody/tr[" + num + "]/td[3]")).getText().trim();
				
				logger.info(Prelims);
				
				driver.findElement(By.xpath("//table[@id='issueElementGrid']/tbody/tr[" + num + "]/td[1]/input")).click();		
				
				addButton.click();
				
				log(LogStatus.INFO, "Add Element to Prelims :"+Prelims);
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
	
	
	private void addElementsToPostlims( int num) throws Exception 
	{	
		try 
		{	
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());	
			
			DriverManager.verifyLinkTextPresent("Journal Issue Details");		
			
			DriverManager.scrollDown(DriverManager.getY(elementTwo));	
			
			elementTwo.click();		
			
			DriverManager.switchToFrame(fancyBoxFrame);
			
			if (fancyBox_items > num && num != 0)
			{
				DriverManager.waitForWebElement(By.id("load_issueElementGrid"), "style", "display: block;");
				
				Postlims=driver.findElement(By.xpath("//table[@id='issueElementGrid']/tbody/tr[" + num + "]/td[3]")).getText().trim();
				
				logger.info(Postlims);
				
				driver.findElement(By.xpath("//table[@id='issueElementGrid']/tbody/tr[" + num + "]/td[1]/input")).click();	
				
				addButton.click();
				
				log(LogStatus.INFO, "Add Element to Postlims :"+Postlims);
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
	
	
	private void deleteElementsPrelimsPostlims() throws Exception 
	{		
		try 
		{	
			test = ExtentManager.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());	
			
			valueOfOne=PrelimsEditBoxValue();	
			
			valueOfTwo=PostlimsEditBoxValue();
			
			logger.info("PrelimsEditBoxValue	:" + valueOfOne + " PostlimsEditBoxValue :" + valueOfTwo);
	
			for (int preItem = 2; preItem <= valueOfOne; preItem++)
			{
				driver.findElement(By.xpath("//table[@id='tableId0']/tbody/tr[" + preItem + "]/td[1]/input")).click();
				
				logger.info("Item selected for Prelims [" + preItem + "]");
			}		
			for (int postItem = 2; postItem <= valueOfTwo; postItem++)
			{
				driver.findElement(By.xpath("//table[@id='tableId999']/tbody/tr[" + postItem + "]/td[1]/input")).click();
				
				logger.info("Item selected for Postlims [" + postItem + "]");
			}
			
			DriverManager.verifyLinkTextPresent("Journal Issue Details");	
			
			DriverManager.scrollDown(DriverManager.getY(deleteSelected));
			
			deleteSelected.click();
			
			alert_Postlims=DriverManager.isAlertPresent();		
			
			log(LogStatus.INFO, alert_Postlims);
			
			DriverManager.verifyLinkTextPresent("Journal Issue Details");
			
			DriverManager.scrollDown(DriverManager.getY(saveData));
				
			saveData.click();
			
			DriverManager.isAlertPresent();			
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
}
