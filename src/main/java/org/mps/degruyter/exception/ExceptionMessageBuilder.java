package org.mps.degruyter.exception;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import org.mps.degruyter.util.CommonUtils;

public class ExceptionMessageBuilder extends CommonUtils
{
	final static Logger logger = Logger.getLogger(ExceptionMessageBuilder.class); 

	public static String strClassName=null;

	public static String strMethodName=null;

	public static int strLineNumber=0;
	
	public final static String getErrorMessage(Exception e)
	{
		String strErrorDescription = "";
		try
		{
			StringWriter sw = new StringWriter(512);
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			strErrorDescription = sw.toString();
			StringTokenizer objST = new StringTokenizer(strErrorDescription, CommonUtils.lineSeparator());			
			String strTemp = objST.nextToken();
			
			int iIndex2 = -1;
			
			while (objST.hasMoreTokens() && (iIndex2 == -1))
			{
				strTemp = objST.nextToken();
				iIndex2 = strTemp.indexOf(':');
			}
			
			objST = null;
		}
		catch (Exception e2)
		{
			strErrorDescription = e2.getMessage();
		}		
		return strErrorDescription;
	}
	
	public static String trace(StackTraceElement e[], Exception ex) 
	{	  
		boolean doNext = false;
	   for (StackTraceElement s : e) 
	   {	
		   if (doNext)
	       {		 
			   if(s.getFileName()!=null && s.getMethodName()!=null && s.getLineNumber()!=0 && s.getClassName()!=null)
			   {
				   strClassName=s.getClassName();			   
				   strMethodName=s.getMethodName();			   
				   strLineNumber=s.getLineNumber();					   	   
			   }		   
		   }
		   doNext = s.getMethodName().equals("getStackTrace");	      
	   }
	  return getRunTimeErrorInfo(strClassName,strMethodName,strLineNumber, ex);
    }	
	
	private static String getRunTimeErrorInfo(String className, String methodName, int lineNum,Exception ex) 
	{
		StringBuilder str = new StringBuilder();
		str.append(lineSeparator);
		str.append("------------- Runtime Error Notification -----------");
		str.append(lineSeparator);
		str.append("Class Name	:"+className);
		str.append(lineSeparator);
		str.append("Method Name	:"+methodName);
		str.append(lineSeparator);
		str.append("Line Number	:"+lineNum);
		str.append(lineSeparator);
		str.append("Error Description :"+lineSeparator+ex.getMessage().toString());	
		str.append(lineSeparator);
		str.append("------------- Runtime Error End -----------");
		return str.toString();
	}	
}