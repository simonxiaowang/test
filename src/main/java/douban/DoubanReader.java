package douban;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import cineplex.CineplexReader;

public class DoubanReader {

	public static List<Movie> movieList = new ArrayList<Movie>();

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	public static void main(String[] args) throws IOException, JSONException {

		String[] movies = CineplexReader.getCineplexMovies();

		System.out.println("英文名字：" + "\t" + "中文名字：" + "\t" + "豆瓣评分：" + "\t");

		for (String movie : movies) {

			String m = URLEncoder.encode(movie, "ISO-8859-1");

			searchMovies(m);
		}

		Collections.sort(movieList, new Comparator<Movie>() {

			@Override
			public int compare(Movie o1, Movie o2) {
				if ((o1.getRating() - o2.getRating()) > 0)
					return -1;
				else if ((o1.getRating() - o2.getRating()) < 0)
					return 1;
				return 0;
			}
		});

		for (Movie m : movieList) {
			System.out.println(m);
		}
		
		System.out.println("Total :" + movieList.size());

	}

	public static String getValue(String str, int index) {

		int start = str.indexOf("\"", index + 1) + 3;
		int end = str.indexOf("\"", start);

		String substring = str.substring(start, end);

		return substring;

	}

	public static void searchMovies(String movie) throws JSONException, IOException {

		JSONObject json = readJsonFromUrl(
				"https://api.douban.com/v2/movie/search?q=" + movie + "&start=0&count=10&year=2018");
		String str = json.toString();

		int prev = 0;

		// "original_title":"The Shawshank Redemption"
		while (str.indexOf("original_title", prev) > 0) {

			int index = str.indexOf("original_title", prev);

			String originalTitle = getValue(str, index);

			// "average": 7.2

			// "year": "2018"

			int indexYear = str.indexOf("year", index);

			String year = getValue(str, indexYear);

			if (year.equalsIgnoreCase("2018")) {

				int indexName = str.indexOf("title", indexYear);

				String name = getValue(str, indexName);

				int indexAverage = str.indexOf("average", index);

				String average = str.substring(indexAverage + 9, indexAverage + 12);

				Movie m = new Movie();
				
				m.setEngName(originalTitle);
				m.setChnName(name);
				
				if (!average.contains(",") || !average.contains("\"")) {

					try {
						m.setRating(Double.parseDouble(average));
					} catch (NumberFormatException e) {
						m.setRating(0.0);
					}


					// System.out.print(originalTitle + "\t");
					// System.out.print(name + "\t");
					// System.out.print("年份：" + "\t" + year + "\t");
					// System.out.print(average + "\t");
					// System.out.println();
				} else {
					m.setRating(0.0);
				}

				movieList.add(m);
			}

			prev = index + 1;
		}

	}

}
