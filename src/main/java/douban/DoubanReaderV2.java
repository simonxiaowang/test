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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import cineplex.CineplexReaderV2;

public class DoubanReaderV2 {

	private static List<String> cinemas = new ArrayList<String>();


	static {
		cinemas.add("cineplex-cinemas-yongedundas-and-vip");
		cinemas.add("scotiabank-theatre-toronto");
		cinemas.add("cineplex-cinemas-markham-and-vip");
	}


	private static Map<String, Movie> movieDataBase = new HashMap<>();

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
		
		List<Movie> movieList = null;
		
		for (String cinemaName : cinemas) {
			movieList = new ArrayList<Movie>();
			printMoviesList(cinemaName, movieList);
		}
		

	}
	
	public static void printMoviesList(String cinemaName, List<Movie> movieList) throws MalformedURLException, IOException {
		
		String first = "https://www.cineplex.com/Showtimes/any-movie/";
		String date = "?11/13/2018";
		
		List<String> movies = CineplexReaderV2.getCineplexMovies(first + cinemaName + date);
		
		System.out.println(cinemaName + ": ");
		System.out.println();

		for (String movie : movies) {

			String name = URLEncoder.encode(movie, "ISO-8859-1");

			Movie m = searchMovies(name);
			
			if (m != null) {
				movieList.add(m);
			}
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
		System.out.println();
	}

	public static Movie searchMovies(String movieName) throws JSONException, IOException {
		
		Movie movie;
		
		if (movieDataBase.containsKey(movieName)) {
			movie = movieDataBase.get(movieName);
		} else {
			
			JSONObject json = readJsonFromUrl("https://api.douban.com/v2/movie/search?q=" + movieName + "&start=0&count=1");
			
			JSONObject subjects = json.getJSONArray("subjects").getJSONObject(0);
			JSONObject rating = subjects.getJSONObject("rating");
			
			String originalTitle = subjects.getString("original_title");
			String title = subjects.getString("title");
			int year = subjects.getInt("year");
			Double score = rating.getDouble("average");
			
			movie = new Movie();
			
			movie.setEngName(originalTitle);
			movie.setChnName(title);
			movie.setRating(score);
			movie.setYear(year);
			movieDataBase.put(originalTitle, movie);			
		}

		return movie;
	}

}
