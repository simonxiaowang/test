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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import douban.Movie;

public class CineplexReader {
	
	private static final String dundasAddress = "https://www.cineplex.com/Showtimes/any-movie/cineplex-cinemas-yongedundas-and-vip?Date=11/6/2018";
	private static final String scotiaAddress = "https://www.cineplex.com/Showtimes/any-movie/scotiabank-theatre-toronto?Date=11/6/2018";
	private static final String markhamAddress = "https://www.cineplex.com/Showtimes/any-movie/cineplex-cinemas-markham-and-vip?Date=11/6/2018";
	private static final String eglintonAddress = "https://www.cineplex.com/Showtimes/any-movie/cineplex-cinemas-yongeeglinton-and-vip?Date=11/6/2018";

	public static List<Movie> movieList = new ArrayList<Movie>();

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

	public static String[] getCineplexMovies() throws MalformedURLException, IOException {
		
		
		
		String str = readCineplex(eglintonAddress);

		// <meta itemprop="name" content="Crazy Rich Asians">

		int prev = 0;

		Set<String> set = new HashSet<String>();

		String sample = "<meta itemprop=\"name\" content=";

		while (str.indexOf(sample, prev) > 0) {

			int index = str.indexOf(sample, prev);

			int start = str.indexOf("\"", index + sample.length()) + 1;
			int end = str.indexOf("\"", start);

			String originalTitle = str.substring(start, end);

			prev = index + 1;

			if (!originalTitle.contains("Cineplex")) {
				if (originalTitle.contains("&#39;")) {
					originalTitle = originalTitle.replace("&#39;", "\'");
				}
				if (originalTitle.contains("(")) {
					int front = originalTitle.indexOf("(");
					originalTitle = originalTitle.substring(0, front);
				}
				set.add(originalTitle);
			}

		}

		Object[] ms = Arrays.copyOf(set.toArray(), set.size());

		String[] movies = new String[ms.length];

		for (int i = 0; i < movies.length; i++) {
			movies[i] = (String) ms[i];
		}

		return movies;
	}
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		String[] movies = getCineplexMovies();
		for (String m : movies) {
			System.out.println(m);
		}
		
		System.out.println("Total :" + movies.length);
	}
	
	/*
	 * 
The Nutcracker And The Four Realms
Can You Ever Forgive Me?
Nobody's Fool
Project Gutenberg 
Bohemian Rhapsody
Badhaai Ho 
Dragon Ball Z Saiyan Double Feature
Smallfoot
Johnny English Strikes Again
The New Romantic
Crazy Rich Asians
Baazaar 
Cliff Richard Live: 60th Anniversary Tour
A Star Is Born
The Hate U Give
Venom
The Nun
Halloween
Rampant 
Total :19

Bohemian Rhapsody, 波西米亚狂想曲, 8.8
無雙, 无双, 8.1
Smallfoot, 雪怪大冒险, 7.6
Can You Ever Forgive Me?, 你能原谅我吗？, 7.4
A Star Is Born, 一个明星的诞生, 7.4
Venom, 毒液：致命守护者, 7.2
Johnny English Strikes Again, 憨豆特工3, 7.1
Crazy Rich Asians, 摘金奇缘, 6.8
Halloween, 月光光心慌慌, 6.8
The Nutcracker And The Four Realms, 胡桃夹子和四个王国, 6.1
창궐, 猖獗, 5.9
阴阳眼之瞳灵公馆, 阴阳眼之瞳灵公馆, 0.0
Nobody's Fool, 大智若愚, 0.0
Badhaai Ho, 喜得千金, 0.0
The New Romantic, 新罗曼蒂克, 0.0
Baazaar, 商战街, 0.0
Nuestro tiempo, 我们的时光, 0.0
The Hate U Give, 你给的仇恨, 0.0
The Nun, 修女, 0.0
Total :19

Bohemian Rhapsody
Beautiful Boy
Free Solo
A Simple Favor
First Man
Mid90s
Bad Times At The El Royale
Hunter Killer
Suspiria
A Star Is Born
Venom
Scotiabank Theatre Toronto
Halloween
Total :13

Free Solo, 徒手攀岩, 9.2
Bohemian Rhapsody, 波西米亚狂想曲, 8.8
First Man, 登月第一人, 7.8
Suspiria, 阴风阵阵, 7.8
Mid '90s, 90年代中期, 7.6
A Star Is Born, 一个明星的诞生, 7.4
A Simple Favor, 一个小忙, 7.3
Venom, 毒液：致命守护者, 7.2
Bad Times at the El Royale, 皇家酒店谋杀案, 7.1
Hunter Killer, 冰海陷落, 7.1
Halloween, 月光光心慌慌, 6.8
Beautiful Boy, 漂亮男孩, 6.4
三年二班, 三年二班, 0.0
Suspiria, 阴风阵阵, 0.0
Nuestro tiempo, 我们的时光, 0.0

The Wife
Can You Ever Forgive Me?
A Star Is Born
Bohemian Rhapsody
Beautiful Boy
Free Solo
Colette
First Man
Total :8

The Nutcracker And The Four Realms
Goosebumps 2: Haunted Halloween
Project Gutenberg 
Hello, Mrs. Money 
A Star Is Born
Bohemian Rhapsody
Venom
Halloween
Smallfoot
First Man
Crazy Rich Asians
Total :11

英文名字：	中文名字：	豆瓣评分：	
Bohemian Rhapsody, 波西米亚狂想曲, 8.8
無雙, 无双, 8.1
First Man, 登月第一人, 7.8
Smallfoot, 雪怪大冒险, 7.6
A Star Is Born, 一个明星的诞生, 7.4
Venom, 毒液：致命守护者, 7.2
Halloween, 月光光心慌慌, 6.8
Crazy Rich Asians, 摘金奇缘, 6.8
The Nutcracker And The Four Realms, 胡桃夹子和四个王国, 6.1
Goosebumps: Haunted Halloween, 鸡皮疙瘩2：闹鬼万圣节, 5.6
李茶的姑妈, 李茶的姑妈, 5.1
Nuestro tiempo, 我们的时光, 0.0
Total :12
	 */
}
