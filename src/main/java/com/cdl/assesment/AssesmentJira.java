package com.cdl.assesment;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.swing.text.BadLocationException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.cdl.util.Utilities;
import com.google.api.services.sheets.v4.model.ValueRange;

public class AssesmentJira extends Utilities{
	static final Logger APPLICATION_LOG = Logger.getLogger("devpinoyLogger");
	static int count = 0;
	static int narrative = 0;
	static Hashtable<String,Integer> hs = new Hashtable<String,Integer>();
	static String downloadLink = null;
	static String ticketNumber = null;
	static HashMap<String,String> ticketDownload = new HashMap<String,String>();
	static HashMap<String,String> ticketDueDate = new HashMap<String,String>();
	static HashMap<String,String> ticketComment = new HashMap<String,String>();
	
	public static void main(String[] args) throws IOException, InterruptedException, BadLocationException {
		String command = "";
    	String folderName = "";
    	hs.clear();
    	csvData = CSV_Reader(CSV_PATH, ",",csv_is);
      	Set<String> keys = csvData.keySet();
      	//INPUT_FOLDER_PATH = "C:/Users/cdluser/Desktop/Script/Tickets/"+todayDate()+"/";
      	INPUT_FOLDER_PATH = "\\"+"\\192.168.100.7\\ebsco\\Assessment\\Data\\"+todayDate()+"\\";
      	FILE = new File(INPUT_FOLDER_PATH);
      	
      	if(FILE.exists()){
      		deleteAllFiles(INPUT_FOLDER_PATH);
      		FILE.delete();
      		APPLICATION_LOG.debug("ALREADY CREATED FOLDER HAS BEEN DELETED");
      	}
      	
      	if(!FILE.exists()){
      		FILE.mkdir();
      		APPLICATION_LOG.debug("FOLDER CREATED OF TODAY'S DATE");
    		APPLICATION_LOG.debug("**************************************");
      	}
      	
      	String DESTINATION_FOLDER_PATH = "C:/Users/cdluser/Desktop/Script/Junk/";
		String officePath = "\\"+"\\192.168.100.20\\scripts\\CDL_Checks\\"+OFFICETOPDF_PATH;
		activeService();
		ValueRange response = service.spreadsheets().values().get(spreadsheetId, RANGE).execute();
		List<List<Object>> values = response.getValues();
    	
		windowCommandRunJira("vpncli.exe disconnect");
		Thread.sleep(3000);
		sendMail("Assesment tracker script has started", "Thank You!" , "No" , "No");
		
		APPLICATION_LOG.debug("SCRIPT STARTED");
		APPLICATION_LOG.debug("* * * * * * ");
		
		windowCommandRunJira("vpncli.exe -s < C:/Users/cdluser/Desktop/Script/vpn.txt");
		
		APPLICATION_LOG.debug("VPN Connected");
		APPLICATION_LOG.debug("**************************************");
		
		Thread.sleep(20000);
		System.setProperty("webdriver.gecko.driver","C:/Users/cdluser/Desktop/Script/"+GECKODRIVER_PATH);
		propertyFile();
		
		APPLICATION_LOG.debug("Launching the browser");
		
		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);
		WebDriver driver = new FirefoxDriver(options);
		driver.get(PROP.getProperty("url"));
		
		APPLICATION_LOG.debug("Browser Launched");

		sendText(driver, PROP.getProperty("userName"),PROP.getProperty("name"));
		sendText(driver, PROP.getProperty("password"),PROP.getProperty("pwd"));
		click(driver, PROP.getProperty("login"));
		APPLICATION_LOG.debug("Entered Login Credentials");
		Thread.sleep(40000);
		
		APPLICATION_LOG.debug("***********************************************");
		APPLICATION_LOG.debug("LOGGED IN JIRA");
		
		click(driver, PROP.getProperty("issues"));
		Thread.sleep(10000);
		click(driver, PROP.getProperty("openissues"));
		
		APPLICATION_LOG.debug("CLICKED ON OPEN ISSUES");
		
		Thread.sleep(30000);		
		
		if(elementSize(driver, PROP.getProperty("leftpaneofticket"))==0){
			APPLICATION_LOG.debug("There is no ticket in Jira");
			sendMail("No Assigned Ticket", "EOD", "No", "No");
			System.exit(0);
		}
		
		WebElement tickets = driver.findElement(By.xpath(PROP.getProperty("leftpaneofticket")));
		
		APPLICATION_LOG.debug("TOTAL TICKETS = " + tickets.findElements(By.tagName("li")).size());
		APPLICATION_LOG.debug("******************************************");

		for(int ticket = 1 ; ticket <= tickets.findElements(By.tagName("li")).size() ; ticket++){
			APPLICATION_LOG.debug("------------------------------------------------");
			ticketNumber = tickets.findElement(By.xpath(PROP.getProperty("leftpaneofticket")+"/li["+ticket+"]")).getAttribute("data-key");
			
			//CLICK ON TICKET
			
			APPLICATION_LOG.debug("Clicked on Ticket = " + ticketNumber);
			
			tickets.findElement(By.xpath(PROP.getProperty("leftpaneofticket")+"/li["+ticket+"]")).click();
			Thread.sleep(20000);
			
			// STORING DUE DATE
			
			for(int vendor = 1 ; vendor <= elementSize(driver, PROP.getProperty("datepanel")) ; vendor++){
				if(getText(driver, PROP.getProperty("datepanel")+"["+vendor+"]/dt").contains("Due")){
					ticketDueDate.put(ticketNumber, getText(driver, PROP.getProperty("datepanel")+"["+vendor+"]/dd"));
					APPLICATION_LOG.debug("Ticket due Date= " + getText(driver, PROP.getProperty("datepanel")+"["+vendor+"]/dd"));
				}
			}
		
			for(int vendor = 1 ; vendor <= elementSize(driver, PROP.getProperty("comment")) ; vendor++){
				if(elementSize(driver, PROP.getProperty("comment")) == vendor){
				
					ticketComment.put(ticketNumber, getText(driver, PROP.getProperty("comment")+"["+vendor+"]"));
					APPLICATION_LOG.debug("Ticket Comment = " + getText(driver, PROP.getProperty("comment")+"["+vendor+"]"));
				}
			}

			click(driver, PROP.getProperty("allattachment"));
			Thread.sleep(5000);
			downloadLink = driver.findElement(By.xpath(PROP.getProperty("downloadall"))).getAttribute("href");
			Thread.sleep(5000);
			ticketDownload.put(ticketNumber, downloadLink);	
		}
		
		driver.quit();

		APPLICATION_LOG.debug("***************************************************");
		APPLICATION_LOG.debug("Closed the browser");
		APPLICATION_LOG.debug("***************************************************");
		APPLICATION_LOG.debug("Downloading Started");
		APPLICATION_LOG.debug("***********************************************");
		
		for(String key : ticketDownload.keySet()){
			//APPLICATION_LOG.debug("s = " + key);

		        if(!values.toString().contains(key) && key.startsWith("DIG")){
		        	//APPLICATION_LOG.debug("Key " + key);
		        	windowCommandRunJira("wget -O "+ INPUT_FOLDER_PATH + key+".zip --no-check-certificate "+ticketDownload.get(key)+"");
		        	APPLICATION_LOG.debug(key + " has been downloaded");
		        }
		}
		
		APPLICATION_LOG.debug("Downloading Completed");
		APPLICATION_LOG.debug("***************************************************");

		//Unzip the zip folder
		
		FILE_LIST = null;
		FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".zip,.7z","Yes","No","All","NULL","NULL");
		if(FILE_LIST.get(0).size()==0)
					System.out.println("There is no zip file");
				else{
					for(int i = 0 ; i < FILE_LIST.get(0).size() ; i++){
						extractFolder(FILE_LIST.get(0).get(i));
						APPLICATION_LOG.debug("Unzip the folder " + FILE_LIST.get(0).get(i));
						}
					//sendMail("Assesment tracker script is running total tickets : " + FILE_LIST.get(0).size() , "Thank You!" , "No" , "No");
					APPLICATION_LOG.debug("Total Zip Folder = " + FILE_LIST.get(0).size());
					APPLICATION_LOG.debug("* * * * * * ");
				}

				//Doc convert to the PDF
				
				APPLICATION_LOG.debug("GOING TO CONVERT PDF FROM DOCUMENT");
				INPUT_FILE_RELATIVEPATHS.clear();
				ALL_FILE_RELATIVEPATHS.clear();
				INPUT_FILE_PATHS.clear();
				INPUT_FILE_NAMES.clear();
				ALL_FILE_NAMES.clear();
				FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".docx,.doc","Yes","No","All","NULL","NULL");
				if(FILE_LIST.get(0).size()==0)
					System.out.println("There is no docx file");
				else{
					for(int i = 0 ; i < FILE_LIST.get(0).size() ; i++){
						folderName = FILE_LIST.get(4).get(i);
						folderName = folderName.replace('\'', '\\');
						folderName = folderName.substring(folderName.lastIndexOf("\\") + 1);
						if(folderCreation(FILE_LIST.get(4).get(i), folderName+"_PDF")){}
		        		command=officePath+" " + "\"" + FILE_LIST.get(0).get(i) + "\"" + " " + "\"" +OUTPUT_FOLDER_PATH+"\\"+FILE_LIST.get(1).get(i).split("\\.")[0]+".pdf\"";
		        		windowCommandRun(command);
		        		APPLICATION_LOG.debug(OUTPUT_FOLDER_PATH+"\\"+FILE_LIST.get(1).get(i).split("\\.")[0]+".pdf");
		        		
		        		}
					APPLICATION_LOG.debug("Total Document File = " + FILE_LIST.get(0).size());
					APPLICATION_LOG.debug("* * * * * * ");
				
				}
				
				APPLICATION_LOG.debug("GOING TO EXTRACT CONTENT IN XML FROM PDF");
				
				INPUT_FILE_RELATIVEPATHS.clear();
				ALL_FILE_RELATIVEPATHS.clear();
				INPUT_FILE_PATHS.clear();
				INPUT_FILE_NAMES.clear();
				ALL_FILE_NAMES.clear();
				FILE_LIST = null;
				OUTPUT_FOLDER_PATH = null;
				FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".pdf","Yes","No","All","NULL","NULL");
				if(FILE_LIST.get(0).size()==0)
					APPLICATION_LOG.debug("There is no PDF file");
				else{
					for(int i=0 ; i < FILE_LIST.get(0).size() ; i++){
						folderName = FILE_LIST.get(4).get(i);
						folderName = folderName.replace('\'', '\\');
						folderName = folderName.substring(folderName.lastIndexOf("\\") + 1);
						if(folderCreation(FILE_LIST.get(4).get(i), folderName+"_XML")){}
						String data = textExtractionFromPdf(FILE_LIST.get(0).get(i));
						//APPLICATION_LOG.debug("Location = " + OUTPUT_FOLDER_PATH);
						writeText(OUTPUT_FOLDER_PATH+"\\"+FILE_LIST.get(1).get(i).split("\\.")[0]+".xml", data);
						APPLICATION_LOG.debug(OUTPUT_FOLDER_PATH+"\\"+FILE_LIST.get(1).get(i).split("\\.")[0]+".xml");
						}
					
					APPLICATION_LOG.debug("Total PDF Folder = " + FILE_LIST.get(0).size());
					APPLICATION_LOG.debug("* * * * * * ");
				}
				
				APPLICATION_LOG.debug("WRITTING QUESTION COUNT IN GOOGLE SHEET");
			
				FILE_LIST = null;
				FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".docx,.doc,.rtf","Yes","No","All","NULL","NULL");
				if(FILE_LIST.get(0).size()==0)
					APPLICATION_LOG.debug("There is no document file");
				else{
					for(int i = 0 ; i < FILE_LIST.get(0).size() ; i++){
						//System.out.println("Total documents = " + FILE_LIST.get(0).size());
		        		if(FILE_LIST.get(0).get(i).endsWith(".docx"))
		    				PARAGRAPHS = readDocxFile(FILE_LIST.get(0).get(i));
		        		if(FILE_LIST.get(0).get(i).endsWith(".rtf"))
		    				PARAGRAPHS = readRtfFile(FILE_LIST.get(0).get(i));
		    			if(FILE_LIST.get(0).get(i).endsWith(".doc"))
		    				PARAGRAPHS = readDocFile(FILE_LIST.get(0).get(i));	
		    			for(String keyy: keys){
		    				for(String para : PARAGRAPHS){
		    					if(para.contains("”"))
		    					 para = para.replace("”","");
		    					if(para.contains("’"))
		    						 para = para.replace("’","");
		    					if(para.contains("\""))
		    						 para = para.replace("\"","");
		    					if(para.contains("“"))
		    					 para = para.replace("“", "");
		    					if(para.contains(" <"))
		    						para = para.replace(" <","<");
		    					para = para.replace("\">", "\">\n");
		    					para = para.replace("\"", "");
		    					para = para.replace(" ","");
		    					keyy = keyy.replace("\"", "");
		    						if(para.contains(keyy)){
		    							count++;
		    							hs.put(keyy, count);
		    						}
		    					}
		    				count = 0;
		    			}	
		    			Set<String> data = hs.keySet();
		    			for(String d : data){
		    				try {
		    					setValue(systemDate(),FILE_LIST.get(0).get(i), d, hs.get(d));
		    					FILE.renameTo(new File(DESTINATION_FOLDER_PATH+"//"+FILE.getName()));
		    				} catch (IOException e) {
		    					APPLICATION_LOG.debug(e.getMessage());
		    					System.out.println(e.getMessage());
		    					}
		    			}
		    			hs.clear();
				}
				}
				
			deleteAllFiles(DESTINATION_FOLDER_PATH);
			
			
			setValue(systemDate(),"Done","Done",-1);
			APPLICATION_LOG.debug(SUCCESS_MESSAGE);
			sendMail("Assesment tracker script complete status" , "Hi All, " + System.lineSeparator()+System.lineSeparator() + "You can find details on below link : " +System.lineSeparator()+ link + System.lineSeparator()+System.lineSeparator() + "Thank You!" , "Yes" , "C:/Users/cdluser/Desktop/Script/Log/LOGGER.log");
			File f = new File("C:/Users/cdluser/Desktop/Script/Log/LOGGER.log");
			f.delete();
		    print(SUCCESS_MESSAGE);
		    
		    System.exit(0);
			windowCommandRunJira("exit");	
				//driver.close();

		
		//driver.close();
	}

}
