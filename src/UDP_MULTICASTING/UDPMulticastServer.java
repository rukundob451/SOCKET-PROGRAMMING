package UDP_MULTICASTING;

import java.io.*;
import java.util.*;
import java.net.*;


public class UDPMulticastServer
{
    public static void main(String[] args) 
    {
        System.setProperty("java.net.preferIPv4Stack", "true"); /* we have to prefer IPv4 since UDP multicasting 
                                                                         is no longer available with IPv6*/   

        String message1 = "This message is for  BSSE";
        String message2 = "This message is for BSC";

        
        try 
        {

            InetAddress BSC = InetAddress.getByName("226.6.7.8"); /*Address that we shall use for multicasting */
            InetAddress BSSE = InetAddress.getByName("225.6.7.8"); /*Address that we shall use for multicasting */

            MulticastSocket socket = new MulticastSocket(); //creating a multicast socket

            DatagramPacket packet = new DatagramPacket(message1.getBytes(), message1.length(), BSSE, 3456);
            socket.send(packet);

            DatagramPacket packet_two = new DatagramPacket(message2.getBytes(), message2.length(), BSC, 3456);
            socket.send(packet_two);

            socket.close();
        } catch (Exception e) {e.printStackTrace();}
           
        
        


    }
}
