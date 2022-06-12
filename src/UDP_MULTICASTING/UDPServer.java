package UDP_MULTICASTING;

import java.net.*;
import java.util.Scanner;
import java.net.SocketException;

//This client will send and receive messages from the client
public class UDPServer 
{
    public static void main(String[] args) throws SocketException
    {
        DatagramSocket  datagramSocket = new DatagramSocket(1234);

        UDPServer udpServer = new UDPServer(datagramSocket);
    }

    private DatagramSocket datagramSocket;
    private byte[] buffer = new byte[256];

    public UDPServer(DatagramSocket datagramSocket)
    {
        this.datagramSocket = datagramSocket;

        while (true) 
        {   
            try
            {   

                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                
                datagramSocket.receive(datagramPacket); //receiving datagram packet

                InetAddress inetAddress = datagramPacket.getAddress();  //extracting Ip address from the datagram packet
                
                int port = datagramPacket.getPort();    //extracting the port number from the datagram packet

                String messageFromClient = new String(datagramPacket.getData(), 0, datagramPacket.getLength()); //converting bytes to string

                System.out.println("Message from client " + messageFromClient);

                datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);      //creating new UDP packet

                datagramSocket.send(datagramPacket);




                
            } catch (Exception e) 
            {
                e.printStackTrace();
            }
            
        }
    }
}
