import java.util.Scanner;
import java.util.StringTokenizer;

public class Tokenizer {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter a sentence: ");
        String sentence = input.nextLine();
        
        System.out.print("Enter the word : ");
        String word = input.next();
        
        StringTokenizer st = new StringTokenizer(sentence, word);
        
        System.out.println("\nResult:");
        while (st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }
        
        input.close();
    }
}
