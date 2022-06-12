package ECHO_APPLICATION;
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class EchoClient 
{
    public static void main(String[] args) 
    {
        System.out.println();
        System.out.println();
        System.out.println();
        
        System.out.println("CLIENT STARTED");
        System.out.println();

        try 
        {
            Socket socket = new Socket("localhost", 1234);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String messageToSend, messageFromServer ;
            Scanner scanner = new Scanner(System.in);

           // while (socket.isConnected()) 
            //{
                messageToSend = scanner.nextLine();
                bufferedWriter.write("CLIENT : " + messageToSend); 
                bufferedWriter.newLine();

                messageFromServer = bufferedReader.readLine();

                System.out.println(messageFromServer);
                System.out.println();
           // }
            
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
      

        
       











        System.out.println();
        System.out.println();
        System.out.println();
    }
}
