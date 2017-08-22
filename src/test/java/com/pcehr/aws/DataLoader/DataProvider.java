package com.pcehr.aws.DataLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DataProvider {
	private HSSFWorkbook book;
	private HSSFSheet sheet;
	private HSSFRow row;
	
	Map<String,String> dataParameters=new LinkedHashMap<String,String>();
	
	public Map<String,String> retrieveDataFromInputSheet(String fileName) throws IOException{
		int i=0;
		FileInputStream fileStream=new FileInputStream(new File("src\\main\\Resources\\InputData.xls"));
		book=new HSSFWorkbook(fileStream);
		sheet = book.getSheet(fileName);
		
		Iterator rows=sheet.rowIterator();
		
		while(rows.hasNext()){
			row=(HSSFRow) rows.next();
				if( i> 0){
				//System.out.println(cell.getStringCellValue());
				dataParameters.put(row.getCell(0).toString(),row.getCell(1).toString());
				
				}
				i++;
		}
		return dataParameters;
	}

}
