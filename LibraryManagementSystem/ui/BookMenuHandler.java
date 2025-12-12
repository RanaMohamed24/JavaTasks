package ui;

import models.library.Book;
import services.library.BookService;
import validation.Validator;
import exceptions.ItemNotFoundException;
import java.util.Scanner;

public class BookMenuHandler {
    private BookService bookService;
    private Scanner scanner;

    public BookMenuHandler(BookService bookService, Scanner scanner) {
        this.bookService = bookService;
        this.scanner = scanner;
    }

    public void manageBooks() {
        boolean managing = true;
        while (managing) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║       BOOK MANAGEMENT                  ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  1. Add Book                           ║");
            System.out.println("║  2. View All Books                     ║");
            System.out.println("║  3. Search Book by ID                  ║");
            System.out.println("║  4. Update Book                        ║");
            System.out.println("║  5. Delete Book                        ║");
            System.out.println("║  6. View Book Stock                    ║");
            System.out.println("║  7. Add Stock Copies                   ║");
            System.out.println("║  8. Back                               ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Choose: ");
            System.out.println("(Enter the option number and press Enter. Invalid inputs return to the previous menu.)");
            
            int choice = getIntInput();
            try {
                switch (choice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        bookService.displayAllItems();
                        break;
                    case 3:
                        searchBook();
                        break;
                    case 4:
                        updateBook();
                        break;
                    case 5:
                        deleteBook();
                        break;
                    case 6:
                        viewBookStock();
                        break;
                    case 7:
                        addStockCopies();
                        break;
                    case 8:
                        managing = false;
                        break;
                    default:
                            System.out.println("Invalid choice. Please select one of the displayed menu numbers.");
                }
            } catch (ItemNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addBook() throws ItemNotFoundException {
        System.out.print("Enter Book ID: ");
        int id = getIntInput();
        
        if (id == -1) return;
        
        String idError = Validator.getIdError(id);
        if (idError != null) {
            System.out.println("Error: " + idError);
            return;
        }
        
        if (!Validator.isIdUniqueInItems(id, bookService.getAllItems())) {
            System.out.println("Error: " + Validator.getUniqueIdItemError(id));
            return;
        }
        
        scanner.nextLine();
        
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        String titleError = Validator.getNameError(title);
        if (titleError != null) {
            System.out.println("Error: " + titleError);
            return;
        }
        
        System.out.print("Enter Author Name: ");
        String author = scanner.nextLine();
        String authorError = Validator.getAuthorError(author);
        if (authorError != null) {
            System.out.println("Error: " + authorError);
            return;
        }
        
        System.out.print("Enter Number of Pages: ");
        int pages = getIntInput();
        
        if (pages == -1) return;
        
        String pagesError = Validator.getPagesError(pages);
        if (pagesError != null) {
            System.out.println("Error: " + pagesError);
            return;
        }
        
        
        
        System.out.print("Enter Total Number of Copies: ");
        int totalCopies = getIntInput();
        
        if (totalCopies == -1) return;
        
        if (totalCopies <= 0) {
            System.out.println("Error: Total copies must be greater than 0");
            return;
        }
        
        Book book = new Book(id, title, author, pages, totalCopies);
        bookService.addItem(book);
        System.out.println("\nBook added successfully!");
        System.out.println("Title: " + title);
        System.out.println("Total Copies: " + totalCopies);
    }

    private void searchBook() throws ItemNotFoundException {
        System.out.print("Enter Book ID to search: ");
        int id = getIntInput();
        
        if (id == -1) return;
        
        Book book = bookService.getItemById(id);
        System.out.println("\n" + book.getItemDetails());
    }

    private void updateBook() throws ItemNotFoundException {
        System.out.print("Enter Book ID to update: ");
        int id = getIntInput();
        
        if (id == -1) return;
        
        try {
            bookService.getItemById(id);
        } catch (ItemNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        
        scanner.nextLine();
        
        System.out.print("Enter new Book Title: ");
        String title = scanner.nextLine();
        String titleError = Validator.getNameError(title);
        if (titleError != null) {
            System.out.println("Error: " + titleError);
            return;
        }
        
        System.out.print("Enter new Author Name: ");
        String author = scanner.nextLine();
        String authorError = Validator.getAuthorError(author);
        if (authorError != null) {
            System.out.println("Error: " + authorError);
            return;
        }
        
        System.out.print("Enter new Number of Pages: ");
        int pages = getIntInput();
        
        if (pages == -1) return;
        
        String pagesError = Validator.getPagesError(pages);
        if (pagesError != null) {
            System.out.println("Error: " + pagesError);
            return;
        }
        
        
        
    Book oldBook = bookService.getItemById(id);
    Book updatedBook = new Book(id, title, author, pages, oldBook.getTotalCopies());
  
    updatedBook.setAvailableCopies(oldBook.getAvailableCopies());
        
        bookService.updateItem(id, updatedBook);
        System.out.println("Book updated successfully!");
    }

    private void deleteBook() throws ItemNotFoundException {
        System.out.print("Enter Book ID to delete: ");
        int id = getIntInput();
        
        if (id == -1) return;
        
        try {
            bookService.deleteItem(id);
            System.out.println("Book deleted successfully!");
        } catch (ItemNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewBookStock() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    BOOK STOCK STATUS                              ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.println("║ ID | Title                               | Total | Available | Status   ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");

        for (Book book : bookService.getAllItems()) {
            String id = String.format("%2d", book.getId());
            String title = String.format("%-35s", book.getTitle());
            String total = String.format("%5d", book.getTotalCopies());
            String available = String.format("%9d", book.getAvailableCopies());
            String status = String.format("%-8s", book.getStatus().getDisplayName());
            System.out.println("║ " + id + " | " + title + " | " + total + " | " + available + " | " + status + " ║");
        }

        System.out.println("╚═══════════════════════════════════════════════════════════════════╝\n");
    }

    private void addStockCopies() throws ItemNotFoundException {
        System.out.print("Enter Book ID: ");
        int id = getIntInput();
        
        if (id == -1) return;
        
        try {
            Book book = bookService.getItemById(id);
            
            System.out.print("Enter number of copies to add: ");
            int copiesToAdd = getIntInput();
            
            if (copiesToAdd == -1) return;
            
            if (copiesToAdd <= 0) {
                System.out.println("Error: Must add at least 1 copy");
                return;
            }
            
            book.addCopies(copiesToAdd);
            System.out.println("\nCopies added successfully!");
            System.out.println("Book: " + book.getTitle());
            System.out.println("Total Copies Now: " + book.getTotalCopies());
            System.out.println("Available Copies: " + book.getAvailableCopies());
            
        } catch (ItemNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
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
