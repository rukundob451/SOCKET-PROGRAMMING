package UDP_MULTICASTING;

import java.net.*;
import java.util.Scanner;

import JAVA_GROUP_CHAT.Client;


//This client will send messages and receive messages from the server
public class UDPClient 
{
    public static void main(String[] args) throws SocketException, UnknownHostException
    {
        System.setProperty("java.net.preferIPv4Stack", "true"); 

            DatagramSocket datagramSocket = new DatagramSocket();

            InetAddress inetAddress =  InetAddress.getByName("localhost");

            UDPClient udpClient = new UDPClient(datagramSocket, inetAddress);

            System.out.println("Send packets to the server");

            udpClient.sendThenReceive();
    }

    private DatagramSocket datagramSocket;
    private InetAddress  inetAddress;
    private byte[] buffer;


    public UDPClient(DatagramSocket datagramSocket , InetAddress inetAddress)
    {
        this.datagramSocket = datagramSocket;
        this.inetAddress  = inetAddress;
    }

    public void  sendThenReceive()
    {
        Scanner s = new Scanner(System.in);


        while (true)
        {
            try 
            {
                String messageToSend = s.nextLine();
                buffer = messageToSend.getBytes();     //convert the message from string to bytes

                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 1234);    //constructing UDP packet

                datagramSocket.send(datagramPacket);    //sending datagram packet

                datagramSocket.receive(datagramPacket); //receiving datagram packet from socket

                String messageFromServer = new String(datagramPacket.getData(), 0, datagramPacket.getLength()); //converting bytes to string

                System.out.println("The server says you said " + messageFromServer);
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
                break;
            }
        }
    }
}
