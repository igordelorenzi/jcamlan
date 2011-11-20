/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcamlan.tcp;

import jcamlan.gui.ViewFrame;
import jcamlan.tcp.Server;
import jcamlan.tcp.CameraFlow;
import jcamlan.IOcam.Cam;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro
 */
public class Client {

    private Cam camera;
    private ViewFrame viewFrame;
    private boolean endTransf;
    private CameraFlow cameraFlow;
    private Socket socket;
    private String ipAdress;

    /**
     *  Sever Constructor create the viewFrame for Server user,
     * create a camera object
     */
    public Client(String ipAdress) {
        camera = new Cam();
        viewFrame = new ViewFrame(camera);
        viewFrame.setVisible(true);
             this.ipAdress = ipAdress;
        System.out.println(ipAdress+this.ipAdress);
        try {
            cameraConnect();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }

    public final void cameraConnect() throws IOException, ClassNotFoundException {
        //create socket
        System.out.println("tentando");
        //conecting to server
        System.out.println(ipAdress);
        socket = new Socket(ipAdress, 7190);
        //start the data flow
        endTransf = false;
        cameraFlow = new CameraFlow();
        viewFrame.setOtherCameraExists(true);
        //start the data flow
        // cameraFlow.receinves(socket, endTransf, viewFrame);
        // cameraFlow.sendImage(camera, socket, endTransf);
        ReceivesThread receivesThread = new ReceivesThread();
        receivesThread.start();
        SendThread sendThread = new SendThread();
        sendThread.start();
    }

    public class SendThread extends Thread {

        @Override
        public void run() {
            try {
                cameraFlow.sendImage(camera, socket, endTransf);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public class ReceivesThread extends Thread {

        @Override
        public void run() {
            try {
                cameraFlow.receinves(socket, endTransf, viewFrame);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
