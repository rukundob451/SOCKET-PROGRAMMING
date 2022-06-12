//SERVER
package JAVA_GROUP_CHAT;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.ServerSocket;
import java.net.Socket;

public class Server 
{


    public static void main(String[] args) throws IOException
    {
        
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("WAITING FOR CLIENTS............................");
        System.out.println();

        Server server = new Server(serverSocket);
        server.startServer();
    }

    
                //DATA FIELDS

    //SERVER SOCKET
    private ServerSocket serverSocket; //server socket object that listens for incoming clients
                                        // and creates socket to communicate with them

    

                    //METHODS



            //CONSTRUCTORS

    public Server(ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
    }

            //@END CONSTRUCTORS




    //START SERVER METHOD IS RESPONSIBLE FOR KEEPING SERVER RUNNING
    public void startServer()
    {
        try 
        {
            Socket socket = serverSocket.accept();
            System.out.println("A new client has connected!");

            ClientHandler clientHandler = new ClientHandler(socket); //each object of this class will be responsible for communicating with a
                                                                     //a client, it will implement the interface runnable, each instance of 
                                                                     //this class will be handled by a different thread.


            Thread thread = new Thread(clientHandler);
            thread.start();                           //thread to handle an instance of client handler to enable it to manage clients simultaneously

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

    }


    //CLOSE SERVER SOCKET - will be used to avoid nested "try catches"; if the socket has a zib , the this method will close it

    public void closeServerSocket()
    {
        try 
        {
            if(serverSocket != null)  /*you have to check if a serverSocket object has been created otherwise, you will be 
                                        closing a null object and will get a NULLEXCEPTION*/
              serverSocket.close();
            
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    
    
}
