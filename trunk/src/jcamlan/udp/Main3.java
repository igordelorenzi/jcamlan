/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jcamlan.udp;

import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Pedro
 */
public class Main3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
     
            ClientUdp client = new ClientUdp("192.168.1.102");
        } catch (SocketException ex) {
            Logger.getLogger(Main3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
