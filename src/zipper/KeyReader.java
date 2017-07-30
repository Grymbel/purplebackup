package zipper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class KeyReader {
public static String getKey(){
	String key = "";
	try {
		FileReader fr = new FileReader("src/zipper/the.key");
		Scanner sc = new Scanner(fr);
		
		key=sc.nextLine();
		sc.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
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
    System.out.println(saltStr);
    
    try {
		FileWriter fw = new FileWriter("src/zipper/the.key");
		fw.append(saltStr);
		fw.close();
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
