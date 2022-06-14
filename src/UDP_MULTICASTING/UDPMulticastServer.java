package UDP_MULTICASTING;

import java.io.*;
import java.util.*;

import javax.swing.GroupLayout.Group;

import java.net.*;
import java.math.BigInteger;


public class UDPMulticastServer
{
    public static void main(String[] args) throws IOException
    {
        System.setProperty("java.net.preferIPv4Stack", "true"); /* we have to prefer IPv4 since UDP multicasting 
                                                                         is no longer available with IPv6*/   

       

        System.out.println("WAITING FOR SUPER CLIENT TO CONNECT......");
        System.out.println();
        System.out.println("-----------GROUPS----------");
        System.out.println();

         

         byte[] buffer = new byte[1000];
         byte[] buffer1 = new byte[1000];
        
         ArrayList<String> groups = new ArrayList<>();
         int timeInterval  = 0;
         DatagramSocket  datagramSocketi = new DatagramSocket(2222);

        
        
            while(true)
            {


                try 
                {

                        DatagramPacket datagramPacketi = new DatagramPacket(buffer, buffer.length);

                        datagramSocketi.receive(datagramPacketi);

                        String group = new String(datagramPacketi.getData(), 0, datagramPacketi.getLength());

                        if (group.charAt(0) == 'X')
                        {
                            //datagramSocketi.close();
                            break;
                        }
                        else 
                        {
                            groups.add(group);
                            //datagramSocketi.close();
                        }
                        
                         if(groups.size() == 4)
                        {
                            break;
                        }  
                        
                        
                        
                       
                        
                        
                        
                        
                        
                    } catch (Exception e) {e.printStackTrace();  break;}
                    
                    
                    
                    
                }


            for (String string : groups) 
                {
                    
                    System.out.print(string);
                    System.out.println();
                }

                System.out.println();
                      
                DatagramPacket datagramPacket = new DatagramPacket(buffer1, buffer1.length);
                datagramSocketi.receive(datagramPacket);   
                  //receiving the time interval

               // datagramSocketi.close();   to be removed once the i confirmed that the same buffer can be reused

                String messageFromClient = new String(datagramPacket.getData(), 0, datagramPacket.getLength());


            System.out.println();
            System.out.println("Time interval between sending of messages  : " + messageFromClient);
            System.out.println();
            
            
            System.out.println();
            System.out.println("DONE GETTING GROUPS ..........  WAITING FOR CLIENTS");
            System.out.println();
                
                byte[] buffer5 = new byte[1000];

                datagramPacket = new DatagramPacket(buffer5, buffer5.length);

                datagramSocketi.receive(datagramPacket);

                InetAddress clientAdress = datagramPacket.getAddress();
                int clientPort = datagramPacket.getPort();

                int i = 0;

                //sending the client the groups

                for(i = 0; i < 4; i++)
                {
                    byte[] nbuffer = new byte[1000];

                    nbuffer = groups.get(i).getBytes();

                    datagramPacket = new DatagramPacket(nbuffer, nbuffer.length, clientAdress, clientPort);

                    datagramSocketi.send(datagramPacket);
                }
         
                while (true) 
                {
                    
                        byte b[] = new byte[256];
                        DatagramPacket d = new DatagramPacket(b, b.length);
                        datagramSocketi.receive(d);
                    
                        

                        System.out.println();
                        System.out.println("DATAGRAM SOCKET CLOSED , OPENING MULTICAST SOCKET.................");
                        System.out.println();

                        InetAddress BSSE = InetAddress.getByName("225.6.7.8"); /*Address that we shall use for multicasting */
                        InetAddress BSC = InetAddress.getByName("226.6.7.8"); /*Address that we shall use for multicasting */
                        InetAddress BLIS = InetAddress.getByName("227.6.7.8"); /*Address that we shall use for multicasting */
                        InetAddress MATH = InetAddress.getByName("228.6.7.8"); /*Address that we shall use for multicasting */

                        String bsseMessage = "Welcome to the BSSE group";  
                        String bscMessage = "Welcome to the BSSE group";  
                        String blisMessage = "Welcome to the BSSE group";  
                        String mathMessage = "Welcome to the BSSE group";  

                        MulticastSocket socket = new MulticastSocket(); //creating a multicast socket
                        
                        System.out.println("Sending to bsse");
                        DatagramPacket bssePacket = new DatagramPacket(bsseMessage.getBytes(), bsseMessage.length(), BSSE, 5648);
                        socket.send(bssePacket);

                        System.out.println("Sending to bcs");
                        DatagramPacket bscPacket = new DatagramPacket(bscMessage.getBytes(), bscMessage.length(), BSC, 5648);
                        socket.send(bscPacket);

                        System.out.println("Sending to blis");
                        DatagramPacket blisPacket = new DatagramPacket(blisMessage.getBytes(), blisMessage.length(), BLIS, 5648);
                        socket.send(blisPacket);

                        System.out.println("Sending to math");
                        DatagramPacket packet = new DatagramPacket(mathMessage.getBytes(), mathMessage.length(), MATH, 5648);
                        socket.send(packet);
                }
            


        

          // datagramSocketi.close();
        
        


    }
}
