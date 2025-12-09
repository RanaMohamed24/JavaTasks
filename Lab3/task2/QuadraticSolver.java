import java.util.function.Function;
import java.util.Scanner;
import java.util.InputMismatchException;

public class QuadraticSolver {
    public static void main(String[] args) {
        
        Function<double[], double[]> solver = coef -> {
            double a = coef[0];
            double b = coef[1];
            double c = coef[2];
            
            double discriminant = b * b - 4 * a * c;
            double root1 = 0, root2 = 0;
            
            if (discriminant >= 0) {
                root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            }
            
            return new double[]{root1, root2, discriminant};
        };
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("Quadratic Equation Solver: ax² + bx + c = 0\n");
            System.out.print("Enter a: ");
            double a = scanner.nextDouble();
            System.out.print("Enter b: ");
            double b = scanner.nextDouble();
            System.out.print("Enter c: ");
            double c = scanner.nextDouble();
            
            double[] result = solver.apply(new double[]{a, b, c});
            double discriminant = result[2];
            
            System.out.println("\n--- Results ---");
            
            if (discriminant > 0) {
                System.out.println("Two roots: " + result[0] + " and " + result[1]);
            } else if (discriminant == 0) {
                System.out.println("One root: " + result[0]);
            } else {
                System.out.println("No real roots");
            }
            
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter valid numbers only!");
        } finally {
            scanner.close();
        }
    }
}
