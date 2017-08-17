package org.mps.degruyter.modules;

import org.apache.log4j.Logger;
import org.mps.degruyter.util.CommonUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
/**
 ****************************************************************************************
 * Created By : Siddhartha Mahakur
 * Created Date : May 01,2017
 * Last Modified By : Siddhartha Mahakur
 * Last Modified Date: 
 ****************************************************************************************
 *
 */
public class AuditTrail extends CommonUtils {

	final static Logger logger = Logger.getLogger(AuditTrail.class);

	public AuditTrail(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver driver;
	
	@FindBy(linkText = "Admin")
	public static WebElement AdminLink;	
	
	@FindBy(linkText = "Audit Trail")
	public static WebElement AuditTrailLink;	
	
	@FindBy(id = "auditTrailTypeId")
	public static WebElement AuditTrailTypeId;	
	
	@FindBy(id = "auditTrailSubEntityTypeId")
	public static WebElement AuditTrailSubEntityTypeId;	
	
	@FindBy(id = "journalIdId")
	public static WebElement journalIdId;	
	
	@FindBy(id = "articleIdId")
	public static WebElement articleIdId;	
	
	@FindBy(id = "auditTrailFromId")
	public static WebElement AuditTrailFrom;	
	
	@FindBy(id = "auditTrailToId")
	public static WebElement AuditTrailTo;		
	
	@FindBy(name = "Submit")
	public static WebElement Submit_Button;			
	
	Select dropdown_1=null;
	
	Select dropdown_2 =null;
	
	//List<String> AuditTrailType = new ArrayList<String>(Arrays.asList(AuditTrailType));
	
	//List<String> AuditTrailSubType = new ArrayList<String>(Arrays.asList(AuditTrailSubType().split(",")));	
	
	//List<String> nameOfArticlesList = new ArrayList<String>(Arrays.asList(nameOfArticlesList().split(",")));
	
	public void mouseHover_AdminMenu(int sub_menu)throws Exception
	{	
		new Actions(driver).moveToElement(AdminLink).perform();
		switch (sub_menu) 
		{
			case 1:
			AuditTrailLink.click();
			logger.info("Clicked on Audit Trail:" + sub_menu);
			break;	
			
			default:
			logger.info("Invalide sub-menu enter:" + sub_menu);
			break;
		}
	}	
	
	public void AuditTrailDetails() throws Exception
	{
		logger.info("------------------------------------------------------------------------");
		logger.info("Admin>Audit Trail");
		logger.info("------------------------------------------------------------------------");	
		mouseHover_AdminMenu(1);		
		dropdown_1 = new Select(AuditTrailTypeId);
		dropdown_2 = new Select(AuditTrailSubEntityTypeId);
		
		for(String aty:auditTrailType)			
		{	
			logger.info("AuditTrailType selected for:"+aty);
			for (String atsub : auditTrailSubType) 
			{
				switch (aty) 
				{
					case "Journal":		
					dropdown_1.selectByVisibleText(aty);				
					JournalAuditTrail(atsub);					
					break;
					
					case "Article":
					dropdown_1.selectByVisibleText(aty);					
					articleAuditTrail(atsub);					
					break;
					
					case "Issue":	
					dropdown_1.selectByVisibleText(aty);					
					issueAuditTrail(atsub);													
					break;
					
					default:
					logger.info("Invalid choice for audit trail");
					break;
				}	
			}							
		}
	}

	
	private void JournalAuditTrail(String atsub) 
	{	
		switch (atsub) 
		{
			case "Copyright Information":
			dropdown_2.selectByVisibleText(atsub);
			logger.info("Journal Audit Trail process for " + atsub);			
			JournalAuditTrailComplete(atsub);			
			break;
		
			case "Issue Makeup":
			dropdown_2.selectByVisibleText(atsub);
			logger.info("Journal Audit Trail process for " + atsub);
			JournalAuditTrailComplete(atsub);	
			break;
		
			case "Journal":
			dropdown_2.selectByVisibleText(atsub);
			logger.info("Journal Audit Trail process for " + atsub);
			JournalAuditTrailComplete(atsub);	
			break;
		
			case "Print Specification":
			dropdown_2.selectByVisibleText(atsub);
			logger.info("Journal Audit Trail process for " + atsub);
			JournalAuditTrailComplete(atsub);	
			break;
		
			case "Volume and Issue":
			dropdown_2.selectByVisibleText(atsub);
			logger.info("Journal Audit Trail process for " + atsub);
			JournalAuditTrailComplete(atsub);	
			break;
			
			default:					
					break;
		}		
	}
	
	private void articleAuditTrail(String atsub) 
	{	
		switch (atsub) 
		{
			case "Article":	
			dropdown_2.selectByVisibleText(atsub);
			logger.info("Article Audit Trail process for "+atsub);
			articleAuditTrailComplete(atsub);			
			break;
		
			case "Author":
			dropdown_2.selectByVisibleText(atsub);
			logger.info("Article Audit Trail process for "+atsub);
			articleAuditTrailComplete(atsub);	
			break;
			
			case "Charging":
			dropdown_2.selectByVisibleText(atsub);
			logger.info("Article Audit Trail process for "+atsub);
			articleAuditTrailComplete(atsub);	
			break;
		
			case "Content":		
			dropdown_2.selectByVisibleText(atsub);
			logger.info("Article Audit Trail process for "+atsub);
			articleAuditTrailComplete(atsub);	
			break;
											
			case "Copyright & Permissions":
			dropdown_2.selectByVisibleText(atsub);
			logger.info("Article Audit Trail process for "+atsub);
			articleAuditTrailComplete(atsub);	
			break;
				
			default:
				
				break;
		}			
	}
	

	private void issueAuditTrail(String atsub) 
	{		
		switch (atsub) 
		{
			case "Issue":
			dropdown_2.selectByVisibleText(atsub);
			logger.info("Issue Audit Trail process for " + atsub);
			break;
		
			case "Issue Makeup":
			dropdown_2.selectByVisibleText(atsub);
			logger.info("Issue Audit Trail process for " + atsub);
			break;
		
			case "Print Cover Specification":
			dropdown_2.selectByVisibleText(atsub);
			logger.info("Issue Audit Trail process for " + atsub);
			break;
		
			default:				
				break;
		}		
	}

	
	
	// JournalAuditTrailComplete() is function which will complete the journal Audit Trail Type*
	private void JournalAuditTrailComplete(String atsub) 
	{		
		for (String Article : nameOfArticlesList) 
		{
			journalIdId.clear();
			AuditTrailFrom.clear();
			AuditTrailTo.clear();
			
			journalIdId.sendKeys(Article);	
			logger.info("JournalAuditTrailComplete Article Name :"+Article+" Audit Trail Type*:"+atsub);
			AuditTrailFrom.sendKeys(auditTrailFromDate);
			AuditTrailTo.sendKeys(auditTrailToDate);
			logger.info(Submit_Button.isDisplayed());
			
		}	
	}
	
	private void articleAuditTrailComplete(String atsub) 
	{
		for (String Article : nameOfArticlesList) 
		{
			articleIdId.clear();
			AuditTrailFrom.clear();
			AuditTrailTo.clear();
			
			articleIdId.sendKeys(Article);	
			logger.info("ArticleAuditTrailComplete Article Name :"+Article+" Audit Trail Type*:"+atsub);
			AuditTrailFrom.sendKeys(auditTrailFromDate);
			AuditTrailTo.sendKeys(auditTrailToDate);
			
		}	
		
	}
}
