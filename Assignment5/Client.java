import java.util.*;
import java.io.*;



public class Client {
    public static void main(String[] args) {
        // validateIP("rjkdf.jfdlks.dksflj.lkdjf");
        String test = "hello.my.name.is.raymond";
        String[] testArr = test.split("\\.");
        for (String element : testArr){
            System.out.println(element);
        }
    }



    // public static String getIP(){
    //     Scanner scanner = new Scanner(System.in);
    //     System.out.print("Please enter the desired IP address: ");
    // }


    public static void validateIP(String ip){
        String[] octets = ip.split("\\.");

        for (String octet : octets){
            System.out.println(octet);
        }

        



    }




}
