package UDP_MULTICASTING;

import java.math.BigInteger;
import java.net.*;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner14;

import java.lang.*;

public class UDPSuperClient /* In charge of creating groups that other clients can join and 
                               configuring the time btn messages to be sent the various groups*/ 
{
    
    public static void main(String[] args) throws SocketException, UnknownHostException
    {
        Scanner s = new Scanner(System.in);
        System.setProperty("java.net.preferIPv4Stack", "true"); 
        int numOfGroups = 0;


        try 
        {
            

            DatagramSocket datagramSocket = new DatagramSocket();

            InetAddress inetAddress =  InetAddress.getByName("localhost");


            System.out.println();

            System.out.println("Create atmost 4 groups below . Enter X when done. ");
            System.out.println();
            
            while(true)
            {
           
            String group = s.nextLine();

            byte[] buffer = group.getBytes();

            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 2222);

            datagramSocket.send(datagramPacket);
            numOfGroups++;  //counting the number of groups
            
            
            System.out.println();
             
             if(numOfGroups == 4)
             {
                System.out.println();
                System.out.println("You can't create more than 4 groups.");
                System.out.println();
                break;
             }
           
            

             if(group.charAt(0) == 'X')
             {
             numOfGroups--;     //the iteration where the client choses X shouldn't be considered a group creation iteration
             break;
             }
             
            

            }
            
            System.out.println();
            System.out.print("After what time intervals should clients receive messages in their groups :   ");
            
            String timeInterval = s.nextLine();
    
            byte[] buffer = timeInterval.getBytes();

            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 2222);
                                                //constructing new datagramPacket

            datagramSocket.send(datagramPacket);        //sending datagram to server
            datagramSocket.close();

            System.out.println();
            System.out.println();

            System.out.println("--------------------------- " + numOfGroups + " GROUPS CREATED WITH SENDING TIME INTERVAL OF " + timeInterval + " SECONDS --------------------------------------");

            System.out.println();
            System.out.println();
            System.out.println();
            

        
        
        } catch (Exception e)
        {
            e.printStackTrace();
            
        }

           
            
    }


}
    