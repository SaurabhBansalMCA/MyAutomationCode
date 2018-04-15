package com.cdl.commonscript;

import java.io.IOException;
import java.util.zip.ZipException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.wiztools.xsdgen.ParseException;
import org.xml.sax.SAXException;

import com.cdl.util.Utilities;


public class UnzipFolder extends Utilities{

	public static void main(String[] args) throws ZipException, IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, SAXException, ParseException {
    	xmlWrite("Any" , Thread.currentThread().getStackTrace()[1].getClassName(), "Zipped Folder" , "It will extract the zipped folder." , "28/12/2016", "Saurabh Bansal");
		System.out.println("Enter Path of Zipped Folder= > ");
		INPUT_FOLDER_PATH = input.nextLine();
		FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".zip,.7z","Yes","No","All","Null","Null");
		if(FILE_LIST.get(0).size()==0)
			print("There is no zipped file");
		for(int i = 0 ; i <FILE_LIST.get(0).size() ; i++)
			extractFolder(FILE_LIST.get(0).get(i));
		successfullMessage();
		System.exit(0);
	}

}
