package com.cdl.geyser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Element;
import org.wiztools.xsdgen.ParseException;
import org.xml.sax.SAXException;

import com.cdl.util.Utilities;

public class AssesstsPlacement  extends Utilities{
	public static void main(String argv[]) throws IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException, SAXException, ParseException {
    	xmlWrite("CENDOC" , Thread.currentThread().getStackTrace()[1].getClassName(), "XML Folder" , "It will placed the Figure & Table at their first reference" , "13/04/2018", "Arun Kumar");
		System.out.println("Please Enter Path of Source Chapters= > ");
		Scanner scan1= new Scanner(System.in);
		String location1 = scan1.nextLine();//Chapter location
		String locationOfDuplicateXMLs = location1; //+"/Duplicate_XMLs"
		System.out.println("Please Enter Path of Figure_Help Files= > ");
		String location2 = scan1.nextLine();//Figure_Help location
		System.out.println("Please Enter Path of Log Files, Where do you want= > ");
		String location3LogFile = scan1.nextLine();//Log File Location
		location3LogFile = location3LogFile+"/";
		scan1.close();
		location1 = location1+"/";
		LogFiles(location3LogFile);
		List<File> files = getFileNames(location1);
		for(File file : files) {
			String currentDir = file.getParent();
			String newDirectory = currentDir+"/DuplicateXMLs";
			new File(newDirectory).mkdir();
			figHR(location2);
			Set<String> allkeyss = new HashSet<String>();
			Set<String> remove = new HashSet<String>();
			try {
				String fileNameOgChapterXml = file.getName();
				String fileNameSplit= fileNameOgChapterXml.substring(fileNameOgChapterXml.length()-4, fileNameOgChapterXml.length());
				if(fileNameSplit.equalsIgnoreCase(".xml")){
					String chapterName ="\n================= "+file.getName() +" ================= Path:\n"+file.getAbsolutePath()+"\n";
					writeLogFileRepeated(chapterName);
					writeLogFileNoFound(chapterName);
					dBuilder = dbFactory.newDocumentBuilder();
					doc = dBuilder.parse(file);
					nl = doc.getElementsByTagName("cl:xref");
					int num = nl.getLength();
					allkeyss.addAll(figureList.keySet());
					for(int i = 0; i < nl.getLength() ; i++){
						Element node = (Element) nl.item(i);
						if(allkeyss.contains(node.getAttribute("pre-text").trim()+node.getAttribute("ordinal"))){
							if(remove.add(node.getAttribute("pre-text").trim()+node.getAttribute("ordinal"))){
								String a = (String) figureList.get(node.getAttribute("pre-text").trim()+node.getAttribute("ordinal").toString());
								Element el = doc.createElement("saurabh");
								el.appendChild(doc.createTextNode(a));
								node.getParentNode().appendChild(el);
								}
							else {
								String repateXREFs = "Repeated xrefs in "+file.getName()+" " + node.getAttribute("pre-text")+" ".trim()+node.getAttribute("ordinal");
								writeLogFileRepeated(repateXREFs);
							}
						}
						else {
							String notFoundXREF = "Not Found xrefs in "+file.getName()+" " + node.getAttribute("pre-text")+" ".trim()+node.getAttribute("ordinal");
							writeLogFileNoFound(notFoundXREF);
						}
					}
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					String locationD = location1+"Dup_"+file.getName();
					StreamResult result = new StreamResult(new File(locationD));//"/home/arunkumar/workspace/CenDocFigPlace/Dup_Chapter_8.xml"
					transformer.transform(source, result);
					File finalChapterxml = new File (locationD);
					FileReader f9r = new FileReader(finalChapterxml);
					BufferedReader finalChapterxmlReader = new BufferedReader(f9r);
					String duplicateLocation = newDirectory+"/"+file.getName(); //.replace("_Dup_Chapter.xml", "_Duplicate_Chapter.xml");
					File ffinalXML = new File (duplicateLocation);
					FileWriter finalWriter = new FileWriter (ffinalXML);
					BufferedWriter finalBuffWriter = new BufferedWriter(finalWriter);
					String finalXMLString = finalChapterxmlReader.readLine();//z9 
					while(finalXMLString!=null){ 
						finalBuffWriter.write(StringEscapeUtils.unescapeHtml4(finalXMLString).replace("></cl:", ">\n</cl:").replaceAll("<saurabh><cl:figure", "\n<cl:figure").replaceAll("</cl:figure></saurabh>", "</cl:figure>").replaceAll("<saurabh><cl:table-wrapper", "\n<cl:table-wrapper").replaceAll("</cl:table-wrapper></saurabh>", "</cl:table-wrapper>").replaceAll("><cl:", ">\n<cl:").replaceAll("<cl:ordinal>", "\n<cl:ordinal>"));	
						finalBuffWriter.newLine();
						finalXMLString = finalXMLString = finalChapterxmlReader.readLine();
					}
					finalBuffWriter.flush();
					f9r.close();
					finalChapterxmlReader.close();
					boolean result23 = Files.deleteIfExists(finalChapterxml.toPath());
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		successfullMessage();
		System.exit(0);
		//windowCommandRunJira("exit");
	}
}
