/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcamlan.udp;

import java.io.Serializable;

/**
 *
 * @author Pedro
 */
public class ImageDataUdp implements Serializable{
    private char loc;
    private byte[] byteArray;
    
    public ImageDataUdp(byte[] byteArray){
        this.byteArray = byteArray;
     
    }
    public ImageDataUdp(){
        
    }
    public void setImg(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public void setLoc(char loc) {
        this.loc = loc;
    }

    public byte[] getImg() {
        return byteArray;
    }

    public char getLoc() {
        return loc;
    }
   
}
