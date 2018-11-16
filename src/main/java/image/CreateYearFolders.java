package image;

import java.io.File;

/**
 * 生成文件夹
 * @author Simon
 *
 */

public class CreateYearFolders {
	
	public static void main(String[] args) {
		
		String year = "2011";
		
		
		String dir = "G:\\照片存档\\IPHONE4 BACKUP" + "\\" + year+"\\"+ year+"-";
		
		
		for (int i=1; i<=12; i++) {

			String path = null;
			if (i<10) {
				path = dir + "0" + i;				
			} else {
				path = dir + i;
			}
			
//			System.out.println(path);
			
			
			File file = new File(path);
			
			if (!file.exists()) {
				file.mkdir();
				System.out.println(path + " is created.");
			}
			
		}
		

	}

}
