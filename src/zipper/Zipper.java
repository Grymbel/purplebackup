package zipper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper
{
    List<String> fileList;
    private String outputZip;
    private String sourceDir;

    public Zipper(String source, String output){
    	fileList = new ArrayList<String>();
    	this.outputZip=output;
    	this.sourceDir=source;
    }

    public void zipUp(){
    	System.out.println("Taking from x to y: "+sourceDir+outputZip);
    	this.generateFileList(new File(sourceDir));
    	this.zipIt(outputZip);
    }
    public void zipIt(String zipFile){

     byte[] buffer = new byte[1024];

     try{

    	FileOutputStream fos = new FileOutputStream(zipFile);
    	ZipOutputStream zos = new ZipOutputStream(fos);

    	System.out.println("Output to Zip : " + zipFile);

    	for(String file : this.fileList){

    		System.out.println("File Added : " + file);
    		ZipEntry ze= new ZipEntry(file);
        	zos.putNextEntry(ze);

        	FileInputStream in = new FileInputStream(sourceDir+File.separator+file);

        	int len;
        	while ((len = in.read(buffer)) > 0) {
        		zos.write(buffer, 0, len);
        	}

        	in.close();
    	}

    	zos.closeEntry();
    	//remember close it
    	zos.close();

    	System.out.println("Done");
    }catch(IOException ex){
       ex.printStackTrace();
    }
   }
    public void generateFileList(File node){

    	//add file only
	if(node.isFile()){
		fileList.add(generateZipEntry(node.getPath().toString()));
	}

	if(node.isDirectory()){
		String[] subNote = node.list();
		for(String filename : subNote){
			generateFileList(new File(node, filename));
		}
	}

    }

    private String generateZipEntry(String file){
    	return file.substring(sourceDir.length()+1, file.length());
    }
}