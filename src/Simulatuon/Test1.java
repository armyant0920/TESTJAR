package Simulatuon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Test1 {

    private static final String dir="src/ImageFile/";
    public static void main(String[] args) {
        String[] readFormats = ImageIO.getReaderFormatNames(); // 可讀入的格式
        String[] writerFormatNames = ImageIO.getWriterFormatNames(); // 可輸出的格式
        System.out.println("可讀入的圖片格式：" + Arrays.asList(readFormats));
        System.out.println("可輸出的圖片格式：" + Arrays.asList(writerFormatNames));

        //讀入圖片
        File imgFile = new File(dir+"cat.png");
        try {
            BufferedImage bufferedImage = ImageIO.read(imgFile); // 讀入檔案轉為 BufferedImage 物件
            int exportWidth=bufferedImage.getWidth()/2;
            int exportHeight=bufferedImage.getHeight()/2;
            BufferedImage emptyImage=new BufferedImage(exportWidth,exportHeight,BufferedImage.TYPE_INT_ARGB);

            Graphics2D graphics2D=emptyImage.createGraphics();
            Random rnd=new Random();
            graphics2D.setBackground(new Color(rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256)));
            graphics2D.clearRect(0,0,exportWidth,exportHeight);//奇妙...要先清除一個矩形範圍背景色才看到的,那表示有預設?
            graphics2D.drawImage(bufferedImage,0,0,exportWidth,exportHeight,null);
            System.out.println(graphics2D.getBackground());

            graphics2D.dispose();
            String imageType=".png";
            FileOutputStream os=new FileOutputStream(dir+new Date().getTime()+imageType);
            ImageIO.write(emptyImage,"png",os);

        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
