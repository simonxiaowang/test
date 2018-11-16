package image;

import java.io.File;

public class ImageMoveTo {
	
	public static void imageMoveTo(String oldPath, String newPath, String fileName) {
		String path = oldPath +"\\"+fileName;
		File file = new File(path);
		file.renameTo(new File(newPath +"\\"+fileName));
	}
	
	
	
	public static void main(String[] args) {
		
		String oldPath = "C:\\Users\\Simon\\Desktop\\2014";
		String newPath = "C:\\Users\\Simon\\Desktop\\2014\\2014-01";
		String fileName = "DSC_0801.JPG";
		
		imageMoveTo(oldPath,newPath,fileName);

	}
}
