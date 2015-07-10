package it.ialweb.poi;

public class TransformEmail {

	public static String getNameByEmail(String email){
		return email.split("@")[0].substring(0, 1).toUpperCase() + email.split("@")[0].substring(1);
		
	}
}
