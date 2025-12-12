package ui;

import models.library.Book;
import models.library.Magazine;
import models.library.LibraryItem;
import models.client.Client;
import services.library.BookService;
import services.library.MagazineService;
import services.client.ClientService;
import services.borrow.BorrowService;
import models.borrow.BorrowRecord;

import java.util.Scanner;
import java.util.List;

public class BorrowMenuHandler {
    private BookService bookService;
    private MagazineService magazineService;
    private ClientService clientService;
    private BorrowService borrowService;
    private Scanner scanner;

    public BorrowMenuHandler(BookService bookService, MagazineService magazineService,
                            ClientService clientService, BorrowService borrowService, Scanner scanner) {
        this.bookService = bookService;
        this.magazineService = magazineService;
        this.clientService = clientService;
        this.borrowService = borrowService;
        this.scanner = scanner;
    }

    public void manageBorrowing() {
        boolean managing = true;
        while (managing) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║   BORROWING MANAGEMENT                 ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  1. Borrow Book                        ║");
            System.out.println("║  2. Borrow Magazine                    ║");
            System.out.println("║  3. Return Item                        ║");
            System.out.println("║  4. View Client Borrow History         ║");
            System.out.println("║  5. View All Borrow Records            ║");
            System.out.println("║  6. View Overdue Items                 ║");
            System.out.println("║  7. View Item Stock Status             ║");
            System.out.println("║  8. View Statistics                    ║");
            System.out.println("║  9. Back                               ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Choose: ");
            System.out.println("(Enter the option number and press Enter. Invalid inputs return to the previous menu.)");
            
            int choice = getIntInput();
            try {
                switch (choice) {
                    case 1:
                        borrowBook();
                        break;
                    case 2:
                        borrowMagazine();
                        break;
                    case 3:
                        returnItem();
                        break;
                    case 4:
                        viewClientBorrowHistory();
                        break;
                    case 5:
                        displayAllBorrowRecords();
                        break;
                    case 6:
                        borrowService.displayOverdueItems();
                        break;
                    case 7:
                        viewItemStockStatus();
                        break;
                    case 8:
                        borrowService.displayBorrowStatistics();
                        break;
                    case 9:
                        managing = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please select one of the displayed menu numbers.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void borrowBook() throws Exception {
        System.out.print("Enter Client ID: ");
        int clientId = getIntInput();
        if (clientId == -1) return;

        Client client = clientService.getClientById(clientId);

        System.out.print("Enter Book ID: ");
        int bookId = getIntInput();
        if (bookId == -1) return;

        Book book = bookService.getItemById(bookId);

        if (!book.isAvailable()) {
            System.out.println("Error: Book not available! Available copies: " + book.getAvailableCopies() + "/" + book.getTotalCopies());
            return;
        }

        BorrowRecord record = borrowService.borrowItem(client, book);
        System.out.println("\nBook borrowed successfully!");
        System.out.println("Title: " + book.getTitle());
        System.out.println("Due date: " + record.getDueDate() + " (14 days)");
        System.out.println("Available copies remaining: " + book.getAvailableCopies() + "/" + book.getTotalCopies());
    }

    private void borrowMagazine() throws Exception {
        System.out.print("Enter Client ID: ");
        int clientId = getIntInput();
        if (clientId == -1) return;

        Client client = clientService.getClientById(clientId);

        System.out.print("Enter Magazine ID: ");
        int magazineId = getIntInput();
        if (magazineId == -1) return;

        Magazine magazine = magazineService.getItemById(magazineId);

        if (!magazine.isAvailable()) {
            System.out.println("Error: Magazine not available! Available copies: " + magazine.getAvailableCopies() + "/" + magazine.getTotalCopies());
            return;
        }

        BorrowRecord record = borrowService.borrowItem(client, magazine);
        System.out.println("\nMagazine borrowed successfully!");
        System.out.println("Title: " + magazine.getTitle());
        System.out.println("Due date: " + record.getDueDate() + " (14 days)");
        System.out.println("Available copies remaining: " + magazine.getAvailableCopies() + "/" + magazine.getTotalCopies());
    }

    private void returnItem() throws Exception {
        System.out.print("Enter Client ID: ");
        int clientId = getIntInput();
        if (clientId == -1) return;

        Client client = clientService.getClientById(clientId);

        System.out.print("Enter Item ID: ");
        int itemId = getIntInput();
        if (itemId == -1) return;

        LibraryItem item = null;
        try {
            item = bookService.getItemById(itemId);
        } catch (Exception e) {
            item = magazineService.getItemById(itemId);
        }

        BorrowRecord record = borrowService.returnItem(client, itemId, item);
        System.out.println("\nItem returned successfully!");
        System.out.println("Title: " + item.getTitle());
        System.out.println("Returned on: " + record.getReturnDate());
        System.out.println("Available copies now: " + item.getAvailableCopies() + "/" + item.getTotalCopies());
    }

    private void viewClientBorrowHistory() throws Exception {
        System.out.print("Enter Client ID: ");
        int clientId = getIntInput();
        if (clientId == -1) return;

        Client client = clientService.getClientById(clientId);
        client.displayBorrowHistory();
    }

    private void displayAllBorrowRecords() {
        borrowService.displayAllBorrowRecords();
    }

    private void viewItemStockStatus() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║              BOOK STOCK STATUS                                    ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Item Name                           | Total | Available | Borrowed ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");

        List<Book> books = bookService.getAllItems();
        for (Book book : books) {
            String itemName = String.format("%-35s", book.getTitle());
            String total = String.format("%5d", book.getTotalCopies());
            String available = String.format("%9d", book.getAvailableCopies());
            String borrowed = String.format("%8d", book.getBorrowedCopies());
            System.out.println("║ " + itemName + "| " + total + " | " + available + " | " + borrowed + " ║");
        }

        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.println("║              MAGAZINE STOCK STATUS                                ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Item Name                           | Total | Available | Borrowed ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");

        List<Magazine> magazines = magazineService.getAllItems();
        for (Magazine magazine : magazines) {
            String itemName = String.format("%-35s", magazine.getTitle());
            String total = String.format("%5d", magazine.getTotalCopies());
            String available = String.format("%9d", magazine.getAvailableCopies());
            String borrowed = String.format("%8d", magazine.getBorrowedCopies());
            System.out.println("║ " + itemName + "| " + total + " | " + available + " | " + borrowed + " ║");
        }

        System.out.println("╚═══════════════════════════════════════════════════════════════════╝\n");
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