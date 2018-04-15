package com.cdl.geyser;

import com.cdl.util.Utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UniqueCGI extends Utilities{
	static ArrayList<String> CGI = new ArrayList<String>();
	static Set<String> unique = new HashSet<String>();
		public static void main(String[] args) throws Exception {
        xmlWrite("CENDOC" , Thread.currentThread().getStackTrace()[1].getClassName(), "XML Folder" , "It will find the duplicate identifiers in XML." , "28/12/2016", "Saurabh Bansal");
		System.out.println("Enter Path of XML Folder= > ");
		INPUT_FOLDER_PATH = input.nextLine();
		FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".xml","Yes","No","All","Null","Null");
		if(FILE_LIST.get(0).size()==0){
			System.out.println("There is no xml file");
			System.exit(0);
		}
		if(!folderCreation(INPUT_FOLDER_PATH,"DupleCGI")){
			System.out.println(ALREADY_FOLDER);
			System.exit(0);
		}
		for (int i = 0 ; i <FILE_LIST.get(0).size() ; i++) {
		    	  dBuilder = dbFactory.newDocumentBuilder();
		    	  doc = dBuilder.parse(FILE_LIST.get(0).get(i));
		    	  xpath = xPathfactory.newXPath();
		    	  XPathExpression expr = xpath.compile("//*[@identifier]");
		    	  nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		    	  int temp = 0;
		    	  writeText(OUTPUT_FOLDER_PATH+"/DuplicateCGI.txt", FILE_LIST.get(0).get(i));
		    	  while (temp < nl.getLength()) {
		    		  Node currentItem = nl.item(temp);
		    		  String key = currentItem.getAttributes().getNamedItem("identifier").getNodeValue();
		    		  CGI.add(key);
		    		  if (!unique.add(key)) {
		    			  writeText(OUTPUT_FOLDER_PATH+"/DuplicateCGI.txt", key);
		    		  }
		    		  ++temp;
		    	  }
		    	  CGI.clear();
		    	  unique.clear();
		      }
		successfullMessage();
		System.exit(0);
		}
	}