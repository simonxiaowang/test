package cineplex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import douban.DoubanReaderV2;
import douban.Movie;

public class CineplexReaderV2 {
	
	public static final String dundasAddress = "https://www.cineplex.com/Showtimes/any-movie/cineplex-cinemas-yongedundas-and-vip?Date=11/13/2018";
	public static final String scotiaAddress = "https://www.cineplex.com/Showtimes/any-movie/scotiabank-theatre-toronto?Date=11/13/2018";
	public static final String markhamAddress = "https://www.cineplex.com/Showtimes/any-movie/cineplex-cinemas-markham-and-vip?Date=11/13/2018";
	
	private static final String initName = "https://www.cineplex.com/Showtimes/any-movie/";

	private static String infos = null;
	private static String mark = "\"movie-details-link-click\"";

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static String readCineplex(String url) throws MalformedURLException, IOException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			return jsonText;
		} finally {
			is.close();
		}
	}

	public static List<String> getCineplexMovies(String targetCinema) throws MalformedURLException, IOException {
		
		List<String> movieList = new ArrayList<String>();

		infos = readCineplex(targetCinema);

		int start = 0;

		int index = -1;

		List<Integer> cords = new ArrayList<Integer>();

		while ((index = infos.indexOf(mark, start)) != -1) {
			cords.add(index);
			start = index + 2;
		}
		
		for (Integer cord : cords) {
			
			String m = getMovie(cord);
			
			if (m != null) {
				movieList.add(m);
			}
		}
		
		return movieList;

	}
	
/*	
	<a class="movie-details-link-click" href="/Movie/rampant-korean-west" aria-label="Rampant (Korean w/e.s.t.)">
    Rampant (Korean w/e.s.t.)
    </a>
*/
	public static String getMovie(Integer cord) {
		
		if (cord < 0) return null;
		
		int start = infos.indexOf(">", cord+2) + 1;
		
		int end = infos.indexOf("<", cord+2);
		
		if (end <= start) return null;
		
		String movieTitle = infos.substring(start, end).trim();	
		
		return checkName(movieTitle);
	}
	
	public static String checkName(String movieTitle) {
		
		if (movieTitle.contains("&#39;")) {
			movieTitle = movieTitle.replace("&#39;", "\'");
		}
		
		if (movieTitle.contains("&amp;")) {
			movieTitle = movieTitle.replace("&amp;", "&");
		}
		
		if (movieTitle.contains("(")) {
			int location = movieTitle.indexOf("(");
			movieTitle = movieTitle.substring(0, location).trim();
		}
		
		if (movieTitle.contains("–")) {
			int location = movieTitle.indexOf("–");
			movieTitle = movieTitle.substring(0, location).trim();
		}
		
		if (movieTitle.contains("-")) {
			int location = movieTitle.indexOf("-");
			movieTitle = movieTitle.substring(0, location).trim();
		}
		
		
		
		return movieTitle;
	}
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		
		long start = System.currentTimeMillis();
		
		List<String> cinemas = new ArrayList<String>();
		cinemas.add(dundasAddress);
		cinemas.add(scotiaAddress);
		cinemas.add(markhamAddress);
		
		
		for (String cinema: cinemas) {
			
			List<String> movieList = getCineplexMovies(cinema);
			
			for (String m : movieList) {
				System.out.println(m);
			}
			
			System.out.println("-------------------------");
			
			System.out.println("Total :" + movieList.size());
			
			System.out.println("-------------------------");
			
			System.out.println("System Running Time : " + ((double)System.currentTimeMillis() - (double)start) / 1000 + "s");
			
		}
		
	}

	/*
	 * 
	 * The Nutcracker And The Four Realms Can You Ever Forgive Me? Nobody's Fool
	 * Project Gutenberg Bohemian Rhapsody Badhaai Ho Dragon Ball Z Saiyan Double
	 * Feature Smallfoot Johnny English Strikes Again The New Romantic Crazy Rich
	 * Asians Baazaar Cliff Richard Live: 60th Anniversary Tour A Star Is Born The
	 * Hate U Give Venom The Nun Halloween Rampant Total :19
	 * 
	 * Bohemian Rhapsody, 波西米亚狂想曲, 8.8 無雙, 无双, 8.1 Smallfoot, 雪怪大冒险, 7.6 Can You
	 * Ever Forgive Me?, 你能原谅我吗？, 7.4 A Star Is Born, 一个明星的诞生, 7.4 Venom, 毒液：致命守护者,
	 * 7.2 Johnny English Strikes Again, 憨豆特工3, 7.1 Crazy Rich Asians, 摘金奇缘, 6.8
	 * Halloween, 月光光心慌慌, 6.8 The Nutcracker And The Four Realms, 胡桃夹子和四个王国, 6.1 창궐,
	 * 猖獗, 5.9 阴阳眼之瞳灵公馆, 阴阳眼之瞳灵公馆, 0.0 Nobody's Fool, 大智若愚, 0.0 Badhaai Ho, 喜得千金,
	 * 0.0 The New Romantic, 新罗曼蒂克, 0.0 Baazaar, 商战街, 0.0 Nuestro tiempo, 我们的时光, 0.0
	 * The Hate U Give, 你给的仇恨, 0.0 The Nun, 修女, 0.0 Total :19
	 * 
	 * Bohemian Rhapsody Beautiful Boy Free Solo A Simple Favor First Man Mid90s Bad
	 * Times At The El Royale Hunter Killer Suspiria A Star Is Born Venom Scotiabank
	 * Theatre Toronto Halloween Total :13
	 * 
	 * Free Solo, 徒手攀岩, 9.2 Bohemian Rhapsody, 波西米亚狂想曲, 8.8 First Man, 登月第一人, 7.8
	 * Suspiria, 阴风阵阵, 7.8 Mid '90s, 90年代中期, 7.6 A Star Is Born, 一个明星的诞生, 7.4 A
	 * Simple Favor, 一个小忙, 7.3 Venom, 毒液：致命守护者, 7.2 Bad Times at the El Royale,
	 * 皇家酒店谋杀案, 7.1 Hunter Killer, 冰海陷落, 7.1 Halloween, 月光光心慌慌, 6.8 Beautiful Boy,
	 * 漂亮男孩, 6.4 三年二班, 三年二班, 0.0 Suspiria, 阴风阵阵, 0.0 Nuestro tiempo, 我们的时光, 0.0
	 * 
	 * The Wife Can You Ever Forgive Me? A Star Is Born Bohemian Rhapsody Beautiful
	 * Boy Free Solo Colette First Man Total :8
	 * 
	 * The Nutcracker And The Four Realms Goosebumps 2: Haunted Halloween Project
	 * Gutenberg Hello, Mrs. Money A Star Is Born Bohemian Rhapsody Venom Halloween
	 * Smallfoot First Man Crazy Rich Asians Total :11
	 * 
	 * 英文名字： 中文名字： 豆瓣评分： Bohemian Rhapsody, 波西米亚狂想曲, 8.8 無雙, 无双, 8.1 First Man,
	 * 登月第一人, 7.8 Smallfoot, 雪怪大冒险, 7.6 A Star Is Born, 一个明星的诞生, 7.4 Venom,
	 * 毒液：致命守护者, 7.2 Halloween, 月光光心慌慌, 6.8 Crazy Rich Asians, 摘金奇缘, 6.8 The
	 * Nutcracker And The Four Realms, 胡桃夹子和四个王国, 6.1 Goosebumps: Haunted Halloween,
	 * 鸡皮疙瘩2：闹鬼万圣节, 5.6 李茶的姑妈, 李茶的姑妈, 5.1 Nuestro tiempo, 我们的时光, 0.0 Total :12
	 */
}
