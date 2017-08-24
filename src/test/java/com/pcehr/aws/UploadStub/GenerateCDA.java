package com.pcehr.aws.UploadStub;

import java.security.*;
import java.security.cert.*;
import java.text.*;
import java.util.*; 
import java.util.zip.*;

import javax.security.auth.x500.*;
import javax.xml.crypto.*;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.*;
import javax.xml.crypto.dsig.keyinfo.*; 
import javax.xml.crypto.dsig.spec.*;
import javax.xml.crypto.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

import groovy.xml.XmlUtil;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.math.BigInteger;
import java.util.UUID;






/*import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSDocInfo;
import org.apache.ws.security.WSEncryptionPart;
import org.apache.ws.security.WSSConfig;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.transform.STRTransform;
import org.apache.ws.security.util.WSSecurityUtil;*/
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import java.util.Map;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
//import org.apache.jmeter.protocol.http.util.Base64Encoder;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.apache.commons.io.*;

public class GenerateCDA {
 int BUFFER_LENGTH = 1024;
String COMMON_NS = "http://ns.electronichealth.net.au/xsp/xsd/SignedPayload/2010";
//String certPath="C:\\Jmeter\\Certificates\\8003623233354074.p12"
//def certPath = context.expand( '${#TestSuite#FolderPath}' )
//String folderPath = context.expand( '${#TestSuite#FolderPath}' );
//def certPath1= folderPath+"\\Keys\\"
//def certNumber= context.expand( '${#TestSuite#HPIO}' )
//def certPath = certPath1+'\\'+certNumber+'.p12';

String certPass="Pass-123";
String UTF_8 = "UTF-8";
///////////////////////////////////////////
String rootfile=null;

public void cdaGeneration(Map<String,String> dataParameters,String ihi,String docID) throws TransformerFactoryConfigurationError, Exception{
File VM=new File("src\\main\\Resources\\PBS3A.vm");
String rootfile=FileUtils.readFileToString(VM);

int s=1;
while(rootfile.contains("$NewID")){
	rootfile=rootfile.replace("$NewID"+s, UUID.randomUUID().toString());
	s++;
}

for(Map.Entry<String, String> key:dataParameters.entrySet()){
	rootfile=rootfile.replace(key.getKey(), key.getValue());
}
rootfile=rootfile.replace("$patientId", ihi);
rootfile=rootfile.replace("$XDSUniqueID", docID);
//System.out.println(rootfile);
//log.info("Encoded Data:  " + new String(encodedData));

File root=new File("target\\Output\\"+docID);
root.mkdir();

File dir1 =  new File (root, "CDA");
if (dir1.mkdir() || (dir1.exists() && dir1.isDirectory())) {
	File dir2 =  new File (dir1, "CDADOC");
	if (dir2.mkdir() || (dir2.exists() && dir2.isDirectory())) {
		File file_root = new File (dir2, "CDA_ROOT.xml");
//		System.out.println("Create "+file.getAbsolutePath());
		FileOutputStream fileOutputStream2 = new FileOutputStream(file_root);
		fileOutputStream2.write(rootfile.getBytes(UTF_8));
		fileOutputStream2.close();
	}
}

String CDA_ROOT_FILE_PATH = "target\\Output\\"+docID+"\\CDA\\CDADOC\\CDA_ROOT.xml";

File file = new File(CDA_ROOT_FILE_PATH);
FileInputStream fis = new FileInputStream(file);
ByteArrayOutputStream bos = new ByteArrayOutputStream();
byte[] buf = new byte[1024];
try {
    for (int readNum; (readNum = fis.read(buf)) != -1;) {
        bos.write(buf, 0, readNum);
    }
} catch (IOException ex) {
    ex.printStackTrace();
}
byte[] value = bos.toByteArray();
MessageDigest md = MessageDigest.getInstance("SHA1");
String hash= new String(Base64.encodeBase64(md.digest(value)));
// log.info(hash)
String tag = "<tns:eSignature xmlns:tns="+"\"http://ns.electronichealth.net.au/cdaPackage/xsd/eSignature/2012\""+" xmlns:xsi="+"\"http://www.w3.org/2001/XMLSchema-instance\""+"><ds:Manifest xmlns:ds="+"\"http://www.w3.org/2000/09/xmldsig#\""+"><ds:Reference URI="+"\"CDA_ROOT.XML\""+"><ds:DigestMethod Algorithm="+"\"http://www.w3.org/2000/09/xmldsig#sha1\""+"/><ds:DigestValue>"+hash+"</ds:DigestValue></ds:Reference></ds:Manifest><tns:signingTime>2015-07-15T15:48:43.380Z</tns:signingTime><tns:approver><tns:personId>http://ns.electronichealth.net.au/id/hi/hpii/1.0/8003613233348366</tns:personId><tns:personName><tns:nameTitle>Dr.</tns:nameTitle><tns:givenName>Todd</tns:givenName><tns:familyName>Bagshaw</tns:familyName><tns:nameSuffix/></tns:personName></tns:approver></tns:eSignature>";    

Document payloadDoc = loadXMLFromString(tag);	

KeyStore keyStore = KeyStore.getInstance("PKCS12");
keyStore.load(new FileInputStream("src\\test\\java\\com\\pcehr\\resources\\PropertyFiles\\8003621566686120.p12"), certPass.toCharArray());

String keyAlias = "";
while (keyStore.aliases().hasMoreElements()) {
	String alias = (String) keyStore.aliases().nextElement();
	keyAlias = alias;
	System.out.println(alias);
	break;
}
DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
docBuilderFactory.setNamespaceAware(true);
DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
Document containerDoc = docBuilder.newDocument();

Element signedPayloadElem = containerDoc.createElementNS(COMMON_NS, "sp:signedPayload");
containerDoc.appendChild(signedPayloadElem);


Element signaturesElem = containerDoc.createElementNS(COMMON_NS, "sp:signatures");
signedPayloadElem.appendChild(signaturesElem);

Element signedPayloadDataElem = containerDoc.createElementNS(COMMON_NS, "sp:signedPayloadData");
signedPayloadElem.appendChild(signedPayloadDataElem);

Node rawPayloadNode = containerDoc.importNode(payloadDoc.getDocumentElement(), true);
signedPayloadDataElem.appendChild(rawPayloadNode);

String signId = UUID.randomUUID().toString();
Attr idAttr = containerDoc.createAttributeNS(null, "id");
idAttr.setValue(signId);
signedPayloadDataElem.getAttributes().setNamedItem(idAttr);

containerDoc.normalizeDocument();

XMLSignatureFactory xmlSigFactory = XMLSignatureFactory.getInstance("DOM");

String referenceUri = "#" + signId;
DigestMethod digestMethod = xmlSigFactory.newDigestMethod(DigestMethod.SHA1, null);
Transform transform = xmlSigFactory.newTransform(CanonicalizationMethod.EXCLUSIVE, (TransformParameterSpec) null);
List transformList = Collections.singletonList(transform);
Reference reference = xmlSigFactory.newReference(referenceUri, digestMethod, transformList, null, null);

CanonicalizationMethod canonicalisationMethod = xmlSigFactory.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, (C14NMethodParameterSpec) null);
SignatureMethod signatureMethod = xmlSigFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null);
List referenceList = Collections.singletonList(reference);
SignedInfo signedInfo = xmlSigFactory.newSignedInfo(canonicalisationMethod, signatureMethod, referenceList);

//for (CertificateKeyPair keyPair : certificateKeyPairs) {

PrivateKey signingPrivateKey = (PrivateKey) keyStore.getKey(keyAlias, certPass.toCharArray());
X509Certificate signingCertificate = (X509Certificate) keyStore.getCertificate(keyAlias);
//cert.getPublicKey();
    //X509Certificate signingCertificate = keyPair.getCertificate();
    //PrivateKey signingPrivateKey = keyPair.getPrivateKey();

    KeyInfoFactory keyinfoFactory = xmlSigFactory.getKeyInfoFactory();
    X509Data x509Data = keyinfoFactory.newX509Data(Collections.singletonList(signingCertificate));
    KeyInfo keyInfo = keyinfoFactory.newKeyInfo(Collections.singletonList(x509Data));

    XMLSignature signature = xmlSigFactory.newXMLSignature(signedInfo, keyInfo);
    DOMSignContext signContext = new DOMSignContext(signingPrivateKey, signaturesElem);

    //Marshal and sign the signature elements                
    signature.sign(signContext);

//signedXML = documentObjectToString(containerDoc);


    // Verification
    //System.out.println("*********Is signature valid***********" + verifySignature(containerDoc));

    // Print signed xml
    //System.out.println("********Signed XML*********" + Utility.documentObjectToString(containerDoc));

StringWriter sw = new StringWriter();
StreamResult result = new StreamResult(sw);

Transformer trans = TransformerFactory.newInstance().newTransformer();
trans.transform(new DOMSource(containerDoc), result);
String signedXML = sw.toString();

File dir3 =  new File (root, "CDA");
if (dir3.mkdir() || (dir3.exists() && dir3.isDirectory())) {
	File dir4 =  new File (dir3, "CDADOC");
	if (dir4.mkdir() || (dir4.exists() && dir4.isDirectory())) {
		File file_sign = new File (dir4, "CDA_SIGN.xml");
//		System.out.println("Create "+file.getAbsolutePath());
		FileOutputStream fileOutputStream2 = new FileOutputStream(file_sign);
		fileOutputStream2.write(signedXML.getBytes(UTF_8));
		fileOutputStream2.close();
	}
}

File zipFile = new File(root, "CDA.zip");
FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
		BufferedOutputStream bfos = new BufferedOutputStream(fileOutputStream);
		ZipOutputStream zipOutputStream = new ZipOutputStream(bfos);
		
		
		zipOutputStream.setLevel(ZipOutputStream.DEFLATED); 			

				ZipEntry folderEntry = new ZipEntry("CDA"+"/");
				ZipEntry folderEntry2 = new ZipEntry("CDA"+"/" + "CDADOC"+"/");
				zipOutputStream.putNextEntry(folderEntry);
				zipOutputStream.putNextEntry(folderEntry2);
		/*
		 * Add files in zip file
		 */		
		
		byte[] buffer = new byte[BUFFER_LENGTH];
		zipOutputStream.setLevel(ZipOutputStream.DEFLATED); 			
			File file1 =  new File( new File( new File(root, "CDA"),"CDADOC"), "CDA_ROOT.xml");
			//System.out.println("Adding: "+file1.getAbsolutePath());				
            FileInputStream fileInputStream = new FileInputStream(file1);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, BUFFER_LENGTH);
            
			String entryName = "CDA"+"/"+"CDADOC"+"/"+"CDA_ROOT.xml";
			ZipEntry zipEntry = new ZipEntry(entryName);
            zipOutputStream.putNextEntry(zipEntry);
            int count;
            while((count = bufferedInputStream.read(buffer, 0, BUFFER_LENGTH)) != -1) {
            	zipOutputStream.write(buffer, 0, count);
            }
            bufferedInputStream.close();
		byte[] buffer1 = new byte[BUFFER_LENGTH];
			zipOutputStream.setLevel(ZipOutputStream.DEFLATED); 			
			File file2 =  new File( new File( new File(root, "CDA"),"CDADOC"), "CDA_SIGN.xml");
			//System.out.println("Adding: "+file1.getAbsolutePath());				
            FileInputStream fileInputStream1 = new FileInputStream(file2);
            BufferedInputStream bufferedInputStream1 = new BufferedInputStream(fileInputStream1, BUFFER_LENGTH);
            
			String entryName1 = "CDA"+"//"+"CDADOC"+"//"+"CDA_SIGN.xml";
			ZipEntry zipEntry1 = new ZipEntry(entryName1);
            zipOutputStream.putNextEntry(zipEntry1);
            int count1;
            while((count1 = bufferedInputStream1.read(buffer1, 0, BUFFER_LENGTH)) != -1) {
            	zipOutputStream.write(buffer1, 0, count1);
            }
            bufferedInputStream.close();
            bufferedInputStream1.close();
            fileInputStream1.close();
            fileInputStream.close();
            
   		    fis.close();
   		    bos.close();


   		    zipOutputStream.close();
   	        bfos.close();
   	     String CDAFilePath = "target\\Output\\"+docID+"\\CDA.zip";
   	     File CDAFile = new File(CDAFilePath);
   	     byte[] ba = FileUtils.readFileToByteArray( CDAFile );
   	  Base64 coder = new Base64();
   	 byte[] encodedData = coder.encode(ba);
   	 String Base64=new String(encodedData)	;
   		System.out.println(Base64);		

}

Document loadXMLFromString(String xml) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    DocumentBuilder builder = factory.newDocumentBuilder();
    return builder.parse(new ByteArrayInputStream(xml.getBytes()));
}

String documentObjectToString(Document documentObject) {
    try {
        Source source = new DOMSource(documentObject);
        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.transform(source, result);
        return stringWriter.getBuffer().toString();
    } catch (TransformerConfigurationException e) {
        e.printStackTrace();
    } catch (TransformerException e) {
        e.printStackTrace();
    }
    return null;
}

}
