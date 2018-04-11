package com.cdl.assesment;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Set;

import javax.swing.text.BadLocationException;

import com.cdl.util.Utilities;

public class AssesmentTracker extends Utilities{
	static int count = 0;
	static int narrative = 0;
	static Hashtable<String,Integer> hs = new Hashtable<String,Integer>();

	public static void main(String[] args) throws IOException, InterruptedException, BadLocationException {
    	String command = "";
    	String folderName = "";
    	hs.clear();
    	csvData = CSV_Reader(CSV_PATH, ",",csv_is);
      	Set<String> keys = csvData.keySet();
      	String DESTINATION_FOLDER_PATH = "C:/Users/cdluser/Desktop/Script/Junk/";
      	//String DESTINATION_FOLDER_PATH = "C:\\Users\\cdladmin\\Desktop\\ddddd";
		INPUT_FOLDER_PATH = "C:/Users/cdluser/Desktop/Script/Tickets";
      	//INPUT_FOLDER_PATH = "C:\\Users\\cdladmin\\Desktop\\ffffffffffffffff";
		String officePath = "\\"+"\\192.168.100.20\\scripts\\CDL_Checks\\"+OFFICETOPDF_PATH;
		//String officePath = "C:\\Users\\cdladmin\\Desktop\\q\\cdl-automator\\target\\classes\\"+OFFICETOPDF_PATH;
		
		//Unzip the zip folder
		
		APPLICATION_LOG.debug("SCRIPT STARTED");
		APPLICATION_LOG.debug("* * * * * * ");
		FILE_LIST = null;
		FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".zip,.7z","Yes","No","All","NULL","NULL");
		if(FILE_LIST.get(0).size()==0)
			System.out.println("There is no zip file");
		else{
			for(int i = 0 ; i < FILE_LIST.get(0).size() ; i++){
				extractFolder(FILE_LIST.get(0).get(i));
				APPLICATION_LOG.debug("Unzip the folder " + FILE_LIST.get(0).get(i));
				}
			sendMail("Assesment tracker script is running total tickets : " + FILE_LIST.get(0).size() , "Thank You!" , "No" , "No");
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
		FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".docx","Yes","No","All","NULL","NULL");
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
		
		//Thread.sleep(2000);
		
		//PDF convert to XML
		
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
			System.out.println("There is no PDF file");
		else{
			for(int i=0 ; i < FILE_LIST.get(0).size() ; i++){
				folderName = FILE_LIST.get(4).get(i);
				folderName = folderName.replace('\'', '\\');
				folderName = folderName.substring(folderName.lastIndexOf("\\") + 1);
				if(folderCreation(FILE_LIST.get(4).get(i), folderName+"_XML")){}
				String data = textExtractionFromPdf(FILE_LIST.get(0).get(i));
				writeText(OUTPUT_FOLDER_PATH+"\\"+FILE_LIST.get(1).get(i).split("\\.")[0]+".xml", data);
				APPLICATION_LOG.debug(OUTPUT_FOLDER_PATH+"\\"+FILE_LIST.get(1).get(i).split("\\.")[0]+".xml");
				}
			APPLICATION_LOG.debug("Total PDF Folder = " + FILE_LIST.get(0).size());
			APPLICATION_LOG.debug("* * * * * * ");
		}
		
		//Write question count in google spredsheet
		
		APPLICATION_LOG.debug("WRITTING QUESTION COUNT IN GOOGLE SHEET");
		FILE_LIST = null;
		FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".docx,.doc,.rtf","Yes","No","All","NULL","NULL");
		if(FILE_LIST.get(0).size()==0)
			//APPLICATION_LOG.debug("There is no document file");
			System.out.println("There is no document file");
		else{
			for(int i = 0 ; i < FILE_LIST.get(0).size() ; i++){
				//System.out.println("Total documents = " + FILE_LIST.get(0).size());
        		if(FILE_LIST.get(0).get(i).endsWith(".docx"))
    				PARAGRAPHS = readDocxFile(FILE_LIST.get(0).get(i));
        		if(FILE_LIST.get(0).get(i).endsWith(".rtf"))
    				PARAGRAPHS = readRtfFile(FILE_LIST.get(0).get(i));
    			if(FILE_LIST.get(0).get(i).endsWith(".doc"))
    				PARAGRAPHS = readDocFile(FILE_LIST.get(0).get(i));	
    			for(String key: keys){
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
    					key = key.replace("\"", "");
    						if(para.contains(key)){
    							count++;
    							hs.put(key, count);
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
        	}

	

}
