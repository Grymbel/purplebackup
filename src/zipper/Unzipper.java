package zipper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

//Unzipping and decryption
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
    	boolean errorless = true;
    	
    	aes = new AESThing();
    	
    	try {
    		aes.decryptFile(new File(inputZip));
		}catch (Exception e){
			errorless=false;
			System.err.println(e.getMessage());
		}
    	
    	if(errorless){
    		try {
				aes.writeToFile(new File(inputZip));
				doRecrypt = true;
			} catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
				System.err.println(e.getMessage());
			}
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

    	//Re-encrypts the read file
    	if(doRecrypt){
    		try {
    			aes = new AESThing();
				aes.encryptFile(new File(inputZip));
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
    	}
    	ArrayList<String> checkList = new ArrayList<String>();
    	
    	Zipper cleaner = new Zipper(outputDir);
    	cleaner.genFileAndDirList(new File(outputDir));
    	checkList=(ArrayList<String>) cleaner.getFileList();
    	
    	for(String s:checkList){
    		File toCheck = new File(s);
    		if(toCheck.isDirectory()&&toCheck.list().length==0){
    			toCheck.delete();
    		}
    	}
    	
    }catch(IOException ex){
    	System.err.println(ex.getMessage());
    }
   }
    
    public void unzipImport(){
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
    }catch(Exception e){
    	e.printStackTrace();
    }
    }
}