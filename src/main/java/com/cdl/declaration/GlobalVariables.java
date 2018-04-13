
package com.cdl.declaration;

import com.cdl.util.TableBuilderA;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Timer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.util.Arrays;

public class GlobalVariables {
    public static String ARGUMENT = "";
    static {
        String slash = "";
        if(System.getProperty("os.name").contains("Windows"))
            slash =  "\\";
        if(System.getProperty("os.name").contains("Linux"))
            slash = "/";
        ARGUMENT = slash;
    }

    public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static Timer timer = new Timer();
    public static final Logger APPLICATION_LOG = Logger.getLogger("devpinoyLogger");
    public static String link = "https://docs.google.com/spreadsheets/d/e/2PACX-1vQ05T5eGAV5XCRcyQNOvG2I2nrAB3HCsvSO1VpCDjazEOIP9wKyXJ0aShqphmqQ9DmoJ_NA8xRdqMze/pubhtml?gid=1923611235&single=true";
    public static ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String XML_PATH = "CDLAUTOMATOR_DATABASE.xml";
    public static final String XSD_PATH = "CDLAUTOMATOR_DATABASE.xsd";
    public static final String CSV_PATH = "Assesment Handler - Handler.csv";
    public static final String CSV_REP_PATH = "Assesment_Replaces - Data.csv";
    public static final String PROPERTYFILE_PATH = "input.properties";
    public static final String OFFICETOPDF_PATH = "OfficeToPDF.exe";
    public static String GECKODRIVER_PATH = "geckodriver.exe";
    public static final String RANGE = "TicketsInPipeline!A:B";
    public static InputStream xml_is = classloader.getResourceAsStream(XML_PATH);
    public static InputStream gecko_is = classloader.getResourceAsStream(GECKODRIVER_PATH);
    public static InputStream xsd_is = classloader.getResourceAsStream(XSD_PATH);
    public static InputStream csv_is = classloader.getResourceAsStream(CSV_PATH);
    public static InputStream csv_rep_is = classloader.getResourceAsStream(CSV_REP_PATH);
    public static InputStream prop_is = classloader.getResourceAsStream(PROPERTYFILE_PATH);
    
    public static final String SUCCESS_MESSAGE = "SCRIPT HAS BEEN EXECUTED SUCCESSFULLY";
    public static final String SUCCESS_FOLDER_CREATION = "Directory is created SUCCESSFULLY";
    public static final String ALREADY_FOLDER = "Please Delete the Existing Folder";
    public static final String SHEET_NAME = "Database";
    public static final String WELCOME_MESSGAE = "**********WELCOME TO THE CDL SCRIPT HUB*********";
    public static final String ERROR_MESSGAE = "PLEASE PASS THE ARGUMENT --help TO RUN SPECIFIC SCRIPT";
    public static final String HELP_MESSAGE = "You can visit the below link for more details: ...... ";
    public static final String LINK = "";
    public static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    public static final java.io.File DATA_STORE_DIR = new java.io.File(PROJECT_PATH+ARGUMENT+"src"+ARGUMENT+"main"+ARGUMENT+"java"+ARGUMENT+"com"+ARGUMENT+"cdl"+ARGUMENT+"config");
    public static final String spreadsheetId = "1XkHiSF4NgQBjr6jQBgEYssQherrqrdej3-U9X7bf8Zs";
    public static final String sheetRange = "Database!A:Z";
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    public static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS);
    public static final Properties PROP = new Properties();

    public static TableBuilderA TBA = new TableBuilderA();
    public static DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    public static DocumentBuilder dBuilder;
    public static Document doc;
    public static XPathFactory xPathfactory = XPathFactory.newInstance();
    public static XPath xpath;
    public static NodeList nl;
    public static Node NODE;
    public static TransformerFactory transformerFactory = TransformerFactory.newInstance();
    public static Transformer transformer;
    public static DOMSource source;
    
    public static List<ArrayList<String>> OUTER_FILE_LIST = new ArrayList<ArrayList<String>>();
    public static List<ArrayList<String>> FILE_LIST = new ArrayList<ArrayList<String>>();
    public static List<ArrayList<String>> outerList = new ArrayList<ArrayList<String>>();
    public static ArrayList<String> INPUT_FILE_NAMES = new ArrayList<String>();
    public static ArrayList<String> INPUT_FILE_EXTENSIONS = new ArrayList<String>();
    public static ArrayList<String> INPUT_FILE_PATHS = new ArrayList<String>();
    public static ArrayList<String> INPUT_FILE_RELATIVEPATHS = new ArrayList<String>();
    public static ArrayList<String> ALL_FILE_PATHS = new ArrayList<String>();
    public static ArrayList<String> ALL_FILE_NAMES = new ArrayList<String>();
    public static ArrayList<String> ALL_FILE_RELATIVEPATHS = new ArrayList<String>();
    public static ArrayList<String> FILE_EXTENSION = new ArrayList<String>();
    public static ArrayList<String> DIRECTORY_PATH = new ArrayList<String>();
    public static ArrayList<String> UNIQUE_NUMBER = new ArrayList<String>();
    public static Hashtable<String, String> csvData = new Hashtable<String, String>();
    public static Hashtable<String, String> REPLACES = new Hashtable<String, String>();
    public static List<String> PARAGRAPHS = new ArrayList<String>();
	public static Hashtable<String,String> figureList = new Hashtable<String,String>();

	public static String logFileFolderPath; 
	public static File logFile;
	
	public static BufferedWriter logFileBWriter;
	public static BufferedWriter repatelogFileBWriter;
	public static BufferedWriter noFoundlogFileBWriter;

	
    public static FileInputStream FIS = null;
    public static FileOutputStream FOS = null;
    public static BufferedInputStream BIS = null;
    public static BufferedOutputStream BOS = null;
    public static File FILE = null;
    public static FileWriter FW = null;
    public static FileReader FR = null;
    public static BufferedReader BR = null;
    public static BufferedWriter BW = null;
    public static Scanner input = new Scanner(System.in);
    public static FileDataStoreFactory DATA_STORE_FACTORY;
    public static HttpTransport HTTP_TRANSPORT;
    public static Sheets service;
    public static ValueRange response;

    public static String INPUT_FOLDER_PATH = "";
    public static String OUTPUT_FOLDER_PATH = "";
    public static String FILE_NAMES = "";
    public static String HELP = "";
}
