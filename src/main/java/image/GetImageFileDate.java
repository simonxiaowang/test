package image;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
//import com.drew.metadata.exif.ExifIFD0Directory;
//import com.drew.metadata.exif.ExifSubIFDDirectory;

public class GetImageFileDate {
	
	public static void main(String[] args) {
 //      String path = "C:\\Users\\Simon\\Desktop\\2014\\DSC_0801.JPG";
 //      String path = "C:\\Users\\Simon\\Desktop\\2014\\IMG_0714.JPG";
 //      String path = "C:\\Users\\Simon\\Desktop\\2014\\CROPPED-IMG_3095.JPG";
//       String path = "G:\\照片存档\\IPHONE6S BACKUP (2018-09-21 Update)\\2016\\IMG_0002.JPG";
		String path = "G:\\照片存档\\IPHONE5 BACKUP\\2013\\IMG_0001 (2).JPG";
        Calendar c = getImageFileDate(path);
        System.out.println(getImageFileYear(c) + ", " + getImageFileMonth(c));
        
	}
	
	public static String getImageFileYear(Calendar c) {
		return c.get(Calendar.YEAR) + "";
	}
	
	public static String getImageFileMonth(Calendar c) {
		return c.get(Calendar.MONTH)+1 + "";
	}
	
	public static Calendar getImageFileDate(String path) {
		
		Map<String,MyTag> map = new HashMap<String, MyTag>();

        File imageFile = new File(path);
        try{

        Metadata metadata = ImageMetadataReader.readMetadata(imageFile);//ImageMetadataReader
        for (Directory directory : metadata.getDirectories()) {
            if(directory == null){
                continue;
            }
            
            for (Tag tag : directory.getTags()) {  
                String tagName = tag.getTagName(); // 标签名
                String desc = tag.getDescription(); // 标签信息
                String directName = tag.getDirectoryName();//文档名字
                
                MyTag mytag = new MyTag(tagName,desc,directName);
                String infos = mytag.toString();
                
                if (infos.contains("time") || infos.contains("Time") ||infos.contains("Date") || infos.contains("date")) {
                	map.put(mytag.getTagName(), mytag);
//                	System.out.println(infos);
                }
                
            }
            
        }  
//        System.out.println(map);
        
            if (map.containsKey("Date/Time Original")){
            	return setOriginal(map.get("Date/Time Original").getDesc());
            } 
            else if (map.containsKey("File Modified Date")){
            	return setOriginal(map.get("File Modified Date").getDesc());
            } 
            else if (map.containsKey("\"Date/Time")){
            	return setOriginal(map.get("\"Date/Time").getDesc());
            } 
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return Calendar.getInstance();
    
	}
	
	public static Calendar setOriginal(String desc) {
    	try {
    		SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
    		Date date = yyyymmddhhmmss.parse(desc);
    		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		System.out.println("拍照时间: " + newFormat.format(date));
    		Calendar c = Calendar.getInstance();
    		c.setTime(date);
    		return c;
    		
    	} catch(ParseException e) {
        	
        	String year = desc.substring(24);
//        	System.out.println(year);
        	
        	String month = desc.substring(4, 7);
//        	System.out.println(month);
           
           Calendar c = Calendar.getInstance();
           c.set(Calendar.YEAR, Integer.parseInt(year));
           
           switch(month) {
           	case "Jan" : c.set(Calendar.MONTH, Calendar.JANUARY);
           	break;
           	case "Feb" : c.set(Calendar.MONTH, Calendar.FEBRUARY);
           	break;
           	case "Mar" : c.set(Calendar.MONTH, Calendar.MARCH);
           	break;
           	case "Apr" : c.set(Calendar.MONTH, Calendar.APRIL);
           	break;
           	case "May" : c.set(Calendar.MONTH, Calendar.MAY);
           	break;
           	case "Jun" : c.set(Calendar.MONTH, Calendar.JUNE);
           	break;
           	case "Jul" : c.set(Calendar.MONTH, Calendar.JULY);
           	break;
           	case "Aug" : c.set(Calendar.MONTH, Calendar.AUGUST);
           	break;
           	case "Sep" : c.set(Calendar.MONTH, Calendar.SEPTEMBER);
           	break;
           	case "Oct" : c.set(Calendar.MONTH, Calendar.OCTOBER);
           	break;
           	case "Nov" : c.set(Calendar.MONTH, Calendar.NOVEMBER);
           	break;
           	case "Dec" : c.set(Calendar.MONTH, Calendar.DECEMBER);
           	break;
           }
           
           return c;
        }
            
    }

	public static Calendar modified(String desc) {

    	
    	String year = desc.substring(24);
//    	System.out.println(year);
    	
    	String month = desc.substring(4, 7);
//    	System.out.println(month);
       
       Calendar c = Calendar.getInstance();
       c.set(Calendar.YEAR, Integer.parseInt(year));
       
       switch(month) {
       	case "Jan" : c.set(Calendar.MONTH, Calendar.JANUARY);
       	break;
       	case "Feb" : c.set(Calendar.MONTH, Calendar.FEBRUARY);
       	break;
       	case "Mar" : c.set(Calendar.MONTH, Calendar.MARCH);
       	break;
       	case "Apr" : c.set(Calendar.MONTH, Calendar.APRIL);
       	break;
       	case "May" : c.set(Calendar.MONTH, Calendar.MAY);
       	break;
       	case "Jun" : c.set(Calendar.MONTH, Calendar.JUNE);
       	break;
       	case "Jul" : c.set(Calendar.MONTH, Calendar.JULY);
       	break;
       	case "Aug" : c.set(Calendar.MONTH, Calendar.AUGUST);
       	break;
       	case "Sep" : c.set(Calendar.MONTH, Calendar.SEPTEMBER);
       	break;
       	case "Oct" : c.set(Calendar.MONTH, Calendar.OCTOBER);
       	break;
       	case "Nov" : c.set(Calendar.MONTH, Calendar.NOVEMBER);
       	break;
       	case "Dec" : c.set(Calendar.MONTH, Calendar.DECEMBER);
       	break;
       }
       
       return c;
    
	}

	public static Calendar dateandtime(String desc) {

    	try {
    		SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
    		Date date = yyyymmddhhmmss.parse(desc);
    		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		System.out.println("创建时间: " + newFormat.format(date));
    		Calendar c = Calendar.getInstance();
    		c.setTime(date);
    		return c;
    		
    	} catch(ParseException e) {
        	
        	String year = desc.substring(24);
//        	System.out.println(year);
        	
        	String month = desc.substring(4, 7);
//        	System.out.println(month);
           
           Calendar c = Calendar.getInstance();
           c.set(Calendar.YEAR, Integer.parseInt(year));
           
           switch(month) {
           	case "Jan" : c.set(Calendar.MONTH, Calendar.JANUARY);
           	break;
           	case "Feb" : c.set(Calendar.MONTH, Calendar.FEBRUARY);
           	break;
           	case "Mar" : c.set(Calendar.MONTH, Calendar.MARCH);
           	break;
           	case "Apr" : c.set(Calendar.MONTH, Calendar.APRIL);
           	break;
           	case "May" : c.set(Calendar.MONTH, Calendar.MAY);
           	break;
           	case "Jun" : c.set(Calendar.MONTH, Calendar.JUNE);
           	break;
           	case "Jul" : c.set(Calendar.MONTH, Calendar.JULY);
           	break;
           	case "Aug" : c.set(Calendar.MONTH, Calendar.AUGUST);
           	break;
           	case "Sep" : c.set(Calendar.MONTH, Calendar.SEPTEMBER);
           	break;
           	case "Oct" : c.set(Calendar.MONTH, Calendar.OCTOBER);
           	break;
           	case "Nov" : c.set(Calendar.MONTH, Calendar.NOVEMBER);
           	break;
           	case "Dec" : c.set(Calendar.MONTH, Calendar.DECEMBER);
           	break;
           }
           
           return c;
        }
            
    
	}
}
