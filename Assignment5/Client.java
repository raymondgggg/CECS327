import java.util.*;
import java.io.*;



public class Client {
    public static void main(String[] args) {
        // String ip = getIP();
        String port = getPort();

    }


    public static String getPort(){
        Scanner scanner = new Scanner(System.in);
        boolean validPort = false;
        String port;
        
        do {
            System.out.print("Please enter the port number: ");
            port = scanner.nextLine();

            List<Character> portChars = ArrayToListConversion(port.toCharArray());
            // boolean valid = portChars.

            boolean checkPort = validatePort(port);
            
            validPort = checkPort;
        } while (!validPort);

        scanner.close();
        return port;
    }

    public static boolean validatePort(String port){
        return Integer.parseInt(port) >= 0 && Integer.parseInt(port) <= 65_535;
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

        scanner.close();
        return ip;
    }


    public static boolean validateIP(String ip){
        String[] octets = ip.split("\\.");

        if (octets.length != 4)
            return false;
        
        for (String octet : octets){
            int octetNum = Integer.parseInt(octet);
            if (!validateOctetRange(octetNum))
                return false;
        }

        return true;
    }


    public static boolean validateOctetRange(int ip){
        return  ip >= 0 && ip <= 255;
    }

}
