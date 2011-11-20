/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcamlan.tcp;

import jcamlan.IOcam.Cam;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcamlan.gui.ViewFrame;

/**
 *
 * @author Pedro
 */
public class Server {

    private boolean endTransf = true;
    private Cam camera;
    private ViewFrame viewFrame;
    private CameraFlow cameraFlow;
    private Socket socket;

    /**
     * Sever Constructor create the viewFrame for Server user,
     * create a camera object
     */
    public Server() {
       
            camera = new Cam(1);
         
         try {    
            if (camera != null) {
                   viewFrame = new ViewFrame(camera);
            viewFrame.setVisible(true);
                cameraConnect();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public final void cameraConnect() throws IOException, ClassNotFoundException {
        //create socket
        ServerSocket serverSocket = new ServerSocket(7190);
        System.out.println("Aguardando conexão...");
        //accept return a socket for comunication with the near
        socket = serverSocket.accept();
        System.out.println("conexao estabelida com " + socket.getInetAddress().getHostName());
        endTransf = false;
        viewFrame.setOtherCameraExists(true);
        //start the data flow
        cameraFlow = new CameraFlow();
        //   cameraFlow.sendImage(camera, socket, endTransf);
        SendThread sendThread = new SendThread();
        sendThread.start();
        ReceivesThread receivesThread = new ReceivesThread();
        receivesThread.start();
        //  cameraFlow.receinves(socket, endTransf, viewFrame);
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
