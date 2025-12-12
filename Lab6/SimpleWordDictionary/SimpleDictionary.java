import java.util.*;

public class SimpleDictionary {
    private Map<Character, TreeSet<String>> dictionary;
    private Scanner scanner;
    
    public SimpleDictionary() {
        dictionary = new TreeMap<>();
        scanner = new Scanner(System.in);
        initializeDictionary();
    }
    
    private void initializeDictionary() {
        String[] words = DictionaryData.getWords();
        for (String word : words) {
            addWord(word);
        }
    }
    
    public void addWord(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
        
        word = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        char firstChar = word.charAt(0);
        
        if (!dictionary.containsKey(firstChar)) {
            dictionary.put(firstChar, new TreeSet<>());
        }
        
        dictionary.get(firstChar).add(word);
    }
    
    public boolean isWordExists(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        
        word = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        char firstChar = word.charAt(0);
        
        if (!dictionary.containsKey(firstChar)) {
            return false;
        }
        
        return dictionary.get(firstChar).contains(word);
    }
    
    public void printAll() {
        System.out.println("\n========== COMPLETE DICTIONARY ==========");
        for (char c = 'A'; c <= 'Z'; c++) {
            if (dictionary.containsKey(c)) {
                System.out.print(c + ": ");
                System.out.println(dictionary.get(c));
            }
        }
        System.out.println("=========================================\n");
    }
    
    public void printWordsOfLetter(char letter) {
        char upperLetter = Character.toUpperCase(letter);
        
        System.out.println("\n--- Words starting with '" + upperLetter + "' ---");
        
        if (dictionary.containsKey(upperLetter)) {
            int count = 1;
            for (String word : dictionary.get(upperLetter)) {
                System.out.println(count + ". " + word);
                count++;
            }
        } else {
            System.out.println("No words found for letter: " + upperLetter);
        }
        System.out.println();
    }
    
    public boolean isValidWord(String word) {
        if (word.isEmpty()) {
            System.out.println("Word cannot be empty!");
            return false;
        }
        
        for (char c : word.toCharArray()) {
            if (!Character.isLetter(c)) {
                System.out.println("Invalid! Word must contain only letters. No numbers or special characters allowed!");
                return false;
            }
        }
        return true;
    }
    
    public void addNewWord() {
        System.out.print("\nEnter the word to add: ");
        String word = scanner.nextLine().trim();
        
        if (isValidWord(word)) {
            if (isWordExists(word)) {
                System.out.println("This word already exists!\n");
            } else {
                addWord(word);
                System.out.println("Word '" + word + "' added successfully!\n");
            }
        }
    }
    
    public void displayMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   SIMPLE WORD DICTIONARY MENU          ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. Display All Words                   ║");
        System.out.println("║ 2. Display Words of Specific Letter    ║");
        System.out.println("║ 3. Add New Word                        ║");
        System.out.println("║ 4. Exit                                ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("\nEnter your choice (1-4): ");
    }
    
    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                printAll();
                break;
            case 2:
                System.out.print("Enter a letter (A-Z): ");
                String letterInput = scanner.nextLine().trim();
                if (letterInput.length() == 1 && Character.isLetter(letterInput.charAt(0))) {
                    printWordsOfLetter(letterInput.charAt(0));
                } else {
                    System.out.println("Invalid input! Please enter a single letter.");
                }
                break;
            case 3:
                addNewWord();
                break;
            case 4:
                System.out.println("\nThank you for using Simple Dictionary! Goodbye!\n");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice! Please enter 1-4.");
        }
    }
    
    public void start() {
        boolean running = true;
        
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  WELCOME TO SIMPLE WORD DICTIONARY     ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        while (running) {
            displayMenu();
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                handleChoice(choice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number (1-4).");
            }
        }
    }
}