package sjsu.umlparser;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class testUnicodeyUML {

	public static void main(String[] args) {
		String temp = "<";
		
		//System.out.println(temp);
		String unicodeString = new String("\uFF0C"); // "Help"
		byte[] utf8Bytes = null;
		String convertedString = null;
		try {
			System.out.println(unicodeString);
			utf8Bytes = unicodeString.getBytes("UTF8");
			convertedString = new String(utf8Bytes, "UTF8");
	//		System.out.println(convertedString); // same as the original string
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
