package com.cdl.rough;

import java.awt.Desktop;
import java.net.URI; //Note this is URI, not URL

public class URLLaunch{
    public static void main(String args[]) throws Exception{
        // Create Desktop object
        Desktop d=Desktop.getDesktop();

        d.browse(new URI("http://google.com"));

        }
    }
