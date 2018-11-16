package image;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	
	public static void main(String[] args) {
		System.out.println(new Date());
		
		String date = new Date().toString();
		
		System.out.println(date);
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		try {
			Date newDate  = sdf.parse(date);
			System.out.println(newDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
