/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcamlan.udp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import jcamlan.IOcam.Cam;
import jcamlan.gui.ViewFrame;

/**
 *
 * @author Pedro
 */
public class CameraFlowUdp implements Serializable {

    private byte[] receiveData = new byte[4000];
    private byte[] sendData = new byte[4000];
    private int seq, oldSeq;

    public void sendImage(final DatagramSocket socket, final Cam camera, final boolean endTrans,
            final InetAddress IPAddress, final int port) throws IOException {
        ActionListener sendAct = new ActionListener() {

            int seq = 0;
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            final ObjectOutputStream out = new ObjectOutputStream(bos);

            public void actionPerformed(ActionEvent evt) {
                byte[] img = null;
                try {

                    if (!endTrans) {


                        do {

                            img = camera.getImageDataUdp();

                        } while (img == null);
                        if (img.length <= 3995) {
                            System.arraycopy(img, 0, sendData, 4, img.length);

                            System.arraycopy(intToByteArray(seq), 0, sendData, 0, 4);
                            DatagramPacket sendPacket =
                                    new DatagramPacket(sendData, sendData.length, IPAddress, port);
                            socket.send(sendPacket);
                        }
                        ++seq;
                    } else {
                        bos.close();
                        out.close();
                        socket.close();
                    }

                } catch (IOException ex) {
                    //System.out.println("PRAW");
                }

            }
        };

        camera.getVideoTimer().addActionListener(sendAct);
    }

    public void receinves(DatagramSocket socket, boolean endTransf, ViewFrame viewFrame) throws IOException, ClassNotFoundException {
        ImageDataUdp buffImg = null;
        byte[] seqNumber = new byte[4];
        DatagramPacket receivePacket =
                new DatagramPacket(receiveData, receiveData.length);
        System.out.println(socket.getPort());
        socket.receive(receivePacket);
        System.out.println("recebeu");
        viewFrame.setUdpConection(true);
        
            System.arraycopy(seqNumber, 0, receivePacket.getData(), 0, 4);
            seq =  byteArrayToInt(seqNumber);
        byte[] img = new byte[receivePacket.getData().length - 4];
        System.arraycopy(receivePacket.getData(), 4, img, 0, img.length);
        buffImg = new ImageDataUdp(img);
        viewFrame.setImgBufPart(buffImg);

        while (!endTransf) {
            oldSeq = seq;
            socket.receive(receivePacket);
            System.arraycopy(receivePacket.getData(), 0, seqNumber, 0, 4);
            seq =  byteArrayToInt(seqNumber);
            img = new byte[receivePacket.getData().length - 4];
            System.arraycopy(receivePacket.getData(), 4, img, 0, img.length);
            buffImg = new ImageDataUdp(img);
            if (receivePacket.getData() != null) {
                viewFrame.setImgBufPart(buffImg);
                viewFrame.getImageUdp(buffImg);
                viewFrame.getViewPane1().repaint();
               
                if (seq - oldSeq != 1) {
                    System.out.println("perdeu "+(seq - oldSeq - 1)+" frames");
                }
            }
        }


        socket.close();

    }

    public static byte[] intToByteArray(int value) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return b;
    }

    public int byteArrayToInt(byte[] b) {
        return (b[0] << 24)
                + ((b[1] & 0xFF) << 16)
                + ((b[2] & 0xFF) << 8)
                + (b[3] & 0xFF);
    }
}
