package zipper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Unzipper
{
    List<String> fileList;
    private String inputZip;
    private String outputDir;

    public Unzipper(String inputZip,String outputDir){
    	this.inputZip=inputZip;
    	this.outputDir=outputDir;
    }
    
    public void unZipIt(){
    	AESThing aes;
    	boolean doRecrypt = false;
    	try {
    		aes = new AESThing("!@#$MySecr3tPassw0rd", 16, "AES");
    		aes.decryptFile(new File(inputZip));
			aes.writeToFile(new File(inputZip));
			doRecrypt = true;
		} catch (Exception e) {
			e.printStackTrace();
		} 

     byte[] buffer = new byte[1024];

     try{

    	//create output directory is not exists
    	File folder = new File(outputDir);
    	if(!folder.exists()){
    		folder.mkdir();
    	}

    	//get the zip file content
    	ZipInputStream zis =
    		new ZipInputStream(new FileInputStream(this.inputZip));
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();

    	while(ze!=null){

    	   String fileName = ze.getName();
           File newFile = new File(outputDir + File.separator + fileName);
           
            new File(newFile.getParent()).mkdirs();

            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while ((len = zis.read(buffer)) > 0) {
       		fos.write(buffer, 0, len);
            }

            fos.close();
            ze = zis.getNextEntry();
    	}

        zis.closeEntry();
    	zis.close();

    	if(doRecrypt){
    		try {
    			aes = new AESThing("!@#$MySecr3tPassw0rd", 16, "AES");
				aes.encryptFile(new File(inputZip));
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			}
    	}
    	System.out.println("Done");

    }catch(IOException ex){
       ex.printStackTrace();
    }
   }
}