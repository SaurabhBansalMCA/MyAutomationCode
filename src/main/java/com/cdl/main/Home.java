package com.cdl.main;

import com.cdl.util.Utilities;

public class Home extends Utilities {
	
	public static void main(String[] args) {
		
		try{
		HELP = args[0];
		try{
			System.out.println();
			print("CDL Automator HUB Help....");
			System.out.println("");
			readDataXML();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		}catch(Exception e){
			System.out.println("\n");
			System.out.println(WELCOME_MESSGAE);
			System.out.println("\n");
			System.out.println(ERROR_MESSGAE + " " + HELP_MESSAGE);
			System.out.println("\n");
			System.out.println("https://docs.google.com/spreadsheets/d/e/2PACX-1vQutHZ9PFzViG09bNA4xcjBDOJzMZNqMJ3l3A9pZUBy81-SA6XDE835BW3lmn5jwCmP09KZGVS5Nv7B/pubhtml?gid=0&single=true");
			}
		System.exit(0);
		}
}
