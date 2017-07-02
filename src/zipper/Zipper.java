package zipper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper{
	List<String> fileList;
	List<String> digestList;
    private String outputZip;
    private String outputDir;
    private String outputFile;
    private String sourceDir;
    private String outputDirFull;
    private int backupID;
    private boolean isBase;

    public Zipper(String source, String outputDir1,String outputDir2, String outputFile, int backupID){
    	fileList = new ArrayList<String>();
    	digestList = new ArrayList<String>();
    	this.sourceDir=source;
    	this.outputDir=outputDir1;
    	this.outputDirFull=outputDir1+backupID+outputDir2;
    	this.outputFile=outputFile;
    	this.outputZip=outputDirFull+outputFile;
    	this.backupID=backupID;
    }
    
    public Zipper(String source, String outputDir1,String outputDir2, String outputFile, int backupID,boolean isBase){
    	fileList = new ArrayList<String>();
    	digestList = new ArrayList<String>();
    	this.sourceDir=source;
    	this.outputDir=outputDir1;
    	this.outputDirFull=outputDir1+backupID+outputDir2;
    	this.outputFile=outputFile;
    	this.outputZip=outputDirFull+outputFile;
    	this.backupID=backupID;
    	this.isBase=isBase;
    }

    public void zipUp(){
    	this.generateFileList(new File(sourceDir));
    	if(isBase==true){
    	this.zipItAll(outputZip);
    	}
    	else{
    		this.zipIt(outputZip);
    	}
    }
    public void zipItAll(String zipFile){

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
        	
        	MDWriter mdw = new MDWriter((ArrayList<String>) fileList,(ArrayList<String>) digestList,(outputDirFull).replace("\\", "/"),(outputDir).replace("\\", "/"),backupID,isBase);
        	mdw.writeMD();
        	mdw.writeDelta();
    	}

    	zos.closeEntry();
    	zos.close();
    	System.out.println("Done");
    }catch(IOException ex){
       ex.printStackTrace();
    }
   }
    
    public void zipIt(String zipFile){

        byte[] buffer = new byte[1024];

        try{
           	MDWriter mdw = new MDWriter((ArrayList<String>) fileList,(ArrayList<String>) digestList,(outputDirFull).replace("\\", "/"),(outputDir).replace("\\", "/"),backupID,isBase);
           	mdw.writeMD();
           	mdw.writeDelta();
           	
           	FileOutputStream fos = new FileOutputStream(zipFile);
           	ZipOutputStream zos = new ZipOutputStream(fos);

           	FileReader fr = new FileReader(outputDirFull+"/delta.data");
           	Scanner sc = new Scanner(fr);
           	sc.useDelimiter("><");
           	
           	fileList = new ArrayList<String>();
           	while(sc.hasNextLine()){
           		String action = sc.next();
           		if(action.equals("ADD")||action.equals("UDT")){
           	fileList.add(sc.next());
           	sc.nextLine();
           	System.out.println(fileList);
           		}
           	}
           	
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
       	zos.close();
       	sc.close();
       	System.out.println("Done");
       }catch(IOException ex){
          ex.printStackTrace();
       }
      }
    public void generateFileList(File node){
	if(node.isFile()){
		fileList.add(generateZipEntry(node.getPath().toString()));
		try {
			digestList.add(SHA1.sha1(node));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
    
    public List<String> getFileList() {
		return fileList;
	}

	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
	}

	public String getOutputZip() {
		return outputZip;
	}

	public void setOutputZip(String outputZip) {
		this.outputZip = outputZip;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	public String getSourceDir() {
		return sourceDir;
	}

	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

	public int getBackupID() {
		return backupID;
	}

	public void setBackupID(int backupID) {
		this.backupID = backupID;
	}

	public String getOutputdirFull() {
		return outputDirFull;
	}

	public void setOutputdirFull(String outputdirFull) {
		this.outputDirFull = outputdirFull;
	}

	public boolean isBase() {
		return isBase;
	}

	public void setBase(boolean isBase) {
		this.isBase = isBase;
	}

}