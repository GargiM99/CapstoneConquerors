import java.util.Random;

public class GeneratePassword {

	public static void main(String[] args) {
//		final int PASSWORD_LENGTH = 500;
//		
//	    final String ALLOWED_UPPERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//	    final String ALLOWED_LOWERS = "abcdefghijklmnopqrstuvwxyz";
//	    final String ALLOWED_SPECIALS = "!@#$";
//	    final String ALLOWED_NUMBERS = "1234567890";
//	    
//	    String allowedChars = ALLOWED_UPPERS + ALLOWED_LOWERS + ALLOWED_SPECIALS + ALLOWED_NUMBERS;
//	    Random random = new Random();
//	    
//	    char[] password = new char[PASSWORD_LENGTH];
//	   
//	    for(int i = 0; i< PASSWORD_LENGTH ; i++) {
//	       password[i] = allowedChars.charAt(random.nextInt(allowedChars.length()));
//	    }
		
		String test = "";
		System.out.println(test.replaceAll("[^a-zA-Z]", ""));
	}

}
