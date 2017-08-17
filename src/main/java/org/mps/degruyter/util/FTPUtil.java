package org.mps.degruyter.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.mps.degruyter.exception.FTPConnectionException;

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

public class FTPUtil extends CommonUtils
{
	final static Logger logger = Logger.getLogger(FTPUtil.class);
	
	private String hostname;

	private String username;

	private String password;

	private String port;

	private String dirPath;	

	public String getHostname() {
		return this.hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDirPath() {
		return this.dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public String toString() {
		return "FTPUtil [" + (this.hostname != null ? "hostname=" + this.hostname + ", " : "") + (this.username != null ? "username=" + this.username + ", " : "") + (this.password != null ? "password=" + this.password + ", " : "") + (this.port != null ? "port=" + this.port + ", " : "")+ (this.dirPath != null ? "dirPath=" + this.dirPath : "") + "]";
	}

	private FTPClient connectFTP() throws FTPConnectionException
	{
		FTPClient client = new FTPClient();
		try 
		{
			logger.info("Going go connection for port >>> " + this.port);
			
			if ((this.port != null) && (this.port.length() > 0)) 
			{
				client.setDefaultPort(Integer.parseInt(this.port));
			}
			
			logger.info("after setting default port the value of client port :" + client.getDefaultPort());
			
			logger.info("client connect for hostname : " + this.hostname);
			
			client.connect(this.hostname);
			
			logger.info("client login for username / password is : " + this.username + " / " + this.password);
			
			if (client.login(this.username, this.password)) 
			{
				logger.info("Client.login() return true ");
			} 
			else 
			{
				logger.info("Client.login() return false ");
			}
			logger.info("getting ReplyCode from client object : " + client.getReplyCode());
			
			int reply = client.getReplyCode();
			
			logger.info("reply: " + reply);
			
			if (!FTPReply.isPositiveCompletion(reply)) 
			{
				logger.info("FTPReply code is not positive so its goes for disconnection ...");
				
				client.disconnect();
				
				throw new FTPConnectionException("FTP server refused connection.");
			 }
			
			logger.info("Going for calling client.enterLocalPassiveMode() method");
			
			client.enterLocalPassiveMode();
			
			logger.info("Going for calling client.setFileType(FTPClient.BINARY_FILE_TYPE) method");
			
			client.setFileType(2);
			
			logger.info("Return a Client object successfully ...");
			
			return client;
		}
		catch (SocketException e) 
		{
			logger.error("SocketException found : " + e.getMessage());
			
			e.printStackTrace();
			
			throw new FTPConnectionException(e.getMessage());
		} 
		catch (IOException e) 
		{
			logger.error("IOException found : " + e.getMessage());
			
			e.printStackTrace();
			
			throw new FTPConnectionException(e.getMessage());
		}
	}
	private void disconnectFTP(FTPClient client) 
	{
		if (client != null)
		{
			if (client.isConnected())
			{
				try 
				{
					logger.info("FTP connection is closed ");
					
					client.disconnect();
				}
				catch (IOException localIOException)
				{
					logger.info("Exception in ftp close");
				}
			}
		}
	}	
		
	
	public void uploadFileToQAFTP(String dirname, String localDir,String filename) throws Exception
	{	
		
		FTPClient client = null;
		
		FileInputStream inputStream = null;
		
		try 
		{
			client = connectFTP();
			
			if ((dirname != null) && (dirname.length() > 0)) 
			{
				logger.info("dir:" + dirname);
				
				client.changeWorkingDirectory(dirname);
				
				logger.info("PWD ::" + client.printWorkingDirectory());	
				
				File localFile=new File(searchFile(new File(localDir), filename));
				
				inputStream = new FileInputStream(localDir+fileSeparator()+localFile);
				
				boolean uploaded = client.storeFile(searchFile(new File(localDir), filename), inputStream);
				
				if (uploaded) 
				{
					logger.info(localFile+" File have been copied to  " + dirname + " folder!");
					
					log(LogStatus.PASS,localFile+" File have been copied to  " + dirname + " folder!");
				}
				else 
				{
					logger.error("Article uploading error !");
					
					log(LogStatus.ERROR,"Article uploading error !");
				}
				
				logger.info("Ingestion Service End");
			}
		} 
		catch (Exception e) 
		{
			logger.error("FTPConnectionException : " + e.getMessage());
			
			throw new FTPConnectionException(e.getMessage());
		}
		finally 
		{
			disconnectFTP(client);
		}		
	}	
}
