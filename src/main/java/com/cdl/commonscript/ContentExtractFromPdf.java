package com.cdl.commonscript;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;

import com.cdl.util.Utilities;


public class ContentExtractFromPdf extends Utilities{
  
	public static void main(String args[]) throws IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, SAXException {
    	xmlWrite("Any" , Thread.currentThread().getStackTrace()[1].getClassName(), "PDF Folder" , "null" , "30/12/2016", "Saurabh");
		try {
			System.out.println("Enter Path of PDF Folder= > ");
    		INPUT_FOLDER_PATH = input.nextLine();
    	//	filesInDirectories(directoryPath, requiredFileExtension, recursive, ignoureFileExtension, onlydDirectoryPathOrAllFiles, placeHolder1, placeHolder2)
    		FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".pdf","Yes","No","All","NULL","NULL");
    		if(FILE_LIST.get(0).size()==0){
    			System.out.println("There is no PDF file");
    			System.exit(0);
    		}
    		for(int i=0 ; i < FILE_LIST.get(0).size() ; i++){
    			String data = textExtractionFromPdf(FILE_LIST.get(0).get(i));
    			writeText(FILE_LIST.get(4).get(i)+"\\"+FILE_LIST.get(1).get(i).split("\\.")[0]+".xml", data);
    		}
	    	} catch (Exception e) {
	    		e.printStackTrace();
	        }
		successfullMessage();
		}
	
}