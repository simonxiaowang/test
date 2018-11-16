package douban;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
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

public class DoubanReaderV3 {

	private static List<String> cinemas = new ArrayList<String>();
	private static Map<String, Movie> movieDataBase = new HashMap<>();
	private static String date = null;

	static {
		cinemas.clear();
		movieDataBase.clear();
		cinemas.add("cineplex-cinemas-yongedundas-and-vip");
		cinemas.add("scotiabank-theatre-toronto");
		cinemas.add("cineplex-cinemas-markham-and-vip");
	}

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
	
	public static List<Movie> getMovieList(String date) throws MalformedURLException, IOException {
		
		DoubanReaderV3.date = date;
		
		for (String cinemaName : cinemas) {
			saveMoviesList(cinemaName);
		}
		
		List<Movie> movieList = new ArrayList<Movie>();
		
		for (Movie e : movieDataBase.values()) {
			movieList.add(e);
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
		
		return movieList;
	}

	public static void main(String[] args) throws IOException, JSONException {
		
		List<Movie> movieList = getMovieList("?Date=11/20/2018");
		
		for (Movie m : movieList) {
			System.out.println(m);
		}

		System.out.println("Total :" + movieList.size());
		System.out.println();
		
	}
	
	public static void saveMoviesList(String cinemaName) throws MalformedURLException, IOException {
		
		String first = "https://www.cineplex.com/Showtimes/any-movie/";
//		String date = "?Date=11/13/2018";
		
		List<String> movieNames = CineplexReaderV2.getCineplexMovies(first + cinemaName + date);
		
		for (String movieName : movieNames) {

			movieName = URLEncoder.encode(movieName, "ISO-8859-1");

			Movie movie = getMovie(movieName);
			
			if (movie != null) {
				
				if ("cineplex-cinemas-yongedundas-and-vip".equals(cinemaName)) {
					movie.setDundus(true);
				}
				if ("scotiabank-theatre-toronto".equals(cinemaName)) {
					movie.setScotia(true);
				}
				if ("cineplex-cinemas-markham-and-vip".equals(cinemaName)) {
					movie.setMarkham(true);
				}
				
			}
			
			
		}


	}
	
	@Test
	public void test01() throws JSONException, IOException {
		String movieName = "A Star Is Born";
		movieName = URLEncoder.encode(movieName, "ISO-8859-1");
		Movie m = searchMoviesFromDoubanAPI(movieName);
		System.out.println(m);
		
		System.out.println(movieDataBase);
	}
	
	public static Movie getMovie(String movieName) throws JSONException, IOException {
		
		if (movieDataBase.containsKey(movieName)) {
			return movieDataBase.get(movieName);
		} else {
			return searchMoviesFromDoubanAPI(movieName);
		}
	}

	public static Movie searchMoviesFromDoubanAPI(String movieName){
		
		Movie movie = new Movie();
		
		try {
			
			
			JSONObject json = readJsonFromUrl("https://api.douban.com/v2/movie/search?q=" + movieName + "&start=0&count=1");
			
			JSONObject subjects = json.getJSONArray("subjects").getJSONObject(0);
			JSONObject rating = subjects.getJSONObject("rating");
			
//		String originalTitle = subjects.getString("original_title");
			String title = subjects.getString("title");
			int year = subjects.getInt("year");
			Double score = rating.getDouble("average");
			
			movieName = URLDecoder.decode(movieName, "ISO-8859-1");
			
			movie.setEngName(movieName);
			movie.setChnName(title);
			movie.setRating(score);
			movie.setYear(year);
			
			movieDataBase.put(movieName, movie);			
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		

		return movie;
	}

}
