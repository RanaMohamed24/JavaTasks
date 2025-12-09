import java.util.function.Function;
import java.util.Scanner;
import java.util.InputMismatchException;

public class TempConverter {
    public static void main(String[] args) {
        
        Function<Double, Double> CelToFahr = new Function<Double, Double>() {
            public Double apply(Double celsius) {
                return (celsius * 9/5) + 32;
            }
        };
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("Enter temp in Celsius: ");
            double celsius = scanner.nextDouble();
            
            double fahrenheit = CelToFahr.apply(celsius);
            
            System.out.println(celsius + "°C = " + fahrenheit + "°F");
            
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid number!");
        } finally {
            scanner.close();
        }
    }
}
