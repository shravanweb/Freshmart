package d3e.core;

import java.util.regex.Pattern;

public class FieldValidation {
	public static boolean isEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9.!#$%&\\'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).matches();
	}
}
