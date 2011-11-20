/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcamlan.tcp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import jcamlan.IOcam.Cam;
import jcamlan.gui.ViewFrame;

/**
 *
 * @author Pedro
 */
public class CameraFlow {

    public void sendImage(final Cam camera, final Socket socket, final boolean endTrans) throws IOException {
      
        final OutputStream os = socket.getOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(os);
        
       
        ActionListener sendAct = new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                try {
                    if (!endTrans) {
                        ImageData bufferImg = null;
                        //ImageBuffer bufferImgOld = ImageBuffer.createEmpty();
                        do {
                            bufferImg = camera.getImageData();
                            //Compression.compress(bufferImgOld, bufferImg);
                           /// bufferImgOld = bufferImg;
                        } while (camera.getImageData() == null);
                        oos.writeObject(bufferImg);
                    } else {
                        oos.close();
                        os.close();
                        socket.close();
                    }

                } catch (IOException ex) {

                    //System.out.println("PRAW");
                }

            }
        };

        camera.getVideoTimer().addActionListener(sendAct);

    }

    public void receinves(Socket socket, boolean endTransf, ViewFrame viewFrame) throws IOException, ClassNotFoundException {
        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        //TimeEx timeEx = new TimeEx();
        //timeEx.initTest(200);
        ImageData bufferImg = (ImageData) ois.readObject();
        while (!endTransf) {
            bufferImg = (ImageData) ois.readObject();
            //timeEx.addSample();
            viewFrame.setOtherImage(bufferImg);
            viewFrame.getViewPane1().repaint();
        }

        ois.close();
        is.close();
        socket.close();


    }
}
