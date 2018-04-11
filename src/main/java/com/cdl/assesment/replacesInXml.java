package com.cdl.assesment;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;

import com.cdl.util.Utilities;

public class replacesInXml extends Utilities{
	static String line = "";
    static String oldText = "";
    static String newText = "";
    static ArrayList<String> replaceStringArray = new ArrayList<String>();
	public static void main(String[] args) throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, SAXException, IOException{
    	xmlWrite("Assesment" , Thread.currentThread().getStackTrace()[1].getClassName(), "XML Folder" , "null" , "18/01/2017", "Saurabh");
    	//hs.clear();
    	csvData.clear();
    	csvData = CSV_Reader(CSV_REP_PATH,",",csv_rep_is);
    	Set<String> find = csvData.keySet();
    	System.out.println(csvData.size());
    	print("Enter Path of XML Folder= > ");
		INPUT_FOLDER_PATH = input.nextLine();
		//filesInDirectories(directoryPath, requiredFileExtension, recursive, ignoureFileExtension, onlydDirectoryPathOrAllFiles, placeHolder1, placeHolder2)
		FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".xml","Yes","No","All","Null","Null");
		if(FILE_LIST.get(0).size()==0)
			print("There is no xml file");
		for(int temp = 0 ; temp < FILE_LIST.get(0).size() ; temp++){
			if(FILE_LIST.get(0).get(temp).contains("geyser.xml")){
			System.out.println(FILE_LIST.get(0).get(temp));
			FILE = new File(FILE_LIST.get(0).get(temp));
	        BR = new BufferedReader(new FileReader(FILE));
	        
	        while((line = BR.readLine()) != null)
	            {
	            oldText += line + "\r\n";
	        }
	        BR.close();
	        System.out.println(find.size());
	       for(String findKey: find){
	        	System.out.println("Match Find  = " + findKey);
		
	        String replaceString = csvData.get(findKey);
	       System.out.println("replace = " + replaceString);
	        Pattern pattern = Pattern.compile(findKey);
	        Matcher matcher = pattern.matcher(oldText);

	        oldText = matcher.replaceAll(replaceString);
		}
	       

	      FW= new FileWriter("C:\\Users\\cdladmin\\Desktop\\g\\geyserOutput.xml");
	        FW.write(oldText);


	        FW.close();
		}
	}}
}
