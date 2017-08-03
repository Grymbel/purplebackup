package zipper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class DBLocker {
	private static File existance = new File("src/database/existance");
	
	public static void lockDB(){
		System.out.println("Starting lock");
		if(existance.exists()){
			System.out.println("Lock process");
		AESThing aes = new AESThing();
		try {
			aes.encryptFile(new File("purplebackups.db"));
			System.out.println("Locked DB");
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		existance.delete();
		}
	}
	public static void unlockDB(){
		System.out.println("Starting unlock");
		if(!existance.exists()){
			System.out.println("Unlock process");
		FileWriter fw;
		try {
			fw = new FileWriter("src/database/existance");
			fw.write("Existance");
			fw.close();
			
			AESThing aes = new AESThing();
			boolean errorless = true;
			try {
				aes.decryptFile(new File("purplebackups.db"));
				
				System.out.println("Unlocked DB");
			} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
				errorless=false;
				e.printStackTrace();
			}
			if(errorless){
			aes.writeToFile(new File("purplebackups.db"));
			}
		} catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		}
	}
}
