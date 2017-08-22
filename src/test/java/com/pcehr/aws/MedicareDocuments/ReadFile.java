package com.pcehr.aws.MedicareDocuments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

public class ReadFile {
	
	private BufferedReader br;
	private BufferedReader br2;

	public String updatePBSContent(String documentID,String organizationUUID,String medicationUUID,String medicationRequestUUID,Map<String,String> dataParameters,String pbsFileName) throws Exception{
		String line = null;
		String requestBody=null;
		StringBuilder sb=new StringBuilder();
		FileReader fileReader=new FileReader(new File("src\\main\\Resources\\"+pbsFileName+".txt"));
		br = new BufferedReader(fileReader);
		
		while((line = br.readLine()) != null){
//			if(line.contains("$organizationName")){
//				requestBody=sb.append(line.replace("$organizationName", "anindya")).toString();
//			}
//			else if(line.contains("$medicationGenericName")){
//				requestBody=sb.append(line.replace("$medicationGenericName", "anindya")).toString();
//			}
//			else if(line.contains("$medicationBrand")){
//				requestBody=sb.append(line.replace("$medicationBrand", "anindya")).toString();
//			}
//			else if(line.contains("$medicationFromAndStrength")){
//				requestBody=sb.append(line.replace("$medicationFromAndStrength", "anindya")).toString();
//			}
//			else if(line.contains("$authoredOnDate")){
//				requestBody=sb.append(line.replace("$authoredOnDate", "anindya")).toString();
//			}
//			else if(line.contains("$numberOfRepeatsAllowed")){
//				requestBody=sb.append(line.replace("$numberOfRepeatsAllowed", "10")).toString();
//			}
			if(line.contains("$documentId")){
				requestBody=sb.append(line.replace("$documentId", documentID)).toString();
			}
//			else if(line.contains("$createdDate")){
//				requestBody=sb.append(line.replace("$createdDate", "anindya")).toString();
//			}
//			else if(line.contains("$sequence")){
//				requestBody=sb.append(line.replace("$sequence", "1")).toString();
//			}
//			else if(line.contains("$servicedDate")){
//				requestBody=sb.append(line.replace("$servicedDate", "anindya")).toString();
//			}
//			else if(line.contains("$quantityValue")){
//				requestBody=sb.append(line.replace("$quantityValue", "6")).toString();
//			}
			else if(line.contains("$organizationID")){
				requestBody=sb.append(line.replace("$organizationID", organizationUUID)).toString();
			}
			else if(line.contains("$medicationID")){
				requestBody=sb.append(line.replace("$medicationID", medicationUUID)).toString();
			}
			else if(line.contains("$medicationRequestID")){
				requestBody=sb.append(line.replace("$medicationRequestID", medicationRequestUUID)).toString();
			}
			for(Map.Entry<String, String> key:dataParameters.entrySet()){
				if(line.contains(key.getKey())){
					requestBody=sb.append(line.replace(key.getKey(), key.getValue())).toString();
					break;
				}
			}
			if(!line.contains("$")){
				requestBody=sb.append(line).toString();
			}
		}
		return requestBody;
	}
	
	public String updateMBSContent(String documentID,String organizationUUID,String medicationUUID,String medicationRequestUUID,Map<String,String> dataParameters, String fileName) throws Exception{
		String line = null;
		String requestBody=null;
		StringBuilder sb=new StringBuilder();
		FileReader fileReader=new FileReader(new File("src\\main\\Resources\\"+fileName+".txt"));
		br2 = new BufferedReader(fileReader);
		
		while((line = br2.readLine()) != null){
			
			if(line.contains("$documentId")){
				requestBody=sb.append(line.replace("$documentId", documentID)).toString();
			}
			if(line.contains("$organizationID")){
				requestBody=sb.append(line.replace("$organizationID", organizationUUID)).toString();
			}
			if(line.contains("$practitionerID")){
				requestBody=sb.append(line.replace("$practitionerID", medicationUUID)).toString();
			}
			for(Map.Entry<String, String> key:dataParameters.entrySet()){
				if(line.contains(key.getKey())){
					requestBody=sb.append(line.replace(key.getKey(), key.getValue())).toString();
					break;
				}
			}
			if(line.contains("$referralRequestID")){
				requestBody=sb.append(line.replace("$referralRequestID", medicationRequestUUID)).toString();
			}
			if(!line.contains("$")){
				requestBody=sb.append(line).toString();
			}
		}
		return requestBody;
	}

}
