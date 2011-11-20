/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcamlan.tcp;
import java.io.Serializable;

/**
 *
 * @author Pedro
 */
public class ImageData implements Serializable {

   //  private transient BufferedImage bufferedImage;
     private byte[] bytearray;

     private int id;

     public ImageData(int id, byte[] bytearray){
         this.id = id;
         this.bytearray = bytearray;
     }

    public ImageData() {
        id = -1;
        bytearray = new byte[320*240]; 
    }
     
    public int getId(){
        return id;
    }
    
    /*public static ImageBuffer createEmpty(){
        ImageBuffer imgBuffer = new ImageBuffer();
        int[] img = new int[160*120];
        for(int i = 0; i  < 160*120; ++i){
            img[i] = 0;
        }
        BufferedImage buffer = new BufferedImage(160, 120, BufferedImage.TYPE_3BYTE_BGR);
        buffer.setRGB(0, 0, 160, 120, img, 0, 160);
        imgBuffer.setImg(buffer);
        return imgBuffer;
    }*/

    public void setImg(byte[] bytearray) {
        this.bytearray = bytearray;
    }

    public byte[] getImg() {
        return bytearray;
    }
}
