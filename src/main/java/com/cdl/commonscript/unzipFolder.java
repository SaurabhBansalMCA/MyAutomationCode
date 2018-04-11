package com.cdl.commonscript;

import java.io.IOException;
import java.util.zip.ZipException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;

import com.cdl.util.Utilities;


public class unzipFolder extends Utilities{

	public static void main(String[] args) throws ZipException, IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, SAXException {
    	xmlWrite("Any" , Thread.currentThread().getStackTrace()[1].getClassName(), "Zipped Folder" , "null" , "28/12/2016", "Saurabh");
		System.out.println("Enter Path of Zipped Folder= > ");
		INPUT_FOLDER_PATH = input.nextLine();
		FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".zip,.7z","Yes","No","All","Null","Null");
		//System.out.println(FILE_LIST.get(0).size());
		for(int i = 0 ; i <FILE_LIST.get(0).size() ; i++){
			//System.out.println(FILE_LIST.get(0).get(i));
			extractFolder(FILE_LIST.get(0).get(i));
		}
		successfullMessage();

	}

}
