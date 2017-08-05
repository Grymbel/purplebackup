package zipper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

//My AES Encryption class
public class AESThing {
	private SecretKeySpec secretKey;
	private Cipher cipher;
	
	private SecretKeySpec secretKeyAlt;

	//Loads the cipher and key for the database
	public AESThing(boolean thing){
		int length=16;
		
		byte[] key2 = new byte[length];
		try {
			key2 = fixSecret("VSV$9S$J0G1X7O*X!BH$1$CFGKJ7X4*RY",length);
		
		this.cipher=Cipher.getInstance("AES");
		this.secretKeyAlt = new SecretKeySpec(key2, "AES");
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Loads the cipher and key for backups
	public AESThing(){
		try{
		String secret;
		int length=16;
		if(KeyReader.gotKey()){
		secret = KeyReader.getKey();
		}
		else{
			KeyReader.genKey();
			secret = KeyReader.getKey();
		}
		
		byte[] key = new byte[length];
		key = fixSecret(secret, length);
		this.secretKey = new SecretKeySpec(key, "AES");
		this.cipher = Cipher.getInstance("AES");
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	//Pads the secret
	private byte[] fixSecret(String s, int length) throws UnsupportedEncodingException {
		if (s.length() < length) {
			int missingLength = length - s.length();
			for (int i = 0; i < missingLength; i++) {
				s += " ";
			}
		}
		return s.substring(0, length).getBytes("UTF-8");
	}

	//Encrypts the file
	public void encryptFile(File f)
			throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
		this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
		this.writeToFile(f);
	}

	//Decrypts the file
	/*
	 * I decided to split decrypting and writing files as unsuccessfully decrypted files
	 * can accidentally overwrite the decryption target to nothing.
	 */
	public void decryptFile(File f)
			throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
		this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
	}
	
	//Writes the encrypted/decrypted file
	public void writeToFile(File f) throws IOException, IllegalBlockSizeException, BadPaddingException {
		FileInputStream in = new FileInputStream(f);
		byte[] input = new byte[(int) f.length()];
		in.read(input);

		FileOutputStream out = new FileOutputStream(f);
		byte[] output = this.cipher.doFinal(input);
		out.write(output);

		out.flush();
		out.close();
		in.close();
	}
	
	//Checks for zip files in the output
	public static ArrayList<String> getArchives(){
		Zipper zip = new Zipper();
		zip.genFileList(new File("src/output/"));
		ArrayList<String> strings = (ArrayList<String>) zip.getFileList();
		return strings;
	}

	//Protects the key
	public void encryptKey()
			throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
		this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKeyAlt);
		this.writeToFile(new File("src/zipper/the.key"));
	}

	//Opens the key for use
	public void decryptKey()
			throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
		this.cipher.init(Cipher.DECRYPT_MODE, this.secretKeyAlt);
	}
	
	//Edits the key
	public void writeToKey() throws IOException, IllegalBlockSizeException, BadPaddingException {
		File f = new File("src/zipper/the.key");
		FileInputStream in = new FileInputStream(f);
		byte[] input = new byte[(int) f.length()];
		in.read(input);

		FileOutputStream out = new FileOutputStream(f);
		byte[] output = this.cipher.doFinal(input);
		out.write(output);

		out.flush();
		out.close();
		in.close();
	}
}