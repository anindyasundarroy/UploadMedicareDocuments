package Migration.MedicareDocuments;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

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
        propertyList=pl.getProperty("ptf");
        System.out.println(propertyList);
        //System.exit(0);
        System.out.println("Please provide the pbs file name: ");
        Scanner s=new Scanner(System.in);
        String pbsFileName=s.next();
        dataParameters=dataProvider.retrieveDataFromInputSheet(pbsFileName);
        list=mf.fileUpdate();
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
