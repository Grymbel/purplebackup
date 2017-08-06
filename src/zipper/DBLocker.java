package zipper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class DBLocker {
	//The existence of this file means the DB is unencrypted
	private static File existence = new File("src/database/existence");
	
	public static void lockDB(){
		System.out.println("Starting lock");
		if(existence.exists()){
			System.out.println("Lock process");
		AESThing aes = new AESThing();
		try {
			aes.encryptFile(new File("purplebackups.db"));
			System.out.println("Locked DB");
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
			e.printStackTrace();
		}
		existence.delete();
		}
	}
	public static void unlockDB(){
		System.out.println("Starting unlock");
		if(!existence.exists()){
			System.out.println("Unlock process");
		FileWriter fw;
		try {
			fw = new FileWriter("src/database/existence");
			fw.write("Existence");
			fw.close();
			
			AESThing aes = new AESThing();
			boolean errorless = true;
			try {
				aes.decryptFile(new File("purplebackups.db"));
			} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
				errorless=false;
				e.printStackTrace();
			}
			if(errorless){
			aes.writeToFile(new File("purplebackups.db"));
			}
			System.out.println("Unlocked DB");
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		catch(IOException io){
			System.out.println("No db to decrypt yet");
		}
		}
	}
}
