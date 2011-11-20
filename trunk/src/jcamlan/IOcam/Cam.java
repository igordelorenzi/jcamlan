/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcamlan.IOcam;

import JMyron.JMyron;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import jcamlan.tcp.ImageData;
import jcamlan.udp.ImageDataUdp;

/**
 *
 * @author Pedro
 */
public class Cam {

    JMyron m;//a camera object
    int width = 160;
    int height = 120;
    int frameRate = 600; //fps
    private Timer videoTimer;

    public Cam() {
        img = new int[width * height];

        m = new JMyron();//make a new instance of the object
        m.start(width, height);//start a capture at 320x240
        videoTimer = new Timer(1000 / frameRate, new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                m.update();//update the camera view
                img = m.image(); //get the normal image of the camera

            }
        });

        videoTimer.start();
    }

    public Cam(int a) {
        img = new int[width * height];

        for (int i = 0; i < img.length; ++i) {
            img[i] = -255;
        }
        videoTimer = new Timer(1000 / frameRate, new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
            }
        });

        videoTimer.start();
    }

    public int getFrameRate() {
        return frameRate;
    }

    public int getHeight() {
        return height;
    }

    public Timer getVideoTimer() {
        return videoTimer;
    }

    public int getWidth() {
        return width;
    }

    public ImageData getImageData() {
        ++count;
        if (img != null) {

            bufferImage.setRGB(0, 0, width, height, img, 0, width);
            try {
               // this.processImg();
                
                baos.reset();
                ImageIO.write(bufferImage, "jpg", baos);
                return new ImageData(count, baos.toByteArray());
            } catch (IOException ex) {
                Logger.getLogger(Cam.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }

        } else {
            return null;
        }
    }
    public byte[] getImageDataUdp() {
       
            
        if (img != null) {
            BufferedImage imagePeace = null;
            bufferImage.setRGB(0, 0, width, height, img, 0, width);
       /*     if(part == '0')
                imagePeace = bufferImage.getSubimage(0, 0, 54, 40);
            else if(part == '1')
                imagePeace = bufferImage.getSubimage(54, 0, 54, 40);
            else if(part == '2')
                imagePeace = bufferImage.getSubimage(107, 0, 53, 40);
            else if(part == '3')
                imagePeace = bufferImage.getSubimage(0, 40, 56, 40);
            else if(part == '4')
                imagePeace = bufferImage.getSubimage(54, 40, 54, 40);            
            else if(part == '5')
                imagePeace = bufferImage.getSubimage(107, 40, 53, 40);              
            else if(part == '6')
                imagePeace = bufferImage.getSubimage(0, 80, 54, 40);
            else if(part == '7')
                imagePeace = bufferImage.getSubimage(54, 80, 54, 40);            
            else if(part == '8')
                imagePeace = bufferImage.getSubimage(107, 80, 53, 40);  */
            try {                           
                baos.reset();
                ImageIO.write(bufferImage, "jpg", baos);
               
                return baos.toByteArray();
            } catch (IOException ex) {
                Logger.getLogger(Cam.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }

        } else {
            return null;
        }
    }
    public int[] getImg() {
        return img;
    }

    public void processImg() {
        int[] rgb = new int[3];
        int tmp;
        WritableRaster raster = bufferImage.getRaster();

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                rgb = raster.getPixel(x, y, rgb);
                if (rgb[0] >= 135 && rgb[0] <= 155 
                   && rgb[1] >= 165 && rgb[1] <= 175
                   && rgb[2] >= 185 && rgb[2] <= 225){
                    tmp = rgb[2];
                    rgb[2] = rgb[1] + 100;
                    rgb[0] = rgb[2] + 50;
                    rgb[1] = tmp - 50;
               

                    raster.setPixel(x, y, rgb);
                  
                                
                }

            }
        }
         bufferImage.setData(raster);
    }
    private int count = 0;
    private int[] img;
    private BufferedImage bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();

}
