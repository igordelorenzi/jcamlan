/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcamlan.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcamlan.IOcam.Cam;
import jcamlan.gui.ViewFrame;

/**
 *
 * @author Pedro
 */
public class ClientUdp {

    private DatagramSocket clientDgSocket;
    private Cam camera;
    private ViewFrame viewFrame;
    private int port = 7190;
    private CameraFlowUdp cameraFlow;
    private boolean endTransf;
    private InetAddress ipAddress;
    
    public ClientUdp(String ip) throws SocketException {

            camera = new Cam();
            viewFrame = new ViewFrame(camera);
            viewFrame.setOtherCameraExists(true);
            viewFrame.setVisible(true);

        try {
            cameraFlow = new CameraFlowUdp();
            endTransf = false;
            String serverHostname = ip;
            clientDgSocket = new DatagramSocket();
            ipAddress = InetAddress.getByName(serverHostname);
            byte[] sendData = new byte[1024];
          
            //Send the requisition
            String sentence = "MACACO";
            sendData = sentence.getBytes();
            System.out.println("Sending data to " + sendData.length
                    + " bytes to server.");
            DatagramPacket sendPacket = 
            new DatagramPacket(sendData, sendData.length, ipAddress, port); 
            
            clientDgSocket.send(sendPacket);
          

           this.connect();
        } catch (SocketException ex) {
            Logger.getLogger(ClientUdp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientUdp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void connect(){
            ReceivesThread receivesThread = new ReceivesThread();
            receivesThread.start();  
            SendThread sendThread = new SendThread();
            sendThread.start();   
    }
     public class ReceivesThread extends Thread {

        @Override
        public void run() {
            try {
                cameraFlow.receinves(clientDgSocket, endTransf, viewFrame);
            } catch (IOException ex) {
                Logger.getLogger(ClientUdp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientUdp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     public class SendThread extends Thread {

  @Override
        public void run() {
            try {
                cameraFlow.sendImage(clientDgSocket, camera, endTransf, ipAddress, port);
            } catch (IOException ex) {
                Logger.getLogger(ServerUdp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     }
}
