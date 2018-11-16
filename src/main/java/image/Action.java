package image;

import java.io.File;

public class Action {

	public static void main(String[] args) {
		
		String parentPath = "G:\\照片存档\\IPHONE4 BACKUP";
		
		String settingYear = "2011";
		
		String path = parentPath + "\\" + settingYear;
		
		File parent = new File(path);
		
		File[] files = null;
		
		if (parent.isDirectory()) {
			files = parent.listFiles();
		}
		
		int count = 0;
		
		
		for (File file : files) {
			
			
			if (file.getName().endsWith(".jpg") || file.getName().endsWith(".JPG") || file.getName().endsWith(".PNG") || file.getName().endsWith(".JPEG")) {

				System.out.println(file.getAbsolutePath());
				
				String month = GetImageFileDate.getImageFileMonth(GetImageFileDate.getImageFileDate(file.getAbsolutePath()));
				
				String year = GetImageFileDate.getImageFileYear(GetImageFileDate.getImageFileDate(file.getAbsolutePath()));
				
				System.out.println(year + "," + month);
				
				if (path.contains(year)) {
					String newPath = null;
					if (Integer.parseInt(month) < 10) {
						newPath = path + "\\" + year +"-0" + month;
					} else {
						newPath = path + "\\" + year + "-" + month;
					}
					
					System.out.println(newPath);						
					ImageMoveTo.imageMoveTo(path, newPath, file.getName());
					count++;
				}
			}
			
		}
		
		System.out.println(count + " files are processed");
		
		
	}

}
