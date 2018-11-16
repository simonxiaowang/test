package douban;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

public class TestDoubanReaderV2 {
	
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new FileInputStream(new File(url));
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}
	
	
	
	public static void main(String[] args) throws JSONException, IOException {
		
		JSONObject json = readJsonFromUrl("C:\\Users\\Simon\\eclipse-workspace\\exif\\src\\main\\resources\\TestSample03");
		JSONObject subjects = json.getJSONArray("subjects").getJSONObject(0);
		JSONObject rating = subjects.getJSONObject("rating");
		
		String originalTitle = subjects.getString("original_title");
		String title = subjects.getString("title");
		int year = subjects.getInt("year");
		long max = rating.getLong("max");
		Double score =  rating.getDouble("average");
		
		System.out.println(originalTitle);
		System.out.println(title);
		System.out.println(year);
		System.out.println(max);
		System.out.println(score);
		
		
	}

}
