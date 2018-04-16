package com.cdl.geyser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.wiztools.xsdgen.ParseException;
import org.xml.sax.SAXException;

import com.cdl.util.Utilities;

public class T2Image extends Utilities{

	public static void main(String[] args) throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, SAXException, IOException, ParseException {
    	xmlWrite("CENDOC" , Thread.currentThread().getStackTrace()[1].getClassName(), "Image Folder" , "It will create the T-2 image of any T-3 image." , "15/04/2018", "Arun Kumar");


	}

}
