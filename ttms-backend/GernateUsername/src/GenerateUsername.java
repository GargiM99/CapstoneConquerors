
public class GenerateUsername {

	public static void main(String[] args) {
		String firstname = "John";
		String lastname = "S";
		
		String lastnameExtract = "";
		String firstnameExtract = "";
		
		int firstnameExtractAmount = 2;
		int lastnameExtractAmount = 6;
		int numericAmount = 0;
		int usernameLength = 7;
		int spacerAmount = 1;
		
		//Converting to lowercase
		firstname = firstname.toLowerCase();
		lastname = lastname.toLowerCase();
		
		//Removing non letters
		firstname = firstname.replaceAll("[^a-z]", "");
		lastname = lastname.replaceAll("[^a-z]", "");
		
		//Determining the amount to extract from lastname and firstname
		if (lastname.length() < lastnameExtractAmount) {
			firstnameExtractAmount = firstnameExtractAmount + (lastnameExtractAmount - lastname.length());
			lastnameExtractAmount = lastname.length();	
		}
		
		//Checks if there needs to be numeric amount added
		if (firstname.length() < firstnameExtractAmount) {
			firstnameExtractAmount = firstname.length();
			numericAmount = usernameLength - (firstnameExtractAmount + lastnameExtractAmount) + spacerAmount;
		}
		
		lastnameExtract = lastname.substring(0, lastnameExtractAmount);
		firstnameExtract = firstname.substring(0, firstnameExtractAmount);
		
		System.out.println(numericAmount);
		System.out.println(firstname + " : " +firstnameExtract + " : " + firstnameExtractAmount);
		System.out.println(lastname + " : " + lastnameExtract + " : " + lastnameExtractAmount);
		
		String tUsername = "SmithJo002";
		
		int numerics = Integer.parseInt(tUsername.replaceAll("[^0-9]", ""));
	    String result = String.format("%0" + numericAmount + "d" , ++numerics);
	    
	    System.out.println(result);
	}

}
