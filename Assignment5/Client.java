import java.util.*;
import java.io.*;
import java.net.*;

public class Client {

    private static Scanner scanner = new Scanner(System.in);
    
    /** 
     * Main method responsible for combining all the functionality created in various other methods:
     * gets user input, validates, sends message to the server.
     * @param args param order ip, port, message
     * @throws Exception if the user provides invalid command line parameters to redo command 
     */
    public static void main(String[] args) throws Exception {
        String ip;
        String port;
        String message;

        System.out.println("\n----------   Client   ----------");

        // check if command line input entered, if not get input at runtime.
        if (args.length == 3){
            ip = args[0];
            port = args[1];
            message = args[2];

            boolean checkInputs = validateIP(ip) && validatePort(port);
            if (!checkInputs){
                throw new Exception("Bad input. Please run command again.");
            }

        } else {
            ip = getIP();
            port = getPort();
        }

        // network communication
        DatagramSocket aSocket = new DatagramSocket();

        try {
            while (true){
                message = getMessage();
                byte[] m = message.getBytes();
                InetAddress aHost = InetAddress.getByName(ip);
                int aPort = Integer.parseInt(port);

                DatagramPacket request = new DatagramPacket(m, m.length, aHost, aPort);
                aSocket.send(request);

                byte[] buffer = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(reply);
                System.out.println("Server message:  " + new String(reply.getData()));
            } 
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null)
                aSocket.close();
        }
    }
    
    /** 
     * Method to prompt the user to enter a message
     * @return String message
     */
    public static String getMessage(){
        System.out.print("Please enter the message: ");
        return scanner.nextLine();
    }

    
    /** 
     * Method to prompt the user to enter the port number, uses helper method validatePort() 
     * to validate the input.
     * @return String port
     */
    public static String getPort(){
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
     * @param port number as a String 
     * @return boolean value regarding whether or not the port is valid.
     */
    public static boolean validatePort(String port){
        try {
            boolean isValid = Integer.parseInt(port) >= 0 && Integer.parseInt(port) <= 65_535;
            return isValid;
        } catch (Exception e){
            return false;
        }
    }

    /** 
     * Method to take in ip address entered by the user at runtime. Uses helper method to validate the 
     * ip address format.
     * @return String ipv4 address 
     */
    public static String getIP(){
        boolean validIP = false;
        String ip;

        do {
            System.out.print("Please enter the IP Address: ");
            ip = scanner.nextLine();
            boolean checkIP = validateIP(ip);
            validIP = checkIP;
        } while (!validIP);

        return ip;
    }

    /** 
     * Helper method used to determine if the ip address entered is valid
     * @param ip as a String
     * @return boolean value pertaining to validity of ip address.
     */
    public static boolean validateIP(String ip){
        String[] octets = ip.split("\\.");

        if (octets.length != 4)
            return false;
        
        for (String octet : octets){
            int octetNum;
            try{
                octetNum = Integer.parseInt(octet);
            } catch (Exception e){
                return false;
            }
            if (!validateOctetRange(octetNum))
                return false;
        }

        return true;
    }

    /** 
     * Helper method used to determine if the octect in an ipv4 address is within
     * a valid range.
     * @param ip octet as int
     * @return boolean value pertaining to validity of octet range.
     */
    public static boolean validateOctetRange(int ip){
        return  ip >= 0 && ip <= 255;
    }
}