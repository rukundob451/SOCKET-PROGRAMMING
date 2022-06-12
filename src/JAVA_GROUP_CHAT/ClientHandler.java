//CLIENT_HANDLER

package JAVA_GROUP_CHAT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;

public class ClientHandler implements Runnable  /*This class will not contain a main method as it will be run by the thread.start()
                                                  line that is in the Server.java class */
{
            //DATA FIELDS

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();  //this array will contain all the clients
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;



            //METHODS

        //CONSTRUCTOR
    public ClientHandler(Socket socket)
    {
        try 
        {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + " has joined the chat!");  //sent to everyone who is on the chat.
            
        } catch (Exception e) 
        {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
        //@endconstructor
      

        //RUN
        /*Everything is this method is what is run on a separate thread, we shall use this to listen for messages , since it's a 
          it's a blocking operation and we don't want our program to get stuck while waiting for messages  .   This will allow us
          to send messages to other clients even without waiting to get one.*/
      @Override
      public void run()
      {
        String messageFromClient;

        while(socket.isConnected())
        {
            try 
            {
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);

            } catch (Exception e) 
            {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            } 
        }
      }


      //BROADCAST MESSAGE
      /* This method is in charge of sending the message to every other connected client*/

      public void broadcastMessage(String messageToSend) 
      {
          for (ClientHandler clientHandler : clientHandlers) 
          {
              try 
              {
                if(!clientHandler.clientUsername.equals(clientUsername)) //we don't want the sending client to also receive message
                {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();     /*We need to flush coz once the buffered writer starts receiving a stream,
                                                                it will only send it when the buffer is full and yet some of the 
                                                                messages we shall won't be able to fill up the stream */

                    

                }

              } catch (Exception e) 
              {
                  closeEverything(socket, bufferedReader, bufferedWriter);
              }
              
          }
      }


      //REMOVE CLIENT
      /*This method will remove the client and then send out broadcast message informing other clients */

      public void removeClient()
      {
          clientHandlers.remove(this);
          broadcastMessage("SERVER: " + clientUsername + " has left the chat!");
      }

      public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)
      {
          removeClient();   //incase client runs into errors, we shall 1st remove them from chat

          try 
          {
              if (bufferedReader != null) 
              {
                  bufferedReader.close();
              }

              if (bufferedWriter != null) 
              {
                  bufferedWriter.close();
              }

              if (socket != null) 
              {
                  socket.close();
              }

          } catch (Exception e) 
          {
            e.printStackTrace();
          }
    } 

}

