/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import jcamlan.tcp.Client;

/**
 *
 * @author Pedro
 */
public class Main3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println (System.getProperty ("java.library.path"));
        Client client = new Client("192.168.1.100");
    }

}
