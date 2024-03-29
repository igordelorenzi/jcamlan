/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ViewFrame.java
 *
 * Created on 01/09/2011, 20:46:03
 */
package jcamlan.gui;

import jcamlan.tcp.ImageData;
import jcamlan.IOcam.Cam;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import jcamlan.udp.ImageDataUdp;

/**
 *
 * @author Pedro
 */
public class ViewFrame extends javax.swing.JFrame {

    private Cam camera = null;
    private BufferedImage bufferImage1, bufferImage2;
    private int camW = 320, camH = 240;
    private boolean cameraExists = false;
    private boolean udpConnection = false;
    private BufferedImage[] imageArray;

    public void setUdpConection(boolean udpConection) {
        this.udpConnection = udpConection;
        if (udpConection) {
            imageArray = new BufferedImage[9];
            for (int i = 0; i < 9; ++i) {
                imageArray[i] = new BufferedImage(camW, camH, BufferedImage.TYPE_3BYTE_BGR);
            }
        }

    }
    private ImageDataUdp imgBufPart;

    public void setImgBufPart(ImageDataUdp imgBufPart) {
        this.imgBufPart = imgBufPart;

    }

    public ImageDataUdp getImgBufPart() {
        return imgBufPart;
    }
    private boolean otherCameraExists = false;
    private ImageData imgBuf1 = new ImageData();

    /** Creates new form ViewFrame */
    public ViewFrame(Cam camera) {
        initComponents();
        this.bufferImage1 = new BufferedImage(camW, camH, BufferedImage.TYPE_3BYTE_BGR);
        if (camera != null) {
            cameraExists = true;
        }
        if (cameraExists) {
            this.camera = camera;
            this.bufferImage2 = new BufferedImage(camW, camH, BufferedImage.TYPE_3BYTE_BGR);
            ActionListener capImgAct = new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    viewPane2.repaint();
                }
            };
            camera.getVideoTimer().addActionListener(capImgAct);
        }
    }

    public void viewPaint(Graphics g, ImageData imgBuffer, BufferedImage bufferImage) {

        try {

            bufferImage = ImageIO.read(new ByteArrayInputStream(imgBuffer.getImg()));

        } catch (IOException ex) {
            Logger.getLogger(ViewFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.drawImage(bufferImage, 0, 0, camW, camH, this);



    }

    public void getImageUdp(ImageDataUdp imgBuffer) {

        char loc = imgBuffer.getLoc();

        try {

            imageArray[0] = ImageIO.read(new ByteArrayInputStream(imgBuffer.getImg()));
            /*switch (loc){
            case '1':
            xi = 107;
            break;
            
            case '2':
            xi = 214;
            break;
            
            case '3':
            yi = 80;
            break;
            
            case '4':
            xi = 107;
            yi = 80;                   
            break;
            
            case '5':
            xi = 214;
            yi = 80;        
            break;
            }*/

        } catch (IOException ex) {
            Logger.getLogger(ViewFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void viewPaintPart(Graphics g, BufferedImage bufferImage[]) {
        g.drawImage(bufferImage[0], 0, 0, 320, 340, this);
      /*  g.drawImage(bufferImage[0], 0, 0, 107, 80, this);
        g.drawImage(bufferImage[1], 107, 0, 107, 80, this);
        g.drawImage(bufferImage[2], 214, 0, 107, 80, this);
        g.drawImage(bufferImage[3], 0, 80, 107, 80, this);
        g.drawImage(bufferImage[4], 107, 80, 107, 80, this);
        g.drawImage(bufferImage[5], 214, 80, 107, 80, this);
        g.drawImage(bufferImage[6], 0, 160, 107, 80, this);
        g.drawImage(bufferImage[7], 107, 160, 107, 80, this);
        g.drawImage(bufferImage[8], 214, 160, 107, 80, this);
*/

    }

    public void setOtherImage(ImageData imgBuf) {
        this.imgBuf1 = imgBuf;
    }

    public void setOtherCameraExists(boolean otherCameraExists) {
        this.otherCameraExists = otherCameraExists;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        viewPane1 = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                if(otherCameraExists){
                    if(!udpConnection){
                        viewPaint(g,imgBuf1,bufferImage1);
                    }
                    else{
                        viewPaintPart(g, imageArray);
                    }
                }

            }
        }
        ;
        viewPane2 = new javax.swing.JPanel(){

            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                if(cameraExists)
                viewPaint(g,camera.getImageData(),bufferImage2);

            }
        }
        ;

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        viewPane1.setBackground(new java.awt.Color(0, 0, 0));
        viewPane1.setPreferredSize(new java.awt.Dimension(320, 240));

        javax.swing.GroupLayout viewPane1Layout = new javax.swing.GroupLayout(viewPane1);
        viewPane1.setLayout(viewPane1Layout);
        viewPane1Layout.setHorizontalGroup(
            viewPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        viewPane1Layout.setVerticalGroup(
            viewPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );

        viewPane2.setBackground(new java.awt.Color(0, 0, 0));
        viewPane2.setPreferredSize(new java.awt.Dimension(320, 240));

        javax.swing.GroupLayout viewPane2Layout = new javax.swing.GroupLayout(viewPane2);
        viewPane2.setLayout(viewPane2Layout);
        viewPane2Layout.setHorizontalGroup(
            viewPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        viewPane2Layout.setVerticalGroup(
            viewPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(84, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(viewPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(viewPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel viewPane1;
    private javax.swing.JPanel viewPane2;
    // End of variables declaration//GEN-END:variables

    public JPanel getViewPane1() {
        return viewPane1;
    }
}
