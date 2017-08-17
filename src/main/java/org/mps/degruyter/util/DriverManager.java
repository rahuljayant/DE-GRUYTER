package org.mps.degruyter.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.mps.degruyter.constant.GlobalConstatnts;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.Select;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
/**
 ****************************************************************************************
 * Created By : Siddhartha Mahakur
 * Created Date : April 27,2017
 * Last Modified By : Siddhartha Mahakur
 * Last Modified Date: 
 ****************************************************************************************
 *
 */

public class DriverManager extends CommonUtils
{
	final static Logger logger = Logger.getLogger(DriverManager.class);
	public static WebDriver driver = null;
	public static WebElement Webtable = null;
	public static int GridValues = 0;
	public static int fancy_box_items = 0;
	public static boolean reply;	
	public static String browser;
	
	public static WebDriver Browser(String br)
	{
		switch (br) 
		{
			case "chrome":
						
						Map<String, Object> prefs = new HashMap<String, Object>();						
						System.setProperty("webdriver.chrome.driver", GlobalConstatnts.CHROME_DRIVER);						
						prefs.put("profile.default_content_setting_values.notifications", 2);							
						ChromeOptions options = new ChromeOptions();							
						options.setExperimentalOption("prefs", prefs);						
						driver = new ChromeDriver(options);						
						break;
						
			case "ie":
						System.setProperty("webdriver.ie.driver", GlobalConstatnts.IE_DRIVER);						
						DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
						browser = caps.getBrowserName();
						caps.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
						caps.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
						caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
						caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
						caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
						caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
						driver = new InternetExplorerDriver(caps);						
						break;
						
						
			case "firefox":
				
						driver = new FirefoxDriver();						
						break;
						
			case "safari":
						SafariOptions opt = new SafariOptions();
						opt.setUseCleanSession(true); //if you wish safari to forget session everytime
						driver = new SafariDriver(opt);
						break;		
				
			default:
					
					logger.info("No browser type initialised");				
					break;
		}		
		return driver;
	}

	public static WebDriver UrlFind(WebDriver driver, String url)
	{
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
		
		driver.get(url);
		
		driver.manage().deleteAllCookies();
		
		return driver;
	}

	public static WebDriver invokeApp(String BrowserName, String url) 
	{		
		WebDriver driver = Browser(BrowserName);	
		
		UrlFind(driver, url);	
		
		return driver;
	}
	
	public static void  driverClose() throws Exception
	{		
		driver.close();
	}	
	
	public static void waitForWebElement(By by, String attribute, String expected) throws InterruptedException 
	{
		 while (driver.findElement(by).getAttribute(attribute).equals(expected)) {  Thread.sleep(1000);  }
	}	
	
	public static void scrollDown(int ypixels)
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		
		jse.executeScript("window.scrollBy(0,"+ypixels+")", "");
	}
	
	public static void scrollUp(int ypixels)
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		
		jse.executeScript("window.scrollBy(0,-"+ypixels+")", "");
	}
	
	public static String getScreenShot(String filepath) throws IOException
    {
        TakesScreenshot ts = (TakesScreenshot)driver;
        
        File source = ts.getScreenshotAs(OutputType.FILE);     
        
        String dest=System.getProperty("user.dir")+fileSeparator+filepath+fileSeparator+alphaNumericRadomCharacter+".png";      
        
        File destination = new File(dest);          
        
        FileUtils.copyFile(source, destination);   
        
        return imgRelativePath(dest, 3);
    }
	
	public static String getFullPageScreenShot(String filename,String filepath) throws Exception
    {
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);        
                
        String dest = System.getProperty("user.dir")+fileSeparator +filepath+fileSeparator+filename+".png";
        
        ImageIO.write(screenshot.getImage(),"PNG",new File(dest));       
       
        return dest;
    }
	public static void datePicker(String today,String calenderID)
	{	
		driver.findElement(By.id(calenderID)).click();
		
		WebElement dateWidget = driver.findElement(By.id("ui-datepicker-div"));
		
        List<WebElement> columns=dateWidget.findElements(By.tagName("td"));
        
        for (WebElement cell : columns)
        {
           if (cell.getText().equals(today.replaceFirst("^0+(?!$)", "")))
           {
              cell.click();
              
              break;
           }
        }		 
	 }	
	
	public static String randomlyselectId(String id) throws InterruptedException
	{		
		List<WebElement> optioncoll=driver.findElements(By.xpath("//select[@id='"+id+"']//option"));
		
		int size=optioncoll.size();	
		
		int val=new Random().nextInt(size);	
		
		if (val == 0) 
		{
			val = val + 1;
		}
		new Select(driver.findElement(By.id(id))).selectByIndex(val);	
		
		logger.info(optioncoll.get(val).getText()+"  : text has been selected from drop down option");
	
		return optioncoll.get(val).getText();
    }
	
	public static String randomlyselectName(String name) throws InterruptedException
	{		
		List<WebElement> optioncoll=driver.findElements(By.xpath("//select[@id='"+name+"']//option"));
		
		int size=optioncoll.size();
		
		int val=new Random().nextInt(size);
		
		new Select(driver.findElement(By.name(name))).selectByIndex(val);
		
		logger.info(optioncoll.get(val).getText()+"  : text has been selected from drop down option");
		
		return optioncoll.get(val).getText();
    }	
	
	
	public static int gridHandler(WebElement tableName,String loader_id,WebElement Pagination)
	{
        try 
        {
        	verifyLinkTextPresent("view");        		 
		
        	waitForWebElement(By.id(loader_id), "style", "display: block;");
        	
			new Select(Pagination).selectByVisibleText("100");
			
			waitForWebElement(By.id(loader_id), "style", "display: block;");
		
			Webtable = tableName;
		
			List<WebElement> TableUsers = Webtable.findElements(By.tagName("tr"));
		
			int totalrowcount = TableUsers.size();
		
			GridValues = totalrowcount - 4;	
		
			return GridValues;
		
        }
        catch (InterruptedException e)
        {       	
			e.printStackTrace();
			
			return 0;
		}		
	}
	
	public static int getFancyBoxElements(WebElement tableName) 
	{
		
		try 
		{		
			Webtable = tableName;		
		
			List<WebElement> TableUsers = Webtable.findElements(By.tagName("tr"));
		
			fancy_box_items = TableUsers.size();			
			
			return fancy_box_items;
		
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
			return 0;
		}
	}
	
	public static boolean verifyLinkTextPresent(String Str) 
	{
		try 
		{
			for (int millis = 1000; millis <= 900000;) 
			{
				if (driver.getPageSource().contains(Str) == false) 
				{
					Thread.sleep(millis);
					
					millis = millis + 1000;
					
					driver.navigate().refresh();
				}
				else if (driver.getPageSource().contains(Str)) 
				{
					reply = true;
					
					break;
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return reply;
	}
	
	public static void switchToFrame(WebElement we) 
	{
		try 
		{
			driver.switchTo().frame(we);		
		} 
		catch (NoSuchFrameException e) 
		{
			logger.info("Unable to locate frame " + e.getStackTrace());
		} 
		catch (Exception e)
		{
			logger.info("Unable to navigate to frame " + e.getStackTrace());
		}
	}

	public static String isAlertPresent()
	{
		String alertTxt = null;
		try
		{
			Alert alert = driver.switchTo().alert();
			
			alertTxt = alert.getText();
			
			alert.accept();
			
			return alertTxt;
		} 
		catch (Exception e) 
		{
			return null;
		}
	}
	
	public static void fancyBoxClose(String id)
	{	
		driver.switchTo().defaultContent();
		
		try 
		{	
			scrollDown(250);
			
			driver.findElement(By.id(id)).click();	
			
			scrollUp(250);
		} 
		catch (Exception e) 
		{
			 e.getMessage();
		}		
	}	
	
	public static int getY(WebElement element )
	{
		Point location = element.getLocation();
		
		int y = location.getY();
		
		return y;		
	}
	public static boolean isElementPresent(By by) 
	{
	    try 
	    {
	        driver.findElement(by);
	        
	        logger.info("Element Present");
	        
	        return true;
	    } 
	    catch (NoSuchElementException e) 
	    {
	    	 logger.info("Element absent");
	    	 
	    	 return false;
	    }
	}
}
