package UDP_MULTICASTING;

import java.io.*;
import java.util.*;

import javax.lang.model.util.ElementScanner14;

import java.net.*;

public class UDPMulticastClient 
{
    public static void main(String[] args) 
    {
        System.setProperty("java.net.preferIPv4Stack", "true");    
        
        Scanner s = new Scanner(System.in);
        int choice;
        String address = null;


        do 
        {
            System.out.println("Chose the group you want to join .");
            System.out.println();

            System.out.println("1.  BSSE");
            System.out.println("2.  BSC");

            System.out.print("Choice : " );
            choice = s.nextInt();
            
        } while (choice != 1 && choice !=  2);

        
        if(choice == 1)
        address = "225.6.7.8";
        
        else if(choice == 2)
        address = "226.6.7.8";

        else 
        System.out.println("Invalid input.");


        try 
        {
            InetAddress group = InetAddress.getByName(address); /*Address that we shall use for multicasting */

            MulticastSocket mSocket = new MulticastSocket(3456);

            mSocket.joinGroup(group);           //joining the multicast group

            int i = 0;

            while(i < 10)
            {
                byte[] buffer = new byte[1000];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                mSocket.receive(packet);

                System.out.println(new String(buffer));
                i++;

            }

            mSocket.close();
            System.out.println("Socket closed");


        } catch (Exception e) { e.printStackTrace(); }
    }

}
    
