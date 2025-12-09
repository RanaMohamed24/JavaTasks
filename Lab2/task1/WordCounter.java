import java.util.Scanner;

public class WordCounter {

    public static int Split(String sentence, String word) {
        String[] words = sentence.split("\\s+");
        int count = 0;
        
         for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word)) {
                count++;
            }
        }
        return count;
    }

    public static int Index(String sentence, String word) {
        int count = 0;
        int index = 0;

        while ((index = sentence.indexOf(word, index)) != -1) {
            count++;
            index += word.length();
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a sentence: ");
        String sentence = input.nextLine();

        System.out.print("Enter the word to search: ");
        String word = input.next();

        int result1 = Split(sentence, word);
        int result2 = Index(sentence, word);

        System.out.println("Count using split(): " + result1);
        System.out.println("Count using indexOf(): " + result2);

        input.close();
    }
}

