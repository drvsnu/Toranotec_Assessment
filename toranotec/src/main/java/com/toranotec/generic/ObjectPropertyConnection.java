package com.toranotec.generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class ObjectPropertyConnection {
	public Properties getObjectRepository(String propFileName) throws Exception
    {
        //Read object repository file

    	File file = new File(System.getProperty("user.dir")+"/ConfigFiles//" + propFileName);
  	 
    	FileInputStream fileInput = null;
    	try {
    		fileInput = new FileInputStream(file);
    	   }
    	catch (FileNotFoundException e)
    	{
    		e.printStackTrace();
    	}
    	Properties prop = new Properties();
    	
    	//load properties file
    	try {
    		prop.load(fileInput);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	   	return prop;
	}
}
