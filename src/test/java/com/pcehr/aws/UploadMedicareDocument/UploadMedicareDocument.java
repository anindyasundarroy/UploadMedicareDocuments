package com.pcehr.aws.UploadMedicareDocument;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import com.pcehr.aws.DataLoader.DataProvider;
import com.pcehr.aws.DataLoader.PropertyLoader;
import com.pcehr.aws.MedicareDocuments.PostMedicareDocumentService;
import com.pcehr.aws.MedicareDocuments.ReadFile;
import com.pcehr.aws.SchemaValidator.SchemaValidator;
import com.pcehr.aws.UUIDDocIDGenerator.MigrateFile;
import com.pcehr.aws.UploadStub.GenerateCDA;

public class UploadMedicareDocument {
    static ArrayList<String> list=new ArrayList<String>();
    static Map<String,String> dataParameters=new LinkedHashMap<String,String>();
    private static ArrayList<String> propertyList=new ArrayList<String>();
    
	public static void main(String[] args) throws Exception {
        MigrateFile mf=new MigrateFile();
        ReadFile rf=new ReadFile();
        DataProvider dataProvider=new DataProvider();
        SchemaValidator requestSchema=new SchemaValidator();
        PropertyLoader pl=new PropertyLoader();
        GenerateCDA generateCDA=new GenerateCDA();
        propertyList=pl.getProperty("pte");
        System.out.println(propertyList);
        
        System.out.println("Please provide the pbs file name: ");
        Scanner s=new Scanner(System.in);
        String pbsFileName=s.next();
        dataParameters=dataProvider.retrieveDataFromInputSheet(pbsFileName);
        list=mf.fileUpdate();
        String ihi="8003608666803200";
        generateCDA.cdaGeneration(dataParameters,ihi,list.get(4));
        System.exit(0);
        String pbsRequestBody=rf.updatePBSContent(list.get(4),list.get(0),list.get(1),list.get(2),dataParameters,pbsFileName);
        System.out.println(pbsRequestBody);
        System.out.println("Please provide the mbs file name: ");
        String mbsFileName=s.next();
        list=mf.fileUpdate();
        String mbsRequestBody=rf.updateMBSContent(list.get(4),list.get(0),list.get(1),list.get(2),dataParameters,mbsFileName);
        System.out.println(mbsRequestBody);
        PostMedicareDocumentService postmedicaredocument=new PostMedicareDocumentService();
	}

}
