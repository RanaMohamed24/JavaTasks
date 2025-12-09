import java.util.regex.*;
import java.util.Scanner;
public class IPAddress {
    public static boolean isValidIP(String ip) {
        String numbers = 
            "([0-9]{1,2}|(0|1)[0-9]{2}|2[0-4][0-9]|25[0-5])";
        String regex = "^" + numbers + "\\." + numbers + "\\." 
                           + numbers+ "\\." + numbers + "$";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an IP address: ");
        String ip = scanner.nextLine();

        if (isValidIP(ip)) {
            System.out.println(ip + " is valid");
            
         
            String[] parts = ip.split("\\.");
            for (int i = 0; i < parts.length; i++) {
                System.out.println(parts[i]);
            }
        } else {
            System.out.println(ip + " is invalid");
        }
    }
}

