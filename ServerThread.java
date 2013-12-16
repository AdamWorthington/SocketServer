
import java.net.*;
import java.io.*;
 
public class ServerThread {
    public static void main(String[] args) throws IOException {
 
    if (args.length != 1) {
        System.err.println("Usage: java KKMultiServer <port number>");
        System.exit(1);
    }
 
        int portNumber = Integer.parseInt(args[0]);

         
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            while (true) {
                new Server(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}