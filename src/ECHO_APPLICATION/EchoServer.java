package ECHO_APPLICATION;
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class EchoServer 
{
    public static void main(String[] args)  
    {

        System.out.println();
        System.out.println();
        System.out.println();

        
        try 
        {   
            ServerSocket serverSocket = new ServerSocket(1234);

            System.out.println("WAITING FOR CLIENTS.................");
            System.out.println();
            
            Socket socket = serverSocket.accept();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println("CLIENT CONNECTED");

            String messageToClient = "SERVER  :  Message received " ;
            String messageFromClient;

           //while(socket.isConnected())
         //  {
                messageFromClient =  bufferedReader.readLine();

                System.out.println("CLIENT  :  " + messageFromClient);

                bufferedWriter.write(messageToClient);
                
         //  }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        






        










        System.out.println();
        System.out.println();
        System.out.println();
    }
        
}
