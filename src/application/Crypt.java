package application;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Crypt {
	private static final String ALGO = "AES";
	private byte[] keyValue;
	
	public Crypt(String key) {
		String formattedKey = formatKey(key);
		keyValue = formattedKey.getBytes();
	}
	
	public String encrypt(String Data) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encValue = c.doFinal(Data.getBytes());
		String encryptedValue =  Base64.getEncoder().encodeToString(encValue);
		return encryptedValue;
	}
	
	public String decrypt(String encryptedData) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decodedValue = Base64.getDecoder().decode(encryptedData);
		byte[] decValue = c.doFinal(decodedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
		
	}
	
	private Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGO);
		
		return key;
	}
	
	public String formatKey(String key) {
	
		if(key.length() > 16)
		{
			key = key.substring(0, 16);
		}
		else if(key.length() < 16) {
			int charToAdd = 16 - key.length();
			for(int i = 0; i < charToAdd; i++) {
				key = key + "7";
			}
		}
		return key;
	}
}
