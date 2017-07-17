package userManagement.model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashPass {
	public static final int iterations = 1000;
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

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
		HashPass HP = new HashPass();
		Base64.Encoder enc = Base64.getEncoder();
		byte [] newSalt = HP.createSalt();
		System.out.println("Salt: " + enc.encodeToString(newSalt));
		System.out.println("Hash: " + HP.getHashedPassword("Password", newSalt));
		//Base64.Decoder dnc = Base64.getDecoder();
		//byte [] saltDecoded = dnc.decode("Hrb9kWHSxFlJVy9tn2mVVg==");
		//System.out.println("Password: T0122332Y\n" + "Hash: " + HP.getHashedPassword("T0122332Y", saltDecoded));
		
		/*
		T0122332Y: 
			Salt: Hrb9kWHSxFlJVy9tn2mVVg==
			Hash: RLmqx//VSr8QfklsRWx4TO1QstI4JobVsLLGPgcK8emTsDUPIc7+oClVWv2uXTbqDNVDekd66fhpDIEZPSAHGg==
			
		T0132425F:
			Salt: yUkxlB8T/ocx09MscWYDkg==
			Hash: veJUkvADVWNjIhgOXsK7kfqVotKTXgcWyL9qa5dR0dDe87JZ+7hrmNrD8ga6Ys5VEncG+IkZB1DzOKmDKZnJ1g==
			
		T0133643O:
			Salt: CrQZi0S8Fk+SJH/DaUhrLQ==
			Hash: sie6f9/oVy++XBOzQdRl6xZNYS9k6mt3J0g+Ql5lFug/Nf0+7Ccbg3SpHlqkl0/jD1vKJVOXXUaMrIjLwEbZ6w==
			
		T0137182J:
			Salt: PBdAbngf71ZANjLMSjecZQ==
			Hash: LDQWKVB0cwoJsuaxD3RpoXBa0TB4d4hzfwDySUhCJj1oSm405isrh4hvENSwYF9tSByvJ32+ck9SzoiV9dwIjQ==
			
		T0145681T:
			Salt: uvSTxHoAgLRUe809Cy1lyg==
			Hash: eeWWoVBzGoXdLFKgk8oDTA3PnHoxk8DuUAv7TVKg65Z+33Uqo8TQccIEuoyC9KigIiH392a6SFrHqfLZFCm6WQ==
			
		T0147532J:
			Salt: Vxs1+227VjmiUgQc60qxMg==
			Hash: SOXuXJm2KRiIzKsvpLdCqa9zy858DlcwkLWbpv3GSJwAsZfvMY7nmvGJTXv3Bcv5KspcpVDvx4PYdPJ0yznQvA==
			
		T0156723G:
			Salt: co6KmniLC3lZNw/yS9HvVQ==
			Hash: GBZXRI6bZZXWudX0LbTFQKhiYQld1F5GiCeHSsR9vnUKsIKEXy5jRCF9M7Ap9Dsh8LBauUe+OTQpbLbQ613hKQ==
			
		T0174638P:
			Salt: me64utTUrlj85RJFVA2Tjg==
			Hash: pfqM37B8nBTGg5iCa2an2+gKWZFoXpmZPV5nNC6imIHq/blvoveVv/q5g38NzQFOst3Jh5MFnFnJMY4TZYp+ig==
			
		T0175927S:
			Salt: Tede5Z0bglf4S0+5OlRBPw==
			Hash: geBCACNkep+KxTIXvIykJenHjjgFmmI79A/NFgBBSnHDxr2iG/oKd/ieBLtt1FV2j3TvY0XfBWZzPZ3pqldfcQ==
			
		T0199283U:
			Salt: 0Rri/iJrxsbKoGo+I8TU6Q==
			Hash: x77gSCkuo35wC5wwcY8GY7U18+EKeKnBoEUsihJsjRTxXODRGZi/oMZUSAwjJ2R0sdv9g0G/kdFNz8LKAGfbbw==
		*/
	}

}
