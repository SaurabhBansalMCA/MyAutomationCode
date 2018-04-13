package com.cdl.geyser;


import java.io.File;
import java.util.List;
import java.util.Scanner;

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
    
    public static void main(String[] args) {
        System.out.println("Enter the path of Source XML folder.....");
        Scanner scan = new Scanner(System.in);
        xmlLocation = scan.nextLine().concat("/");        
        System.out.println("Enter the path of Help XML folder.....");
        helpLocation = scan.nextLine().concat("/");
        System.out.println("Enter the path of Log FILES folder.....");
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
                	System.out.println("Find Different File Extension: "+file.getName());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally{
            System.out.println("************************************\n");
            System.out.println("HURREYYYYYYY! Key Term has been Fixed....\n"
                    + "Check the Fixed file in same Directory.....\n"
                    + "Check the Duplicate and Miss terms in '.txt' files....");
            System.out.println("\n************************************");
        }
    }
}