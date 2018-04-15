package com.cdl.assesment;


import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Set;

import javax.swing.text.BadLocationException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.wiztools.xsdgen.ParseException;
import org.xml.sax.SAXException;

import com.cdl.util.Utilities;

public class QuestionCount extends Utilities{
	static int count = 0;
	static int narrative = 0;
	static Hashtable<String,Integer> hs = new Hashtable<String,Integer>();
	
	public static void main(String[] args) throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, SAXException, IOException, BadLocationException, ParseException {
    	try {
			xmlWrite("Assesment" , Thread.currentThread().getStackTrace()[1].getClassName(), "Docx Folder" , "It will count the questions in the document." , "30/12/2016", "Saurabh");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	hs.clear();
    	csvData = CSV_Reader(CSV_PATH, ",",csv_is);
      	Set<String> keys = csvData.keySet();
		print("Enter Path of Docx Folder= > ");
		INPUT_FOLDER_PATH = input.nextLine();
		print("Enter Path of Destination Folder of Document= > ");
		String DESTINATION_FOLDER_PATH = input.nextLine();
		FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".docx,.doc,.rtf","Yes","No","All","Null","Null");
		if(FILE_LIST.get(0).size()==0)
			print("There is no document file");
		for(int temp = 0 ; temp < FILE_LIST.get(0).size() ; temp++){
			if(FILE_LIST.get(0).get(temp).endsWith(".docx"))
				PARAGRAPHS = readDocxFile(FILE_LIST.get(0).get(temp));
			if(FILE_LIST.get(0).get(temp).endsWith(".rtf"))
				PARAGRAPHS = readRtfFile(FILE_LIST.get(0).get(temp));
			if(FILE_LIST.get(0).get(temp).endsWith(".doc"))
				PARAGRAPHS = readDocFile(FILE_LIST.get(0).get(temp));	
			for(String key: keys){
				for(String para : PARAGRAPHS){
					if(para.contains("”"))
					 para = para.replace("”","");
					if(para.contains("\""))
						 para = para.replace("\"","");
					if(para.contains("”"))
						 para = para.replace("”","");
					
					if(para.contains("’"))
						 para = para.replace("’","");
					if(para.contains("\""))
						 para = para.replace("\"","");
					if(para.contains("“"))
					 para = para.replace("“", "");
					if(para.contains(" <"))
						para = para.replace(" <","<");
					para = para.replace("\">", "\">\n");
					para = para.replace("\"", "");
					para = para.replace(" ","");
					key = key.replace("\"", "");
						if(para.contains(key)){
							count++;
							hs.put(key, count);
						}
					}
				count = 0;
				}
			
			Set<String> data = hs.keySet();
			for(String d : data){
				try {
					setValue(systemDate(),FILE_LIST.get(0).get(temp), d, hs.get(d));
					FILE.renameTo(new File(DESTINATION_FOLDER_PATH+"//"+FILE.getName()));
				} catch (IOException e) {
					System.out.println(e.getMessage());
					
				}
			}
			hs.clear();

				
			}
		
		setValue(systemDate(),"Done","Done",-1);
		successfullMessage();
		System.exit(0);
		}
}
