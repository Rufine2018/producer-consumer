import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Manager {
    Tampon tampon;
    ServerSocket serverSocket;
    int portNumber;
    public static final String SYN = "SYN";
    public static final String ACK = "ACK";
    public static final String KO = "KO";
    public static final String FIN = "FIN";

    public void manage () {
        String message = "";
        Socket connectedSocket = null;
        BufferedReader socketBufferedReader = null;
        PrintWriter socketPrintWriter = null;
        try {
            serverSocket = new ServerSocket(portNumber);
            String managerName = getClass().getCanonicalName();
            String clientName = "";
            System.out.println(managerName +" is ready");
            while (true) {
                connectedSocket = serverSocket.accept();

                socketBufferedReader = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));

                message = socketBufferedReader.readLine();
                clientName = connectedSocket.getInetAddress().getCanonicalHostName()+ ":" + connectedSocket.getPort();
                System.out.println(managerName +": "+ clientName + " said: "+message);
                if (message.equalsIgnoreCase(SYN)) {
                    socketPrintWriter = new PrintWriter(connectedSocket.getOutputStream(), true);
                    if (canDoProcess()) {
                        socketPrintWriter.println(ACK);
                        System.out.println(managerName +" sent " + ACK + " to "+ clientName);

                        message = socketBufferedReader.readLine();
                        System.out.println(managerName +": "+ clientName + " said: "+message);
                        if (canDoProcess()) {
                            try {
                                message = process(message, socketPrintWriter);
                                System.out.println(managerName +" sent " + message + " to "+ clientName);
          /*                      if (STOP.equalsIgnoreCase(message)) {// to stop the server kindly. use a producer with stop message
                                    notify();
                                }*/
                            } catch (InterruptedException e) {
                                System.out.println(this.getClass().getCanonicalName() +"- Server manager failed: "+ e.getMessage());
                            }
                        }
                    } else {
                        socketPrintWriter.println(KO);
                        System.out.println(managerName +" sent " + KO + " to "+ clientName);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Producer manager exception "+ e.getMessage());
        } finally {
            try {
                connectedSocket.close();
                socketBufferedReader.close();
                socketPrintWriter.close();
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("Can't close the socket "+e.getMessage());
            }
        }
    }

    abstract boolean canDoProcess();

    abstract String process(String message, PrintWriter socketBufferedReader) throws InterruptedException;

}
