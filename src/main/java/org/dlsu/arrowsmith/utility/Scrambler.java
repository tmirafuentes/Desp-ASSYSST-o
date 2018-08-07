package org.dlsu.arrowsmith.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Scrambler {
	
	public static String getHashed512(String input, String salt) {
		String output = "";
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes());
			
			byte[] bytes = md.digest(input.getBytes("UTF-8"));
			
			StringBuilder sb = new StringBuilder();
			
			for(int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
			
			output = sb.toString();
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}
	
	public static boolean isSamePassword(String dbPass, String currPass) {
		String salt = splitSalt(dbPass);
		
//		System.out.println("salt: " + salt);
		
		String hashedPass = getHashed512(currPass, salt);
		
//		System.out.println("hashedPass: " + hashedPass);
		
		return dbPass.equals(hashedPass + "|" + salt);
	}
	
	public static String splitSalt(String dbPass) {
		return dbPass.split("\\|")[1];
	}
}
