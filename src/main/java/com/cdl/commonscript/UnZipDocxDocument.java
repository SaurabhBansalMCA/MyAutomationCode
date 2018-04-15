package com.cdl.commonscript;

import java.io.IOException;
import java.util.zip.ZipException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.wiztools.xsdgen.ParseException;
import org.xml.sax.SAXException;

import com.cdl.util.Utilities;

public class UnZipDocxDocument extends Utilities{

	public static void main(String[] args) throws ZipException, IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, SAXException, ParseException {
    	xmlWrite("Any" , Thread.currentThread().getStackTrace()[1].getClassName(), "Docx Folder" , "It will extract the docx file into XML." , "18/01/2017", "Saurabh Bansal");
		System.out.println("Enter Path of Docx Files Folder= > ");
		INPUT_FOLDER_PATH = input.nextLine();
		FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".docx","Yes","No","All","NULL","NULL");
		if(FILE_LIST.get(0).size()==0){
			System.out.println("There is no docx file");
			System.exit(0);
		}
		for(int i=0 ; i < FILE_LIST.get(0).size() ; i++){
			unZipDocx(FILE_LIST.get(0).get(i),FILE_LIST.get(4).get(i)+"\\"+FILE_LIST.get(1).get(i).split(".docx")[0]+"_XML");
		}
		successfullMessage();
		System.exit(0);
	}
}

