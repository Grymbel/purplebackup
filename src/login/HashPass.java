package login;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashPass {
	public static final int iterations = 100000;
	public static final int keyLength = 512;

	public byte [] hashPassword (String password, byte[] salt, int iterations, int keyLength ) {
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
			SecretKey key = skf.generateSecret(spec);
			byte [] hash = key.getEncoded( );
			return hash;
	 
	       } catch(NoSuchAlgorithmException | InvalidKeySpecException e) {
	           throw new RuntimeException(e);
	       }
	   }
	
	public byte [] createSalt () {
		SecureRandom SRandom = new SecureRandom();
		byte [] salt = new byte [16];
		SRandom.nextBytes(salt);
		return salt;
	}
	
	public byte [] getDecodedSalt (String saltToDecode) {
		Base64.Decoder dnc = Base64.getDecoder();
		byte [] saltDecoded = dnc.decode(saltToDecode);
		return saltDecoded;
	}
	
	public String getHashedPassword (String password, byte [] salt) {
		Base64.Encoder enc = Base64.getEncoder();
		String hashedPassword = enc.encodeToString(hashPassword(password, salt, iterations, keyLength));
		return hashedPassword;
	}

	public static void main(String[] args) {
		HashPass HP = new HashPass();
		Base64.Encoder enc = Base64.getEncoder();
		byte [] newSalt = HP.createSalt();
		System.out.println("Salt: " + enc.encodeToString(newSalt));
		System.out.println("Hash: " + HP.getHashedPassword("Admin", newSalt));
	}

}
