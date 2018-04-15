package com.cdl.commonscript;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.wiztools.xsdgen.ParseException;
import org.wiztools.xsdgen.XsdGen;

import org.xml.sax.SAXException;

import com.cdl.util.Utilities;


public class CreateXSDFromXML extends Utilities{

	public static void main(String[] args) throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, SAXException, IOException, ParseException {
		xmlWrite("Any" , Thread.currentThread().getStackTrace()[1].getClassName(), "XML Folder" , "It will generate the XSD or xml schema of any xml." , "30/12/2016", "Saurabh Bansal");
    	System.out.println("Enter Path of XML Folder= > ");
		INPUT_FOLDER_PATH = input.nextLine();
		FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".xml","Yes","No","All","NULL","NULL");
		if(FILE_LIST.get(0).size()==0){
			System.out.println("There is no xml file");
			System.exit(0);
		}
		for(int i=0 ; i < FILE_LIST.get(0).size() ; i++){
			FILE = new File(FILE_LIST.get(0).get(i));
			String xsd = new XsdGen().parse(FILE).toString();
            writeText(FILE_LIST.get(4).get(i)+"\\"+FILE_LIST.get(1).get(i).split("\\.")[0]+".xsd", xsd);
        
		}
		successfullMessage();
		System.exit(0);
		
	}

}
