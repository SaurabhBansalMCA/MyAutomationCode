package com.cdl.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.cdl.declaration.GlobalVariables;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class Utilities extends GlobalVariables{
	
	public static String slash = systemPath(); 
	public static List<ArrayList<String>> filesInDirectories(String directoryPath,
			String requiredFileExtension,
			String recursive,
			String ignoureFileExtension,
			String onlydDirectoryPathOrAllFiles,
			String placeHolder1,
			String placeHolder2){
		INPUT_FILE_NAMES.clear();
		INPUT_FILE_PATHS.clear();
		FILE_EXTENSION.clear();
		outerList.clear();
		ALL_FILE_PATHS.clear();
		OUTER_FILE_LIST.clear();
		DIRECTORY_PATH.clear();
		UNIQUE_NUMBER.clear();
		if(onlydDirectoryPathOrAllFiles.equalsIgnoreCase("Directory")){
			DIRECTORY_PATH = directoryPaths(directoryPath);
			UNIQUE_NUMBER = uniqueNumber(DIRECTORY_PATH);
			outerList.add(DIRECTORY_PATH);
			outerList.add(UNIQUE_NUMBER);
		}
		if(onlydDirectoryPathOrAllFiles.equalsIgnoreCase("All")){
			if(recursive.equalsIgnoreCase("Yes")){
				OUTER_FILE_LIST = listFilesAndFilesSubDirectories(directoryPath);
				if(requiredFileExtension.contains(",")){
					for (int temp = 0 ; temp < OUTER_FILE_LIST.get(0).size() ; temp++){
						for(String fileEx : listFileExtension(requiredFileExtension)){
							if(OUTER_FILE_LIST.get(0).get(temp).endsWith(fileEx)){
								INPUT_FILE_PATHS.add(OUTER_FILE_LIST.get(0).get(temp));
								INPUT_FILE_NAMES.add(OUTER_FILE_LIST.get(1).get(temp));
								INPUT_FILE_EXTENSIONS.add(fileExtension(OUTER_FILE_LIST.get(1).get(temp)));
								INPUT_FILE_RELATIVEPATHS.add(OUTER_FILE_LIST.get(2).get(temp));
							}
						}
					}
					UNIQUE_NUMBER = uniqueNumber(INPUT_FILE_PATHS);
				}
				if(!requiredFileExtension.contains(",") && requiredFileExtension.startsWith(".")){
					for (int temp = 0 ; temp < OUTER_FILE_LIST.get(0).size() ; temp++){
						if(OUTER_FILE_LIST.get(0).get(temp).endsWith(requiredFileExtension)){
							INPUT_FILE_PATHS.add(OUTER_FILE_LIST.get(0).get(temp));
							INPUT_FILE_NAMES.add(OUTER_FILE_LIST.get(1).get(temp));
							INPUT_FILE_EXTENSIONS.add(fileExtension(OUTER_FILE_LIST.get(1).get(temp)));
							INPUT_FILE_RELATIVEPATHS.add(OUTER_FILE_LIST.get(2).get(temp));
						}
					}
					UNIQUE_NUMBER = uniqueNumber(INPUT_FILE_PATHS);
				}
				if(requiredFileExtension.equalsIgnoreCase("all") && ignoureFileExtension.equalsIgnoreCase("no")){
					for (int temp = 0 ; temp < OUTER_FILE_LIST.get(0).size() ; temp++){
						INPUT_FILE_PATHS.add(OUTER_FILE_LIST.get(0).get(temp));
						INPUT_FILE_NAMES.add(OUTER_FILE_LIST.get(1).get(temp));
						INPUT_FILE_EXTENSIONS.add(fileExtension(OUTER_FILE_LIST.get(1).get(temp)));
						INPUT_FILE_RELATIVEPATHS.add(OUTER_FILE_LIST.get(2).get(temp));
					}
					UNIQUE_NUMBER = uniqueNumber(INPUT_FILE_PATHS);
				}
				if(requiredFileExtension.equalsIgnoreCase("all") && ignoureFileExtension.contains(",")){
					for (int temp = 1 ; temp <= OUTER_FILE_LIST.get(0).size() ; temp++){
						for(String fileEx : listFileExtension(requiredFileExtension)){
							if(OUTER_FILE_LIST.get(0).get(temp-1).endsWith(fileEx)){
								OUTER_FILE_LIST.get(0).remove(temp-1);
								OUTER_FILE_LIST.get(1).remove(temp-1);
								temp--;
								break;
							}
						}
					}
					for (int temp = 0 ; temp < OUTER_FILE_LIST.get(0).size() ; temp++){
						INPUT_FILE_PATHS.add(OUTER_FILE_LIST.get(0).get(temp));
						INPUT_FILE_NAMES.add(OUTER_FILE_LIST.get(1).get(temp));
						INPUT_FILE_EXTENSIONS.add(fileExtension(OUTER_FILE_LIST.get(1).get(temp)));
						INPUT_FILE_RELATIVEPATHS.add(OUTER_FILE_LIST.get(2).get(temp));
					}
					UNIQUE_NUMBER = uniqueNumber(INPUT_FILE_PATHS);
				}
				if(requiredFileExtension.equalsIgnoreCase("all") && (!ignoureFileExtension.contains(","))){
					for (int temp = 0 ; temp < OUTER_FILE_LIST.get(0).size() ; temp++){
						if(!OUTER_FILE_LIST.get(0).get(temp).endsWith(ignoureFileExtension)){
							INPUT_FILE_PATHS.add(OUTER_FILE_LIST.get(0).get(temp));
							INPUT_FILE_NAMES.add(OUTER_FILE_LIST.get(1).get(temp));
							INPUT_FILE_EXTENSIONS.add(fileExtension(OUTER_FILE_LIST.get(1).get(temp)));
							INPUT_FILE_RELATIVEPATHS.add(OUTER_FILE_LIST.get(2).get(temp));
						}
					}
					UNIQUE_NUMBER = uniqueNumber(INPUT_FILE_PATHS);
				}
			}

			if(recursive.equalsIgnoreCase("No")){		
				OUTER_FILE_LIST = listFilesInRootDirectory(directoryPath);
				if(requiredFileExtension.contains(",")){
					for (int temp = 0 ; temp < OUTER_FILE_LIST.get(0).size() ; temp++){
						for(String fileEx : listFileExtension(requiredFileExtension)){
							if(OUTER_FILE_LIST.get(0).get(temp).endsWith(fileEx)){
								INPUT_FILE_PATHS.add(OUTER_FILE_LIST.get(0).get(temp));
								INPUT_FILE_NAMES.add(OUTER_FILE_LIST.get(1).get(temp));
								INPUT_FILE_EXTENSIONS.add(fileExtension(OUTER_FILE_LIST.get(1).get(temp)));
								INPUT_FILE_RELATIVEPATHS.add(OUTER_FILE_LIST.get(2).get(temp));
							}
						}
					}
					UNIQUE_NUMBER = uniqueNumber(INPUT_FILE_PATHS);
				}
				if(!requiredFileExtension.contains(",") && requiredFileExtension.startsWith(".")){
					for (int temp = 0 ; temp < OUTER_FILE_LIST.get(0).size() ; temp++){
						if(OUTER_FILE_LIST.get(0).get(temp).endsWith(requiredFileExtension)){
							INPUT_FILE_PATHS.add(OUTER_FILE_LIST.get(0).get(temp));
							INPUT_FILE_NAMES.add(OUTER_FILE_LIST.get(1).get(temp));
							INPUT_FILE_EXTENSIONS.add(fileExtension(OUTER_FILE_LIST.get(1).get(temp)));
							INPUT_FILE_RELATIVEPATHS.add(OUTER_FILE_LIST.get(2).get(temp));
						}
					}
					UNIQUE_NUMBER = uniqueNumber(INPUT_FILE_PATHS);
				}
				if(requiredFileExtension.equalsIgnoreCase("all") && (!ignoureFileExtension.contains(","))){
					for (int temp = 0 ; temp < OUTER_FILE_LIST.get(0).size() ; temp++){
						if(!OUTER_FILE_LIST.get(0).get(temp).endsWith(ignoureFileExtension)){
							INPUT_FILE_PATHS.add(OUTER_FILE_LIST.get(0).get(temp));
							INPUT_FILE_NAMES.add(OUTER_FILE_LIST.get(1).get(temp));
							INPUT_FILE_EXTENSIONS.add(fileExtension(OUTER_FILE_LIST.get(1).get(temp)));
							INPUT_FILE_RELATIVEPATHS.add(OUTER_FILE_LIST.get(2).get(temp));
						}
					}
					UNIQUE_NUMBER = uniqueNumber(INPUT_FILE_PATHS);
				}
				if(requiredFileExtension.equalsIgnoreCase("all") && ignoureFileExtension.equalsIgnoreCase("no")){
					for (int temp = 0 ; temp < OUTER_FILE_LIST.get(0).size() ; temp++){
						INPUT_FILE_PATHS.add(OUTER_FILE_LIST.get(0).get(temp));
						INPUT_FILE_NAMES.add(OUTER_FILE_LIST.get(1).get(temp));
						INPUT_FILE_EXTENSIONS.add(fileExtension(OUTER_FILE_LIST.get(1).get(temp)));
						INPUT_FILE_RELATIVEPATHS.add(OUTER_FILE_LIST.get(2).get(temp));
					}
					UNIQUE_NUMBER = uniqueNumber(INPUT_FILE_PATHS);
				}
				if(requiredFileExtension.equalsIgnoreCase("all") && ignoureFileExtension.contains(",")){
					for (int temp = 1 ; temp <= OUTER_FILE_LIST.get(0).size() ; temp++){
						for(String fileEx : listFileExtension(requiredFileExtension)){
							if(OUTER_FILE_LIST.get(0).get(temp-1).endsWith(fileEx)){
								OUTER_FILE_LIST.get(0).remove(temp-1);
								OUTER_FILE_LIST.get(1).remove(temp-1);
								temp--;
								break;
							}
						}
					}
					for (int temp = 0 ; temp < OUTER_FILE_LIST.get(0).size() ; temp++){
						INPUT_FILE_PATHS.add(OUTER_FILE_LIST.get(0).get(temp));
						INPUT_FILE_NAMES.add(OUTER_FILE_LIST.get(1).get(temp));
						INPUT_FILE_EXTENSIONS.add(fileExtension(OUTER_FILE_LIST.get(1).get(temp)));
						INPUT_FILE_RELATIVEPATHS.add(OUTER_FILE_LIST.get(2).get(temp));
					}
					UNIQUE_NUMBER = uniqueNumber(INPUT_FILE_PATHS);
				}
			}
			outerList.add(INPUT_FILE_PATHS);
			outerList.add(INPUT_FILE_NAMES);
			outerList.add(INPUT_FILE_EXTENSIONS);
			outerList.add(UNIQUE_NUMBER);
			outerList.add(INPUT_FILE_RELATIVEPATHS);
		}
		return outerList;
	}

	
	
	public static List<ArrayList<String>> listFilesAndFilesSubDirectories(String directoryName){		
		FILE = new File(directoryName);
		File[] fList = FILE.listFiles();
		for (File file : fList){
			if (file.isFile()){
              ALL_FILE_PATHS.add(file.getAbsolutePath());
              ALL_FILE_NAMES.add(file.getName());
              ALL_FILE_RELATIVEPATHS.add(file.getParent());
			}
			else if (file.isDirectory()){
				listFilesAndFilesSubDirectories(file.getAbsolutePath());
			}
		}
		OUTER_FILE_LIST.add(ALL_FILE_PATHS);
		OUTER_FILE_LIST.add(ALL_FILE_NAMES);
		OUTER_FILE_LIST.add(ALL_FILE_RELATIVEPATHS);
		return OUTER_FILE_LIST;
	}
	
	public static ArrayList<String> listFileExtension(String fileExtension){
		FILE_EXTENSION.clear();
		if(fileExtension.contains(",")){
			for(int i=0 ; i<fileExtension.split(",").length; i++){
				FILE_EXTENSION.add(fileExtension.split(",")[i]);
			}
		}
		return FILE_EXTENSION;
	}

	
	public static List<ArrayList<String>> listFilesInRootDirectory(String directoryName){
		ALL_FILE_PATHS.clear();
		FILE = new File(directoryName);
		File[] fList = FILE.listFiles();
		for (File file : fList){
			if (file.isFile()){
				ALL_FILE_PATHS.add(file.getAbsolutePath());
				ALL_FILE_NAMES.add(file.getName());
				ALL_FILE_RELATIVEPATHS.add(file.getParent());
			}
		}
		OUTER_FILE_LIST.add(ALL_FILE_PATHS);
		OUTER_FILE_LIST.add(ALL_FILE_NAMES);
		OUTER_FILE_LIST.add(ALL_FILE_RELATIVEPATHS);
		return OUTER_FILE_LIST;
	}
	
	public static String fileExtension(String fileName){
		return fileName.split("\\.")[1];
	}
	
	
	public static ArrayList<String> directoryPaths(String directoryName){
		FILE = new File(directoryName);
		File[] fList = FILE.listFiles();
		for (File file : fList){
			if (file.isDirectory())
				DIRECTORY_PATH.add(file.getAbsolutePath());
		}
		return DIRECTORY_PATH;
	}
	
	public static ArrayList<String> uniqueNumber(List<String> arrayList){
		for(int i = 1 ; i <= arrayList.size() ; i++){
			UNIQUE_NUMBER.add(Integer.toString(i));
		}
		return UNIQUE_NUMBER;
	}
	

	
	/*public static boolean textFileCreation(String path){
		try {
			FILE = new File(path+"//DuplicateCGI.txt");
			if (FILE.createNewFile()){
		        //System.out.println("File is created!");
		        return true;
			}
			else{
		        System.out.println("Please delete the existing file");
		        return false;
			}
		}
		catch (IOException e) {
		      e.printStackTrace();
		}
		return false;
	}*/
	
	public static boolean folderCreation(String path,String folderName) throws IOException{
		FILE = new File(path+slash+folderName);
		    if (FILE.mkdir()) {
		        OUTPUT_FOLDER_PATH = path+slash+folderName;
		        return true;
		    }
		    else if(FILE.exists()){
		        return false;
		    }
		    OUTPUT_FOLDER_PATH = path+slash+folderName;
		return false;
	}
	
	public static String todayDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return  dateFormat.format(date);
	}
	
	public static void writeText(String fileName , String data) throws IOException{
		FW = new FileWriter(fileName, true);
		BW = new BufferedWriter(FW);
		BW.write(data);
		BW.newLine();
		BW.close();
	}
	
	
	public static void windowCommandRun(String command){
		 try 
         { 
             Process p=Runtime.getRuntime().exec(command); 
             p.waitFor(); 
             BufferedReader reader=new BufferedReader(
                 new InputStreamReader(p.getInputStream())
             ); 
             String line; 
             while((line = reader.readLine()) != null) 
             { 
                 System.out.println(line);
             } 

         }
         catch(IOException e1) {} 
         catch(InterruptedException e2) {} 
	}
	
	public static void windowCommandRunJira(String command) throws IOException{

			 ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/C", command);
			 builder.redirectErrorStream(true);
			 Process p = builder.start();
			 BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			 String line;
			 while (true) {
				 line = r.readLine();
				 	if (line == null) { break; }
				 System.out.println(line);
			 }
			 System.out.println("Done");
        }
	
	
	public static void successfullMessage(){
		System.out.println(SUCCESS_MESSAGE);
	}
	
	
	/*public static void writeTextInExcel(String functionName ,
										String argumentName , 
										String project,
										String comments,
										String develpoer,
										String date){
		//System.out.println("start = " + XLS.getRowCount(SHEET_NAME));
		for(int row = 1 ; row<=XLS.getRowCount(SHEET_NAME) ; row++){
				if(XLS.getCellData(SHEET_NAME, "Function Name", row).equalsIgnoreCase(functionName)){
				System.out.println("Already Recorded");
				break;
				}
				if(XLS.getCellData(SHEET_NAME, 1, row).equals("")){
					XLS.setCellData(SHEET_NAME,"Function Name",row, functionName);
					XLS.setCellData(SHEET_NAME,"Arguments",row, argumentName);
					XLS.setCellData(SHEET_NAME,"Project",row, project);
					XLS.setCellData(SHEET_NAME,"Comments",row, comments);
					XLS.setCellData(SHEET_NAME,"Developed By",row, develpoer);
					XLS.setCellData(SHEET_NAME,"Date",row, date);
					break;
				}
			}
	}*/
			
	/*public static void readDataInExcel(){
		int totalRows = 0;
		for(int row = 0 ; row <= XLS.getRowCount(SHEET_NAME) ; row++){
			if(!XLS.getCellData(SHEET_NAME, 1, row).equals("")){
				totalRows++;
			}
		}
		System.out.println("\n");
		System.out.print("  ||  " + XLS.getCellData(SHEET_NAME, 0, 1));
		System.out.print("  ||  " + XLS.getCellData(SHEET_NAME, 1, 1));
		System.out.print("  ||  " + XLS.getCellData(SHEET_NAME, 2, 1));
		System.out.println("  ||  " + XLS.getCellData(SHEET_NAME, 3, 1));
		System.out.println("--------------------------------------------------------------------");
		System.out.println("\n");
		for(int i= 2 ; i <= totalRows ; i++){
			for(int j = 0 ; j < 4 ; j++){
				System.out.print("  ||  " + XLS.getCellData(SHEET_NAME, j, i));
			}
			System.out.println("\n");
		}
	}*/
	
	public static void extractFolder(String zipFile) throws ZipException, IOException 
	{
	    int BUFFER = 2048;
	    FILE = new File(zipFile);

	    ZipFile zip = new ZipFile(FILE);
	    String newPath = zipFile.substring(0, zipFile.length() - 4);

	    new File(newPath).mkdir();
	    Enumeration zipFileEntries = zip.entries();

	    // Process each entry
	    while (zipFileEntries.hasMoreElements())
	    {
	        ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
	        String currentEntry = entry.getName();
	        File destFile = new File(newPath, currentEntry);
	        File destinationParent = destFile.getParentFile();
	        destinationParent.mkdirs();

	        if (!entry.isDirectory())
	        {
	            BIS = new BufferedInputStream(zip
	            .getInputStream(entry));
	            int currentByte;
	            // establish buffer for writing file
	            byte data[] = new byte[BUFFER];

	            // write the current file to disk
	            FOS = new FileOutputStream(destFile);
	            BOS = new BufferedOutputStream(FOS,
	            BUFFER);

	            // read and write until last byte is encountered
	            while ((currentByte = BIS.read(data, 0, BUFFER)) != -1) {
	                BOS.write(data, 0, currentByte);
	            }
	            BOS.flush();
	            BOS.close();
	            BIS.close();
	        }

	        if (currentEntry.endsWith(".zip"))
	        {
	            // found a zip file, try to open
	            extractFolder(destFile.getAbsolutePath());
	        }
	    }
	}

	
	public static List<String> readDocxFile(String fileName) {
		PARAGRAPHS.clear();
		try {
			FILE = new File(fileName);
			FIS= new FileInputStream(FILE.getAbsolutePath());
			XWPFDocument document = new XWPFDocument(FIS);
			XWPFParagraph[] paragraphs = document.getParagraphs();
			//paragraphs.length
			//System.out.println("Total no of paragraph "+paragraphs.length);
			for (XWPFParagraph para : paragraphs) {
				PARAGRAPHS.add(para.getText());
				//System.out.println(para.getText());
			}
			FIS.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return PARAGRAPHS;
		
	}
	
	public static void print(String message){
		System.out.println(message);
	}


	public static List<String> readRtfFile(String fileName) throws IOException, BadLocationException {
		PARAGRAPHS.clear();
		String[] paragraphs = null;
		FIS = new FileInputStream(fileName);
		RTFEditorKit kit = new RTFEditorKit();
		Document doc = kit.createDefaultDocument();
		kit.read(FIS, doc, 0);
		paragraphs = doc.getText(0, doc.getLength()).split("\\n"); 
		for(String s : paragraphs)
			PARAGRAPHS.add(s);
		return PARAGRAPHS;
		
	}


public static List<String> readDocFile(String fileName) {
	PARAGRAPHS.clear();
	String[] paragraphs = null;
	try {
		FILE = new File(fileName);
		FIS = new FileInputStream(FILE.getAbsolutePath());
		HWPFDocument document = new HWPFDocument(FIS);
		WordExtractor we = new WordExtractor(document);
		paragraphs = we.getParagraphText();
	//	System.out.println("Total no of paragraph "+paragraphs.length);
		for (String para : paragraphs) {
			PARAGRAPHS.add(para.toString());
		//	System.out.println(para.toString());
		}
	FIS.close();
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	return PARAGRAPHS;
}


	public static boolean xmlParse() throws ParserConfigurationException, SAXException, IOException{
        dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(xml_is);
		return true;
		//return false;
	}
	
	public static void xmlWrite(String projectName , String functionName , String argumentType , String comments , String dates , String developedBy) throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, SAXException, IOException{
		int value = 1;
		if(xmlParse()){
			NodeList nList = doc.getElementsByTagName("functionname");			
			//System.out.println("total " + nList.getLength());
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if(nNode.getTextContent().equalsIgnoreCase(functionName)){
       	 			System.out.println("Already Recorded");
       	 			value = 0;
       	 		}
       	 		/*if(!nNode.getTextContent().equalsIgnoreCase(functionName)){
       	 			value = 1;
       	 			System.out.println("ssss");
       	 		}*/
			}
			if(value == 1 && nList.getLength() > 0){
				Node projects = doc.getElementsByTagName("projects").item(0);
				NodeList n = (NodeList) doc.getElementsByTagName("projects").item(0);
				Element e = doc.getDocumentElement();
				Element project = doc.createElement("project");
				Attr att = doc.createAttribute("name");
				att.setValue(projectName);
				project.setAttributeNode(att);
				projects.appendChild(project);
				Element functionname = doc.createElement("functionname");
				functionname.appendChild(doc.createTextNode(functionName));
				project.appendChild(functionname);
       	    
				Element argumenttype = doc.createElement("argumenttype");
				argumenttype.appendChild(doc.createTextNode(argumentType));
				project.appendChild(argumenttype);
    	    
				Element comment = doc.createElement("comment");
				comment.appendChild(doc.createTextNode(comments));
				project.appendChild(comment);
       	    
				Element date = doc.createElement("date");
				date.appendChild(doc.createTextNode(dates));
				project.appendChild(date);
    	    
				Element developedby = doc.createElement("developedby");
				developedby.appendChild(doc.createTextNode(developedBy));
				project.appendChild(developedby);
       	    
				((Node) n).appendChild(project);

				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				StreamResult result = new StreamResult(new File(XML_PATH));
				DOMSource source = new DOMSource(doc);
				transformer.transform(source, result);
				System.out.println("Entered in XML");
			}
		}
		else{
			print("XML is not parssed successfully");
		}
	}
	
	
	public static String textExtractionFromPdf(String filePath) throws InvalidPasswordException, IOException{
		PDDocument pdf = PDDocument.load(new File(filePath));
    	PDFTextStripper pdfStripper = new PDFTextStripper();
    	String parsedText = pdfStripper.getText(pdf);
    	return parsedText;
		
	}
	
	public static void readDataXML() throws DOMException, ParserConfigurationException, SAXException, IOException{
		if(xmlParse()){
			//doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("project");
			TBA.addRow("Project","|","Function Name","|","Argument Type");
			TBA.addRow("-------","|","-------------","|","-------------");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					TBA.addRow(eElement.getAttribute("name"),
								"|",
								eElement.getElementsByTagName("functionname").item(0).getTextContent(),
								"|",
								eElement.getElementsByTagName("argumenttype").item(0).getTextContent());
					}
				}
			System.out.println(TBA.toString());
		}
	}
	
	public static  Hashtable<String,String> CSV_Reader(String csvPath , String splittedBy , InputStream csv_is) {
		csvData.clear();
        String line = "";
        String[] questionType = null;
        try {
        	BR = new BufferedReader(new InputStreamReader(csv_is));
            while ((line = BR.readLine()) != null) {
            	// use comma as separator
                questionType = line.split(splittedBy);
                //System.out.println(questionType[1]);
                csvData.put(questionType[1], questionType[0]);
            }
        //    System.out.println(csvData.size());
        } catch (Exception e) {
        	System.out.println(e);
        	}
		return csvData;
    }
	
	public static void propertyFile() throws IOException{
		PROP.load(prop_is);
		}
	
	public static Credential authorize() throws IOException {
        // Load client secrets.
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
        InputStream in = Utilities.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("test@gmail.com");

        return credential;
    }

    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
    }
    
    public static void activeService() throws IOException{
    	service = getSheetsService();
    }
	
    public static int getSheetRows() throws IOException{
    	activeService();
    	response = service.spreadsheets().values().get(spreadsheetId, sheetRange).execute();   
    	List<List<Object>> values = response.getValues();
		return values.size();  
    }
    
    public static void setValue(String date,String docName,String questionType,int questionCount) throws IOException{
    	//activeService();
    	int rowStart = getSheetRows()+1;
    	String range = "A"+rowStart+":Z";
    	response.setRange(range); // I NEED THE NUMBER OF THE LAST ROW
    	response.setValues(getData(date,docName,questionType,questionCount));
    	List<ValueRange> oList = new ArrayList();
    	oList.add(response);
    	BatchUpdateValuesRequest oRequest = new BatchUpdateValuesRequest().setValueInputOption("RAW").setData(oList);
    	BatchUpdateValuesResponse oResp1sd = service.spreadsheets().values().batchUpdate(spreadsheetId, oRequest).execute();
    	}
    
    public static List<List<Object>> getData (String date,String docName,String questionType,int questionCount)  {

  	  List<Object> data1 = new ArrayList<Object>();
  	  data1.add(date);
  	  data1.add (docName);
  	  data1.add (questionType);
  	  data1.add (questionCount);
  	  List<List<Object>> data = new ArrayList<List<Object>>();
  	  data.add (data1);
  	  return data;
  	}
    
    public static String systemDate(){
    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
//		System.out.println(dateFormat.format(date));
		return dateFormat.format(date);
    }
    
    public static String systemPath() {
        if(System.getProperty("os.name").contains("Windows"))
            ARGUMENT =  "\\";
        if(System.getProperty("os.name").contains("Linux"))
            ARGUMENT = "/";
        return ARGUMENT;
        
    } 
    
    public static boolean deleteAllFiles(String path){
    	FILE = new File(path);
		File[] files = FILE.listFiles();
		for(File file : files)
			file.delete();
		return true;
    }
    
    public static void unZipDocx(String docxFile, String outputFolder){
    	byte[] buffer = new byte[1024];
    	try{
    		
    		//create output directory is not exists
    		FILE = new File(outputFolder);
    		if(!FILE.exists()){
    			FILE.mkdir();
    		}
    		//get the zip file content
    		ZipInputStream zis = new ZipInputStream(new FileInputStream(docxFile));
    		//get the zipped file list entry
    		ZipEntry ze = zis.getNextEntry();
    		while(ze!=null){
    			String fileName = ze.getName();
    			FILE = new File(outputFolder + File.separator + fileName);
    		//	System.out.println("file unzip : "+ FILE.getAbsoluteFile());

               //create all non exists folders
               //else you will hit FileNotFoundException for compressed folder
               new File(FILE.getParent()).mkdirs();
               FOS = new FileOutputStream(FILE);
               int len;
               while ((len = zis.read(buffer)) > 0) {
            	   FOS.write(buffer, 0, len);
               }
               FOS.close();
               ze = zis.getNextEntry();
               }
    		zis.closeEntry();
    		zis.close();
    		System.out.println("Done");
    	}catch(IOException ex){
    		ex.printStackTrace();
    		}
    	}
    
    public static class MyTimeTask extends TimerTask {
        public void run() {
           System.out.println("Running Task");
           System.out.println("Current Time: " + df.format( new Date()));
           timer.cancel();
        }
     }
    
    public static void dynamicWait(WebDriver driver , String element){
    	WebDriverWait wait = new WebDriverWait(driver,60);
    	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(element)));
    }
    
    public static void sendText(WebDriver driver , String locator , String data){
    	if(isElementPresent(driver, locator))
    		driver.findElement(By.xpath(locator)).sendKeys(data);
    	else
    		System.out.println(locator +"Locator is not present");
    }
    
    public static void click(WebDriver driver , String locator){
    	if(isElementPresent(driver, locator))
    		driver.findElement(By.xpath(locator)).click();
    	else
    		System.out.println(locator +"Locator is not present");
    }
    
    public static boolean isElementPresent(WebDriver driver , String xPath){
    	if(driver.findElements(By.xpath(xPath)).size()>0){
    		return true;
    	}
    	else{
    		System.out.println("Not Present");
    		return true;
    	}
    	
    }
    
    public static int elementSize(WebDriver driver , String xPath){
    	return driver.findElements(By.xpath(xPath)).size();
    	
    }
    
    public static String getText(WebDriver driver , String xPath) {
    	return driver.findElement(By.xpath(xPath)).getText();
    	
    }
    
    public static void sendMail(String sub , String msg, String attachment , String attachmentFilePath) throws IOException{
    	final String username = "saurabhbansal.mca@gmail.com";
		final String password = "ihatemoney16";

		// setting gmail smtp properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.debug", "true");


		// check the authentication
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("saurabhbansal.mca@gmail.com"));

			// recipients email address
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("saurabhbansal@qainfotech.com,akhileshyadav@qaicdl.in,devendrakumar@qaicdl.in,sachin.kumar@qaicdl.in"));

			// add the Subject of email
			message.setSubject(sub);

			Multipart multipart = new MimeMultipart();

			// add the body message
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setText(msg);
			multipart.addBodyPart(bodyPart);

			if(attachment.equalsIgnoreCase("Yes")){
			// attach the file
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.attachFile(new File(attachmentFilePath));
			multipart.addBodyPart(mimeBodyPart);
			}
			
			message.setContent(multipart);

			Transport.send(message);
			System.out.println("Email Sent Successfully");

		} catch (MessagingException e) {
			System.out.println(e.getMessage());
			

    }
    }
    
    
    
    //Arun
    
    public static List<File> getFileNames(String location) {
        File directoryName = new File(location);
        File[] fileList = directoryName.listFiles();
        List<File> resultSet = new ArrayList<File>();
        
        for (File file : fileList) {
        	if (file.isFile())
                resultSet.add(file.getAbsoluteFile());
            else if (file.isDirectory()){
            	if(file.getName().equalsIgnoreCase("DuplicateXMLs")){}
            	 else {
                resultSet.addAll(getFileNames(file.getAbsolutePath()));
            	 }
            }
        }    
        return resultSet;
    }
    
	public  static void figHR(String location) throws IOException{
		
		location = location+"/";

		
		List<File> helpfiles = getFileNames(location);
    	for(File file2 : helpfiles)
    	{

			String fileNameOfHelpxml = file2.getName();
			String fileNameSplit= fileNameOfHelpxml.substring(fileNameOfHelpxml.length()-4, fileNameOfHelpxml.length());
				
			if(fileNameSplit.equalsIgnoreCase(".xml")){
		
		    //Figure_Help xml reader
		    FileReader f2r = new FileReader(file2);
		    BufferedReader b2r = new BufferedReader(f2r);
		    
		    //Figure_Help xml Writer
		    String Dlocation = location+"Dup_"+file2.getName();
		   // System.out.println("dddd " + Dlocation);
		    File f3 = new File (Dlocation);//location+"/Figure_H1_Duplicate.xml"
		    
		    FileWriter f3w = new FileWriter (f3, true);
		    BufferedWriter b3w = new BufferedWriter(f3w);
		    
		    //Replace Enter
		    String z = b2r.readLine();
		        while(z!=null){
		        b3w.write(z.trim());
		        z= b2r.readLine();       // System.out.println("null " + z);
		    } 
		        b3w.flush();
		       
		    
		     FileReader f3r = new FileReader(f3);
		     BufferedReader b3r = new BufferedReader(f3r);
		     String z2 = b3r.readLine();
		     //z2=(z2.replace("aa          ", "aa"));
		     z2=(z2.replace("       ", " "));
		     z2=(z2.replace("      ", " "));
		     z2=(z2.replace("     ", " "));
		     z2=(z2.replace("    ", " "));
		     z2=(z2.replace("   ", " "));
		     z2=(z2.replace("  ", " "));
		     z2=(z2.replace("  ", " "));
		     z2=(z2.replace("  ", " "));
		     z2=(z2.replace("  ", " "));
		     z2=(z2.replace("  ", " "));
		     z2=(z2.replace("  ", " "));
		     z2=(z2.replace("</cl:figure><cl:figure", "</cl:figure>  <cl:figure"));
		     //System.out.println(z2);

		      String z5[] = z2.trim().split("  ");
		      //System.out.println("Current locatiion = " + location);
		      //System.out.println("00000" + file2);
		      //File file = new File(location);
		      FileWriter f2w = new FileWriter (file2);
		      //System.out.println(f2w);
		      BufferedWriter b2w = new BufferedWriter(f2w);
		     
		      for(int i=0; i<z5.length; i++){
		    	  b2w.write(z5[i]);
		    	  b2w.newLine();
		        }
		      b2w.flush();
		     b3w.close();
		     b3r.close();
		     boolean result = Files.deleteIfExists(f3.toPath());

		     FileReader f5r = new FileReader(file2);
             BufferedReader b5r = new BufferedReader(f5r);
             String figureTag = b5r.readLine();//// Coppy line
             
             while(figureTag!=null){
            	 String zz2[] = figureTag.trim().split("<cl:ordinal>");
            	 String zz4[] = zz2[0].trim().split("<cl:label>");
            	 String flabel = zz4[1];
            	 
                 String zz3[] = zz2[1].trim().split("</cl:ordinal>");
                 String fOrdinal = zz3[0];
       
                 figureList.put(flabel+fOrdinal, figureTag);
        
                 figureTag = b5r.readLine();
 		    }
 		        
             
			}
           	     
		     
	}
	  
	}
	
	public static void LogFiles (String logFileFolderPathh) throws IOException{
		logFileFolderPath=logFileFolderPathh;
		String log_FilesDirectory = logFileFolderPath+"Log_Files";
    	//System.out.println("Directory created..........................");
    	new File(log_FilesDirectory).mkdir();
		
		logFileFolderPath=logFileFolderPathh;
		
/*		logFile = new File (logFileFolderPath+"/Log_File.text");
		FileWriter logFileWriter = new FileWriter (logFile);
		logFileBWriter = new BufferedWriter(logFileWriter);
*/		
		
		File repeatedLogFile = new File (logFileFolderPath+"/Log_Files/Repeated_xrefs_Log_File.text");
		FileWriter repatelogFileWriter = new FileWriter (repeatedLogFile);
		repatelogFileBWriter = new BufferedWriter(repatelogFileWriter);
		
		
		File noFoundLogFile = new File (logFileFolderPath+"/Log_Files/Not_Found_xrefs_Log_File.text");
	    FileWriter noFoundlogFileWriter = new FileWriter (noFoundLogFile);
		noFoundlogFileBWriter = new BufferedWriter(noFoundlogFileWriter);
		  
	}
	
	public static void writeLogFileRepeated (String RepeatedlogDtat) throws IOException{
		

		
		repatelogFileBWriter.write(RepeatedlogDtat);
		repatelogFileBWriter.newLine();
		repatelogFileBWriter.flush();
		
	}
	
public static void writeLogFileNoFound (String FileNoFoundlogDtat) throws IOException{
	    
		
		noFoundlogFileBWriter.write(FileNoFoundlogDtat);
		noFoundlogFileBWriter.newLine();
		noFoundlogFileBWriter.flush();
		
	}
    
    }