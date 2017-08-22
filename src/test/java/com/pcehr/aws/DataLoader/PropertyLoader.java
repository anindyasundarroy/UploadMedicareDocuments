package com.pcehr.aws.DataLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertyLoader {
	
	private final ArrayList<String> propertyList=new ArrayList<String>();
	
	public ArrayList<String> getProperty(String environment) throws IOException{
	
		if(environment.equalsIgnoreCase("PTF")){
			InputStream inputStream=new FileInputStream(new File("src\\test\\java\\com\\pcehr\\resources\\PropertyFiles\\ptf.properties"));
			Properties prop=new Properties();
			if (inputStream != null) {
				prop.load(inputStream);
			}
			propertyList.add(prop.getProperty("clinicalResourceGatewayBase"));
			propertyList.add(prop.getProperty("hpio"));
			propertyList.add(prop.getProperty("userID"));
		}
		else if(environment.equalsIgnoreCase("PTE")){
			InputStream inputStream=new FileInputStream(new File("src\\test\\java\\com\\pcehr\\resources\\PropertyFiles\\pte.properties"));
			Properties prop=new Properties();
			if (inputStream != null) {
				prop.load(inputStream);
			}
			propertyList.add(prop.getProperty("clinicalResourceGatewayBase"));
			propertyList.add(prop.getProperty("hpio"));
			propertyList.add(prop.getProperty("userID"));			
		}
		else{
			InputStream inputStream=new FileInputStream(new File("src\\test\\java\\com\\pcehr\\resources\\PropertyFiles\\ptd.properties"));
			Properties prop=new Properties();
			if (inputStream != null) {
				prop.load(inputStream);
			}
			propertyList.add(prop.getProperty("clinicalResourceGatewayBase"));
			propertyList.add(prop.getProperty("hpio"));
			propertyList.add(prop.getProperty("userID"));			
		}
		
		return propertyList;

	}
}
