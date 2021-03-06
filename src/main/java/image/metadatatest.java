package image;

import java.io.File;
import java.text.SimpleDateFormat; 
import java.util.Date; 
import com.drew.imaging.ImageMetadataReader; 
import com.drew.metadata.Directory; 
import com.drew.metadata.Metadata; 
import com.drew.metadata.Tag; 
import com.drew.metadata.exif.ExifIFD0Directory; 
import com.drew.metadata.exif.ExifSubIFDDirectory;

public class metadatatest {
	
    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\Simon\\Desktop\\2014\\DSC_0801.JPG";
        getInfo(path);
    }
    
    public static void getInfo(String path){
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
                System.out.println(tagName + " " + desc + " " + directName);
                if (tagName.equals("GPS Latitude")) {
                    System.err.println("纬度 : " + desc);
                    // System.err.println("纬度(度分秒格式) : "+pointToLatlong(desc));
                } else if (tagName.equals("GPS Longitude")) {
                    System.err.println("经度: " + desc);
                    // System.err.println("经度(度分秒格式): "+pointToLatlong(desc));
                } else if (tagName.equals("GPS Altitude")) {
                    System.err.println("海拔: " + desc);
                } else if (tagName.equals("Date/Time Original")){
                        SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                        Date date = yyyymmddhhmmss.parse(desc);
                        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        System.out.println("拍照时间: " + newFormat.format(date));
                } else if (tagName.equals("Date/Time")){
                    SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                    Date date = yyyymmddhhmmss.parse(desc);
                    SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    System.out.println("创建时间: " + newFormat.format(date));
                } else if (tagName.equals("Image Width")){
                    System.err.println("宽: " + Integer.parseInt(desc.replaceAll("[^0-9]", "").trim()));
                } else if (tagName.equals("Image Height")){
                    System.err.println("高: " + Integer.parseInt(desc.replaceAll("[^0-9]", "").trim()));
                } else if (tagName.equals("X Resolution")){
                    System.err.println("水平分辨率: " + Integer.parseInt(desc.replaceAll("[^0-9]", "").trim()));
                } else if (tagName.equals("Y Resolution")){
                    System.err.println("垂直分辨率: " + Integer.parseInt(desc.replaceAll("[^0-9]", "").trim()));
                }
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_FNUMBER)){
                //光圈F值=镜头的焦距/镜头光圈的直径
                System.out.println("光圈值："+directory.getDescription(ExifSubIFDDirectory.TAG_FNUMBER));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_EXPOSURE_TIME)){
                System.out.println("曝光时间: " + directory.getString(ExifSubIFDDirectory.TAG_EXPOSURE_TIME)+ "秒");
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT)){
                System.out.println("ISO速度: " + directory.getString(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_FOCAL_LENGTH)){
                System.out.println("焦距: " + directory.getString(ExifSubIFDDirectory.TAG_FOCAL_LENGTH)+ "毫米");
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_MAX_APERTURE)){
                System.out.println("最大光圈：" + directory.getDouble(ExifSubIFDDirectory.TAG_MAX_APERTURE));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_EXIF_IMAGE_WIDTH)){
                System.out.println("宽: " + directory.getString(ExifIFD0Directory.TAG_EXIF_IMAGE_WIDTH));//ExifIFD0Directory
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_EXIF_IMAGE_HEIGHT)){
                System.out.println("高: " + directory.getString(ExifSubIFDDirectory.TAG_EXIF_IMAGE_HEIGHT));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_MAKE)){
                System.out.println("照相机制造商: " + directory.getString(ExifSubIFDDirectory.TAG_MAKE));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_MODEL)){
                System.out.println("照相机型号: " + directory.getString(ExifSubIFDDirectory.TAG_MODEL));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_X_RESOLUTION)){
                System.out.println("水平分辨率(X方向分辨率): " + directory.getString(ExifSubIFDDirectory.TAG_X_RESOLUTION));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_Y_RESOLUTION)){
                System.out.println("垂直分辨率(Y方向分辨率): " + directory.getString(ExifSubIFDDirectory.TAG_Y_RESOLUTION));
            }
            //其他参数测试开始
            if(directory.containsTag(ExifSubIFDDirectory.TAG_SOFTWARE)){
                //Software软件 显示固件Firmware版本
                System.out.println("显示固件Firmware版本(图片详细信息的程序名称):" + directory.getString(ExifSubIFDDirectory.TAG_SOFTWARE));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_35MM_FILM_EQUIV_FOCAL_LENGTH)){
                System.out.println("35mm焦距:" + directory.getString(ExifSubIFDDirectory.TAG_35MM_FILM_EQUIV_FOCAL_LENGTH));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_APERTURE)){
                System.out.println("孔径(图片分辨率单位):" + directory.getString(ExifSubIFDDirectory.TAG_APERTURE));
            } else {
//              System.out.println("孔径");
            }

            if(directory.containsTag(ExifSubIFDDirectory.TAG_ARTIST)){
                //作者
                System.out.println("作者：" + directory.getString(ExifSubIFDDirectory.TAG_ARTIST));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_BODY_SERIAL_NUMBER)){
                System.out.println("TAG_BODY_SERIAL_NUMBER：" + directory.getString(ExifSubIFDDirectory.TAG_BODY_SERIAL_NUMBER));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_METERING_MODE)){
                //MeteringMode测光方式， 平均式测光、中央重点测光、点测光等
                System.out.println("点测光值：" + directory.getString(ExifSubIFDDirectory.TAG_METERING_MODE));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_RESOLUTION_UNIT)){
                //XResolution/YResolution X/Y方向分辨率
                System.out.println("分辨率单位：" + directory.getString(ExifSubIFDDirectory.TAG_RESOLUTION_UNIT));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_EXPOSURE_BIAS)){
                System.out.println("曝光补偿：" + directory.getDouble(ExifSubIFDDirectory.TAG_EXPOSURE_BIAS));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_COLOR_SPACE)){
                System.out.println("色域、色彩空间：" + directory.getString(ExifSubIFDDirectory.TAG_COLOR_SPACE));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_YCBCR_COEFFICIENTS)){
                System.out.println("色相系数：" + directory.getString(ExifSubIFDDirectory.TAG_YCBCR_COEFFICIENTS));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_YCBCR_POSITIONING)){
                System.out.println("色相定位：" + directory.getString(ExifSubIFDDirectory.TAG_YCBCR_POSITIONING));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_YCBCR_SUBSAMPLING)){
                System.out.println("色相抽样：" + directory.getString(ExifSubIFDDirectory.TAG_YCBCR_SUBSAMPLING));
            }
            if(directory.containsTag(ExifSubIFDDirectory.TAG_EXIF_VERSION)){
                System.out.println("exif版本号：" + directory.getString(ExifSubIFDDirectory.TAG_EXIF_VERSION));
            }
        }  
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 经纬度格式 转换为 度分秒格式 ,如果需要的话可以调用该方法进行转换
     * 
     * @param point
     *            坐标点
     * @return
     */
    public static String pointToLatlong(String point) {
        Double du = Double.parseDouble(point.substring(0, point.indexOf("°")).trim());
        Double fen = Double.parseDouble(point.substring(point.indexOf("°") + 1, point.indexOf("‘")).trim());
        Double miao = Double.parseDouble(point.substring(point.indexOf("‘") + 1, point.indexOf("\"")).trim());
        Double duStr = du + fen / 60 + miao / 60 / 60;
        return duStr.toString();
    }

}
