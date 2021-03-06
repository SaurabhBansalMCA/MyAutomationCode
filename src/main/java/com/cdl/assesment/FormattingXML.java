package com.cdl.assesment;



import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cdl.util.NamespaceResolver;
import com.cdl.util.Utilities;

public class FormattingXML extends Utilities{
		static Node currentItem;
		public static void main(String[] args) throws Exception {
	        xmlWrite("Assesment" , Thread.currentThread().getStackTrace()[1].getClassName(), "XML Folder" , "It will fix the italic-bold in xml." , "17/01/2017", "Saurabh Bansal");
			propertyFile();
			System.out.println("Enter Path of XML Folder= > ");
			INPUT_FOLDER_PATH = input.nextLine();
			FILE_LIST = filesInDirectories(INPUT_FOLDER_PATH,".xml","Yes","No","All","Null","Null");
			if(FILE_LIST.get(0).size()==0){
				System.out.println("There is no xml file");
				System.exit(0);
			}
			dbFactory.setNamespaceAware(true);
	    	dBuilder = dbFactory.newDocumentBuilder();
			for (int i = 0 ; i <FILE_LIST.get(0).size() ; i++) {
				if(FILE_LIST.get(0).get(i).endsWith("document.xml"))
				{
					System.out.println(FILE_LIST.get(0).get(i));
				doc = dBuilder.parse(FILE_LIST.get(0).get(i));
				//System.out.println("s " + ORPROP.keySet());
				for(Object PropKey : ORPROP.keySet()){
					xpath = xPathfactory.newXPath();
		    	  xpath.setNamespaceContext(new NamespaceResolver(doc));
		    	 // System.out.println(PropKey.toString());
		    	  XPathExpression expr = xpath.compile(PROP.getProperty(PropKey.toString()));
		    	  if(expr != null){ 
		    	  NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		    	  int temp = 0;
	
		    	  while (temp < nl.getLength()) {
		    		  currentItem = nl.item(temp);
		    		  String key = currentItem.getParentNode().getParentNode().getLastChild().getTextContent();
		    		  Element el = doc.createElement(PropKey.toString());
		    		  if(currentItem.getNextSibling().toString().contains("w:i:"))
		    			  el = doc.createElement("bold-italic");
		    			  
		    		 // System.out.println("n"+currentItem.getNextSibling().toString());
		    		//  System.out.println("p"+currentItem.getPreviousSibling());
		    		 // System.out.println("curct " + currentItem);
		    		  //Extract content of italic/bold/underline in key string
		    		  
		    		  
		    	//	  System.out.println("xxxx"+currentItem.getParentNode().getParentNode().getLastChild());
		    		  Element remove = (Element) currentItem.getParentNode().getParentNode().getLastChild();
		    		// System.out.println(key);
		    		 //System.out.println(remove);

		    			  
		    			  currentItem.getParentNode().getParentNode().replaceChild(el, remove);
		    			  currentItem.getParentNode().getParentNode().getLastChild().setTextContent(key);
		    			//  currentItem.getParentNode().getParentNode().appendChild(el).setTextContent(key);
		    			//  System.out.println("s"+currentItem.getParentNode().getNextSibling().getNodeName());
				        //  currentItem.getParentNode().getParentNode().removeChild(currentItem.getParentNode().getParentNode().getFirstChild().getNextSibling());

		    		  	  ++temp;
		    	  }
		//    	  System.out.println("ssss");
		    	  }
		    	  }
		    	  
				DOMSource source = new DOMSource(doc);
			    TransformerFactory transformerFactory = TransformerFactory.newInstance();
			    Transformer transformer = transformerFactory.newTransformer();
			    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");
			    System.out.println(FILE_LIST.get(4).get(i));
		          StreamResult result = new StreamResult(FILE_LIST.get(4).get(i)+"\\geyser.xml");
		          transformer.transform(source, result);
			}}
		successfullMessage();
		System.exit(0);
		}
	}