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
import java.util.logging.Level;
import java.util.logging.Logger;
import jcamlan.IOcam.Cam;
import jcamlan.gui.ViewFrame;
import jcamlan.tcp.Server;

/**
 *
 * @author Pedro
 */
public class ServerUdp {

    private DatagramSocket serverDgSocket;
    private Cam camera;
    private CameraFlowUdp cameraFlow;
    private boolean endTransf = true;
    private int port = 7190;
    private InetAddress ipAddress;
    private final ViewFrame viewFrame;

    public ServerUdp() {
        
            camera = new Cam();
            viewFrame = new ViewFrame(camera);
            viewFrame.setOtherCameraExists(true);
            viewFrame.setVisible(true);
        byte[] receiveData = new byte[1024]; 
        try {
            serverDgSocket = new DatagramSocket(port);
            cameraFlow = new CameraFlowUdp();
            endTransf = false;
            receiveData = new byte[1024]; 

           DatagramPacket receivePacket = 
             new DatagramPacket(receiveData, receiveData.length); 

           System.out.println ("Waiting for datagram packet");
            try {
            
                serverDgSocket.receive(receivePacket);
            } catch (IOException ex) {
                Logger.getLogger(ServerUdp.class.getName()).log(Level.SEVERE, null, ex);
            }
            String sentence = new String(receivePacket.getData());
            System.out.println(sentence);
            port = receivePacket.getPort();
            ipAddress = receivePacket.getAddress();
            System.out.println(ipAddress.toString());
            connect();
        } catch (SocketException ex) {
            Logger.getLogger(ServerUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void connect(){
            SendThread sendThread = new SendThread();
            sendThread.start();      
            ReceivesThread receivesThread = new ReceivesThread();
            receivesThread.start();  
    }
    public class SendThread extends Thread {

        @Override
        public void run() {
            try {
                cameraFlow.sendImage(serverDgSocket, camera, endTransf, ipAddress, port);
            } catch (IOException ex) {
                Logger.getLogger(ServerUdp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public class ReceivesThread extends Thread {
        
        @Override
        public void run() {
            try {
                cameraFlow.receinves(serverDgSocket, endTransf, viewFrame);
            } catch (IOException ex) {
                Logger.getLogger(ClientUdp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientUdp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
