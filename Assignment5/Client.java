import java.util.*;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        String ip;
        String port;
        String message;

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

        while (true){
            DatagramSocket aSocket = new DatagramSocket();
            message = getMessage();
            byte[] m = message.getBytes();
            InetAddress aHost = InetAddress.getByName(ip);
            int aPort = Integer.parseInt(port);

            DatagramPacket request = new DatagramPacket(m, m.length, aHost, aPort);

            aSocket.send(request);

            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);
            System.out.println("Message received from server: " + new String(reply.getData()));                                                                   );


        }   
        
        

    }


    public static String getMessage(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the message: ");
        return scanner.nextLine();
    }


    public static String getPort(){
        Scanner scanner = new Scanner(System.in);
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

    public static boolean validatePort(String port){
        try {
            boolean isValid = Integer.parseInt(port) >= 0 && Integer.parseInt(port) <= 65_535;
            return isValid;
        } catch (Exception e){
            return false;
        }
    }

    public static String getIP(){
        Scanner scanner = new Scanner(System.in);
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


    public static boolean validateOctetRange(int ip){
        return  ip >= 0 && ip <= 255;
    }

}
