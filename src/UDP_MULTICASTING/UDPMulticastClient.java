package UDP_MULTICASTING;

import java.io.*;
import java.util.*;

import javax.lang.model.util.ElementScanner14;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import java.net.*;

public class UDPMulticastClient 
{
    public static void main(String[] args) throws IOException
    {
        System.setProperty("java.net.preferIPv4Stack", "true");    
        
        Scanner s = new Scanner(System.in);
        int choice;
        String address = null;
        String clientGreeteServer = "Hello , I would like to be assigned a group";

        DatagramSocket datagramSocket = new DatagramSocket(); //creating a socket for client
        byte[] buffer = new byte[1000];
        String groupName = null;
        

        

        //sending the server first message such that it can knw the sender's ip address and port number
        buffer = clientGreeteServer.getBytes();

        InetAddress inetAddress1 =  InetAddress.getByName("localhost");

        DatagramPacket datagramPacket1 = new DatagramPacket(buffer, buffer.length, inetAddress1, 2222);
        datagramSocket.send(datagramPacket1);  //end of sending to server handshake


         DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        // datagramSocket.receive(datagramPacket);         //receiving the number of groups from server
        
        // String numberOfGroupsString = new String(datagramPacket.getData(), 0, datagramPacket.getLength());

        // int numberOfGroups = Integer.parseInt(numberOfGroupsString);

        ArrayList<String> groups = new ArrayList<>();

        for(int i = 0; i < 4; i++)  //receiving the group names from the server
        {
            //will using the same buffer cause error
            byte[] bufferx = new byte[1000];

            DatagramPacket d = new DatagramPacket(bufferx, bufferx.length);

            datagramSocket.receive(d); 
            
            groupName = new String(d.getData(), 0, d.getLength());

            groups.add(groupName);
        }

       // datagramSocket.close();  will leaving the socket open while opeing multicast socket on the same client cause problem
        
        do  //printing groups for the client to join
        {
            System.out.println();
            System.out.println();
            System.out.println("Choose the group you want to join .");
            System.out.println();

            int i = 0, k = 1;

            for (String string : groups) 
            {
                System.out.print((k++) + " " + groups.get(i++));
                System.out.println();
            }

            System.out.print("Choice : " );
            choice = s.nextInt();
            
        } while (choice < 1 || choice > 4 ); //use the numberOfGroups variable

       // giving out multicast group addresses depending on the group chosen
        if(choice == 1)
        address = "225.6.7.8";  
        else if(choice == 2)
        address = "226.6.7.8";
        else if(choice == 3)
        address = "227.6.7.8";
        else if(choice == 2)
        address = "228.6.7.8";
        else 
        System.out.println("Invalid input.");

        


        try 
        {
            InetAddress group = InetAddress.getByName(address); /*Address that we shall use for multicasting */

            MulticastSocket mSocket = new MulticastSocket(5648);

            mSocket.joinGroup(group);           //joining the multicast group

            byte[] b = (Integer.toString(choice)).getBytes();
            DatagramPacket p = new DatagramPacket(b, b.length, inetAddress1, 2222);
            datagramSocket.send(p);

            datagramSocket.close();

          
                byte[] buffer2 = new byte[1000];
                DatagramPacket packet = new DatagramPacket(buffer2, buffer2.length);
                mSocket.receive(packet);

                System.out.println(new String(buffer2));
               // i++;

            

            mSocket.close();


        } catch (Exception e) { e.printStackTrace(); }
    }

}
    
