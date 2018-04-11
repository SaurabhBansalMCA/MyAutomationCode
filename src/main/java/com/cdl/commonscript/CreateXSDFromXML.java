package com.cdl.commonscript;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;

import com.cdl.util.Utilities;


public class CreateXSDFromXML extends Utilities{

	public static void main(String[] args) throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, SAXException, IOException {
		//xmlWrite("Any" , Thread.currentThread().getStackTrace()[1].getClassName(), "XML Folder" , "null" , "30/12/2016", "Saurabh");
    	String command = "";
    	System.out.println("Enter Path of XML Folder= > ");
		INPUT_FOLDER_PATH = input.nextLine();
		//filesInDirectories(directoryPath, requiredFileExtension, recursive, ignoureFileExtension, onlydDirectoryPathOrAllFiles, placeHolder1, placeHolder2)
		FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".xml","Yes","No","All","NULL","NULL");
		//System.out.println(FILE_LIST.get(0).size());
		if(FILE_LIST.get(0).size()==0){
			System.out.println("There is no xml file");
			System.exit(0);
		}
		for(int i=0 ; i < FILE_LIST.get(0).size() ; i++){
			command = "java -jar "+System.getProperty("user.dir")+"\\src\\main\\java\\com\\cdl\\config\\trang.jar " +
						FILE_LIST.get(0).get(i) + " " + FILE_LIST.get(4).get(i)+"\\"+FILE_LIST.get(1).get(i).split("\\.")[0]+".xsd";
			//command = java -jar D:/trang.jar Sample.xml Sample.xsd
			windowCommandRun(command);
			print(command);
		}
		successfullMessage();
		
	}

}
