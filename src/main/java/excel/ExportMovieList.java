package excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import douban.DoubanReaderV3;
import douban.Movie;

public class ExportMovieList {
	
	private static String FILE_NAME = null;
	
	public static void write(List<Movie> movieList) {
		
		if (movieList.isEmpty() || movieList == null) return;
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("MovieList");

		int rowNum = 0;
		System.out.println("Creating excel");
		
		Row row = sheet.createRow(rowNum++);
		
		Cell cell00 = null;
		Cell cell01 = row.createCell(1);
		Cell cell02 = row.createCell(2);
		Cell cell03 = row.createCell(3);
		Cell cell04 = row.createCell(4);
		Cell cell05 = row.createCell(5);
		Cell cell06 = row.createCell(6);
		Cell cell07 = row.createCell(7);
		
		cell01.setCellValue("english_name");
		cell02.setCellValue("chinese_name");
		cell03.setCellValue("rating");
		cell04.setCellValue("year");
		cell05.setCellValue("Dundas");
		cell06.setCellValue("Scotia");
		cell07.setCellValue("Markham");

		for (Movie m : movieList) {
			
			row = sheet.createRow(rowNum);
			cell00 = row.createCell(0);
			cell01 = row.createCell(1);
			cell02 = row.createCell(2);
			cell03 = row.createCell(3);
			cell04 = row.createCell(4);
			cell05 = row.createCell(5);
			cell06 = row.createCell(6);
			cell07 = row.createCell(7);
			
			cell00.setCellValue(rowNum++);
			cell01.setCellValue(m.getEngName());
			cell02.setCellValue(m.getChnName());
			cell03.setCellValue(m.getRating());
			cell04.setCellValue(m.getYear());
			cell05.setCellValue(m.isDundus());
			cell06.setCellValue(m.isScotia());
			cell07.setCellValue(m.isMarkham());
			
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");
	}
	
	@Test
	public void test() {	
		Movie m = new Movie();
		m.setChnName("泰坦尼克");
		m.setEngName("Titanic");
		m.setRating(9.0);
		m.setYear(1998);
		m.setMarkham(true);
		
		Movie m2 = new Movie();
		m2.setChnName("肖申克的救赎");
		m2.setEngName("ShaoShank Redemption");
		m2.setRating(9.9);
		m2.setYear(1991);
		m2.setDundus(true);
		m2.setScotia(true);
		
		
		
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		movieList.add(m);
		movieList.add(m2);
		
		write(movieList);
	}
	
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		Long start = System.currentTimeMillis();
		FILE_NAME = "C:\\Users\\Simon\\Desktop\\MovieLis20181116.xlsx";
		String date = "?Date=11/16/2018";
		List<Movie> movieList = DoubanReaderV3.getMovieList(date);
		write(movieList);
		Long end = System.currentTimeMillis();
		System.out.println((double)(end - start)/2.0 + "s");
		
	}

}
