package com.cdl.geyser;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.wiztools.xsdgen.ParseException;
import org.xml.sax.SAXException;

import com.cdl.util.Utilities;

public class KeyTermFixer extends Utilities{
    
    public static String fileName = null;
    public static String xmlLocation = null;
    public static String helpLocation = null;
    public static String logFileLocation = null;
    public static File txtLogFile = null;
    public static File txtNotFoundFile = null;
    public static File txtFixedTerms = null;
    public static String duplicate = "/Duplicate_Log.txt";
    public static String notFound = "/NotFound_Log.txt";
    public static String fixedTerms = "/FixedKeyTerms_Log.txt";
    
    public static void main(String[] args) throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, SAXException, IOException, ParseException {
    	xmlWrite("CENDOC" , Thread.currentThread().getStackTrace()[1].getClassName(), "XML Folder" , "It will fix the key terms definations." , "13/04/2018", "Sanjai Singh");

        print("Enter the path of Source XML folder= > ");
        Scanner scan = new Scanner(System.in);
        xmlLocation = scan.nextLine().concat("/");        
        print("Enter the path of Help XML folder= > ");
        helpLocation = scan.nextLine().concat("/");
        print("Enter the path of Log FILES folder= > ");
        logFileLocation = scan.nextLine().concat("/LOG FILES");
        scan.close();

        new File(logFileLocation).mkdir();
        
        txtLogFile = new File(logFileLocation.concat(duplicate));
        txtNotFoundFile = new File(logFileLocation.concat(notFound));
        txtFixedTerms = new File(logFileLocation.concat(fixedTerms));
        
        try {
            if (txtLogFile.delete() && txtNotFoundFile.delete() && txtFixedTerms.delete()) {
                txtLogFile.createNewFile();
                txtNotFoundFile.createNewFile();
                txtFixedTerms.createNewFile();
            }
            else{
                txtLogFile.createNewFile();
                txtNotFoundFile.createNewFile();
                txtFixedTerms.createNewFile();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        try {
            getKeytermList(helpLocation.concat("keyterm_help.xml"));
            List<File> files = getFileNames(xmlLocation);
            for(File file : files)
            {
                if(file.getName().endsWith(".xml"))
                    copyXMLFile(file.getParent(), file.getName());
                else
                	print("Find Different File Extension: "+file.getName());
            }
        } catch (Exception e) {
        	print(e.getMessage());
            e.printStackTrace();
        }
        successfullMessage();
        System.exit(0);
    }
}