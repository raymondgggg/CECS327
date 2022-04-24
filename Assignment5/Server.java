import java.net.*;
import java.util.*;
import java.io.*;

public class Server{
    private static Scanner scanner = new Scanner(System.in);
    
    /** 
     * @param args command line input for port 
     * @throws Exception if the port is invalid 
     */
    public static void main(String[] args) throws Exception {
        String port = "hello";
        System.out.println("----------   Server  ----------");

        // check command line if no input get port input at runtime
        if (args.length == 1){
            port = args[0];
            boolean checkInput = validatePort(port);

            if (!checkInput){
                throw new Exception("Bad input. Please run command again.");
            } 
        } else {
            port = getPort();
        }

        // network communcation
        int aPort = Integer.parseInt(port);
        DatagramSocket aSocket = new DatagramSocket(aPort);

        try{
            while (true) {
                byte [] buffer = new byte[1000];

                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                System.out.println("Client message: " + new String(request.getData()));

                String reply = new String(request.getData(), "UTF-8");
                reply = reply.toUpperCase();
                byte [] m = reply.getBytes();

                DatagramPacket message = new DatagramPacket(m, m.length, request.getAddress(), request.getPort());
                aSocket.send(message);
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.print("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) {
                aSocket.close();
            }
        }
    }

    /**
     * Method to prompt the user to enter the port number, uses helper method
     * validatePort()
     * to validate the input.
     * 
     * @return String port
     */
    public static String getPort() {
        boolean validPort = false;
        String port;

        do {
            System.out.print("Please enter the port number: ");
            port = scanner.nextLine();
            boolean checkPort = validatePort(port);
            validPort = checkPort;
        } while (!validPort);

        return port;
    }

    /**
     * Helper method to validate the port entered by the user
     * 
     * @param port number as a String
     * @return boolean value regarding whether or not the port is valid.
     */
    public static boolean validatePort(String port) {
        try {
            boolean isValid = Integer.parseInt(port) >= 0 && Integer.parseInt(port) <= 65_535;
            return isValid;
        } catch (Exception e) {
            return false;
        }
    }
}