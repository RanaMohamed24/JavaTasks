package ui;


import services.library.BookService;
import services.library.MagazineService;
import services.client.ClientService;
import services.borrow.BorrowService;
import java.util.Scanner;

public class MenuSystem {
    private BookService bookService;
    private MagazineService magazineService;
    private ClientService clientService;
    private BorrowService borrowService;
    private Scanner scanner;
    private BookMenuHandler bookMenuHandler;
    private MagazineMenuHandler magazineMenuHandler;
    private ClientMenuHandler clientMenuHandler;
    private BorrowMenuHandler borrowMenuHandler;

    public MenuSystem() {
        this.bookService = new BookService("Book Library");
        this.magazineService = new MagazineService("Magazine Library");
        this.clientService = new ClientService();
        this.borrowService = new BorrowService();
        this.scanner = new Scanner(System.in);
        
        this.bookMenuHandler = new BookMenuHandler(bookService, scanner);
        this.magazineMenuHandler = new MagazineMenuHandler(magazineService, scanner);
        this.clientMenuHandler = new ClientMenuHandler(clientService, scanner);
        this.borrowMenuHandler = new BorrowMenuHandler(bookService, magazineService, 
                                                        clientService, borrowService, scanner);
    }

    public void displayMainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║   Library Management System        ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║  1. Manage Books                   ║");
            System.out.println("║  2. Manage Magazines               ║");
            System.out.println("║  3. Manage Clients                 ║");
            System.out.println("║  4. Manage Borrowing               ║");
            System.out.println("║  5. Exit                           ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("Choose an option (enter the number and press Enter): ");
            
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    bookMenuHandler.manageBooks();
                    break;
                case 2:
                    magazineMenuHandler.manageMagazines();
                    break;
                case 3:
                    clientMenuHandler.manageClients();
                    break;
                case 4:
                    borrowMenuHandler.manageBorrowing();
                    break;
                case 5:
                    running = false;
                    System.out.println("\nThank you for using Library Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter one of the menu option numbers (e.g., 1-5).");
            }
        }
        scanner.close();
    }

    private int getIntInput() {
        try {
            int value = scanner.nextInt();
            return value;
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Invalid input: please enter a whole number and press Enter. Returning to previous menu.");
            return -1;
        }
    }
}