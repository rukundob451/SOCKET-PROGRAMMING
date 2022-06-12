//CLIENT
package JAVA_GROUP_CHAT;
import java.net.*;
import java.io.* ;
import java.net.Socket;
import java.util.Scanner;

public class Client 
{
    public static void main(String[] args) throws IOException
    {
        try (Scanner s = new Scanner(System.in)) {
            System.out.print("Enter your username to be used in the group chat  :   ");
            String userName =  s.nextLine();
            Socket socket = new Socket("localhost", 1234); 
            Client client = new Client(socket, userName);
            client.listenForMessage();
            client.sendMessage();
        }
    }

            //DATAFIELDS

        private Socket socket ;
        private BufferedReader bufferedReader;
        private BufferedWriter bufferedWriter;
        private String userName;

            //METHODS

        //CONSTRUCTORS

        public Client(Socket socket, String userName) 
        {
            try 
            {
                this.socket = socket;
                this.userName = userName;
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            } catch (Exception e) 
            {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
        //@end_constructors


        //SEND MESSAGE
        /*This method will send messages to the client handler */
        public void sendMessage()
        {
            Scanner s = new Scanner(System.in);
            try 
            {
                bufferedWriter.write(userName);
                bufferedWriter.newLine();
                bufferedWriter.newLine();
                bufferedWriter.flush();

                

                while(socket.isConnected())
                {
                     String messageToSend = s.nextLine();
                     bufferedWriter.write(userName + "" + messageToSend);
                     bufferedWriter.newLine();
                     bufferedWriter.newLine();
                     bufferedWriter.flush();
                }
                
            } catch (Exception e) 
            {  
                closeEverything(socket, bufferedReader, bufferedWriter);
            }

            s.close();
            
        }


        //LISTEN FOR MESSAGES
        /*This method will listen for messages from the server as long client is still connected */

        public void listenForMessage()
        {
            new Thread
            (
                new Runnable() 
                {
                    @Override
                    public void run()
                    {
                        String messageFromGroupChat;

                        while(socket.isConnected())
                        {
                            try 
                            {
                                messageFromGroupChat = bufferedReader.readLine();
                                System.out.println(messageFromGroupChat);
                            } catch (Exception e) {
                                closeEverything(socket, bufferedReader, bufferedWriter);
                            }
                        }
                    }
                }
            ).start();
        }




        //CLOSE EVERYTHING
        /*This method will close everything once the client runs into any input output errors */
        public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)
      {

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
