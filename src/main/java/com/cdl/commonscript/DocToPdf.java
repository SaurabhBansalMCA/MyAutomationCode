package com.cdl.commonscript;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.wiztools.xsdgen.ParseException;
import org.xml.sax.SAXException;
import com.cdl.util.Utilities;


    public class DocToPdf extends Utilities
    { 
    	
        public static void main(String args[]) throws IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, SAXException, ParseException { 
        	xmlWrite("Any" , Thread.currentThread().getStackTrace()[1].getClassName(), "Doc Folder" , "It will create the PDF of documents.	" , "28/12/2016", "Saurabh Bansal");
        	String command = "";
        	String folderName = "";
        	System.out.println("Enter Path of Doc Folder= > ");
    		INPUT_FOLDER_PATH = input.nextLine();
    		FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".docx","Yes","No","All","NULL","NULL");
    		String officePath = "\\"+"\\192.168.100.20\\scripts\\CDL_Checks\\"+OFFICETOPDF_PATH;
    		if(FILE_LIST.get(0).size()==0){
    			System.out.println("There is no document file");
    			System.exit(0);
    		}
    		for(int i=0 ; i < FILE_LIST.get(0).size() ; i++){
    			System.out.println("ss " + FILE_LIST.get(0).get(i));
				System.out.println(FILE_LIST.get(4).size());
				folderName = FILE_LIST.get(4).get(i);
				System.out.println(FILE_LIST.get(4).get(i));
    			folderName = folderName.replace('\'', '\\');
    			folderName = folderName.substring(folderName.lastIndexOf("\\") + 1);
            	if(folderCreation(FILE_LIST.get(4).get(i), folderName+"_PDF")){}
            	command=officePath+" " + "\"" + FILE_LIST.get(0).get(i) + "\"" + " " + "\"" 
            									+OUTPUT_FOLDER_PATH+"\\"+FILE_LIST.get(1).get(i).split("\\.")[0]+".pdf\"";
            	windowCommandRun(command);
    		}
    		successfullMessage();
    		System.exit(0);
        }
    }
    
