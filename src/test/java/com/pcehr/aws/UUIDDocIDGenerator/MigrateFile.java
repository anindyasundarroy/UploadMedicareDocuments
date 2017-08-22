package com.pcehr.aws.UUIDDocIDGenerator;

//import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.UUID;

public class MigrateFile {

		public ArrayList<String> fileUpdate() throws Exception{
			final String organizationUUID = UUID.randomUUID().toString();
			final String medicationUUID =  UUID.randomUUID().toString();
			final String medicationRequestUUID =  UUID.randomUUID().toString();
			final String documentUUID= UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
			BigInteger bg=new BigInteger(documentUUID,16);
			final String documentOID="2.25."+bg;
			final String documentID="1.3.16.1.3"+documentOID.substring(5, 9).toString()+"."+documentOID.substring(10, 19).toString()+"."+documentOID.substring(20, 36).toString();
		    ArrayList<String> list=new ArrayList<String>();
		
		    list.add(organizationUUID);
		    list.add(medicationUUID);
		    list.add(medicationRequestUUID);
		    list.add(documentOID);
		    list.add(documentID);
		    
		    return list;
		
		}
}
