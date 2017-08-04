package zipper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class KeyReader {
	
public static void lockKey(){
	AESThing aes = new AESThing(true);
	
	try {
		aes.encryptKey();
	} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public static void unlockKey(){
	AESThing aes = new AESThing(true);
	boolean errorless=true;
	try {
		aes.decryptKey();
	}catch (Exception e){
		errorless=false;
		System.err.println(e.getMessage());
	}
	
	if(errorless){
		try {
			aes.writeToKey();
		} catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
			System.err.println(e.getMessage());
		}
	}
}

public static String getKey(){
	unlockKey();
	String key = "";
	try {
		FileReader fr = new FileReader("src/zipper/the.key");
		Scanner sc = new Scanner(fr);
		
		key=sc.nextLine();
		sc.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	lockKey();
	return key;
	}
public static void genKey() {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()-=";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < 33) { // length of the random string.
        int index = (int) (rnd.nextFloat() * chars.length());
        salt.append(chars.charAt(index));
    }
    String saltStr = salt.toString();
    
    try {
		FileWriter fw = new FileWriter("src/zipper/the.key");
		fw.append(saltStr);
		fw.close();
		
		lockKey();
	} catch (IOException e) {
		e.printStackTrace();
	}
}

public static boolean gotKey(){
	try {
		FileReader fr = new FileReader("src/zipper/the.key");
	
	Scanner sc = new Scanner(fr);
	String line;
	try{
	line = sc.nextLine();
	}
	catch(NoSuchElementException n){
		sc.close();
		return false;
	}
	if(line==""||line==null){
		sc.close();
		return false;
	}
	else{
		sc.close();
		return true;
	}
} catch (FileNotFoundException e) {
	return false;
		}
	}
}
