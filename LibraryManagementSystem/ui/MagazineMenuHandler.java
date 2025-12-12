package ui;

import models.library.Magazine;
import services.library.MagazineService;
import validation.Validator;
import exceptions.ItemNotFoundException;
import java.util.Scanner;

public class MagazineMenuHandler {
    private MagazineService magazineService;
    private Scanner scanner;

    public MagazineMenuHandler(MagazineService magazineService, Scanner scanner) {
        this.magazineService = magazineService;
        this.scanner = scanner;
    }

    public void manageMagazines() {
        boolean managing = true;
        while (managing) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║    MAGAZINE MANAGEMENT                 ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  1. Add Magazine                       ║");
            System.out.println("║  2. View All Magazines                 ║");
            System.out.println("║  3. Search Magazine by ID              ║");
            System.out.println("║  4. Update Magazine                    ║");
            System.out.println("║  5. Delete Magazine                    ║");
            System.out.println("║  6. View Magazine Stock                ║");
            System.out.println("║  7. Add Stock Copies                   ║");
            System.out.println("║  8. Back                               ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Choose: ");
            System.out.println("(Enter the option number and press Enter. Invalid inputs return to the previous menu.)");
            
            int choice = getIntInput();
            try {
                switch (choice) {
                    case 1:
                        addMagazine();
                        break;
                    case 2:
                        magazineService.displayAllItems();
                        break;
                    case 3:
                        searchMagazine();
                        break;
                    case 4:
                        updateMagazine();
                        break;
                    case 5:
                        deleteMagazine();
                        break;
                    case 6:
                        viewMagazineStock();
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

    private void addMagazine() throws ItemNotFoundException {
        System.out.print("Enter Magazine ID: ");
        int id = getIntInput();
        
        if (id == -1) return;
        
        String idError = Validator.getIdError(id);
        if (idError != null) {
            System.out.println("Error: " + idError);
            return;
        }
        
        if (!Validator.isIdUniqueInItems(id, magazineService.getAllItems())) {
            System.out.println("Error: " + Validator.getUniqueIdItemError(id));
            return;
        }
        
        scanner.nextLine();
        
        System.out.print("Enter Magazine Title: ");
        String title = scanner.nextLine();
        String titleError = Validator.getNameError(title);
        if (titleError != null) {
            System.out.println("Error: " + titleError);
            return;
        }
        
        System.out.print("Enter Publisher Name: ");
        String publisher = scanner.nextLine();
        String publisherError = Validator.getPublisherError(publisher);
        if (publisherError != null) {
            System.out.println("Error: " + publisherError);
            return;
        }
        
        System.out.print("Enter Issue Number: ");
        int issueNumber = getIntInput();
        
        if (issueNumber == -1) return;
        
        String issueError = Validator.getIssueNumberError(issueNumber);
        if (issueError != null) {
            System.out.println("Error: " + issueError);
            return;
        }
        
        scanner.nextLine();
        
        System.out.print("Enter Release Date (YYYY-MM-DD): ");
        String releaseDate = scanner.nextLine();
        String dateError = Validator.getDateError(releaseDate);
        if (dateError != null) {
            System.out.println("Error: " + dateError);
            return;
        }
        
        System.out.print("Enter Total Number of Copies: ");
        int totalCopies = getIntInput();
        
        if (totalCopies == -1) return;
        
        if (totalCopies <= 0) {
            System.out.println("Error: Total copies must be greater than 0");
            return;
        }
        
        Magazine magazine = new Magazine(id, title, publisher, issueNumber, releaseDate, totalCopies);
        magazineService.addItem(magazine);
        System.out.println("\nMagazine added successfully!");
        System.out.println("Title: " + title);
        System.out.println("Total Copies: " + totalCopies);
    }

    private void searchMagazine() throws ItemNotFoundException {
        System.out.print("Enter Magazine ID to search: ");
        int id = getIntInput();
        
        if (id == -1) return;
        
        Magazine magazine = magazineService.getItemById(id);
        System.out.println("\n" + magazine.getItemDetails());
    }

    private void updateMagazine() throws ItemNotFoundException {
        System.out.print("Enter Magazine ID to update: ");
        int id = getIntInput();
        
        if (id == -1) return;
        
        try {
            magazineService.getItemById(id);
        } catch (ItemNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        
        scanner.nextLine();
        
        System.out.print("Enter new Magazine Title: ");
        String title = scanner.nextLine();
        String titleError = Validator.getNameError(title);
        if (titleError != null) {
            System.out.println("Error: " + titleError);
            return;
        }
        
        System.out.print("Enter new Publisher Name: ");
        String publisher = scanner.nextLine();
        String publisherError = Validator.getPublisherError(publisher);
        if (publisherError != null) {
            System.out.println("Error: " + publisherError);
            return;
        }
        
        System.out.print("Enter new Issue Number: ");
        int issueNumber = getIntInput();
        
        if (issueNumber == -1) return;
        
        String issueError = Validator.getIssueNumberError(issueNumber);
        if (issueError != null) {
            System.out.println("Error: " + issueError);
            return;
        }
        
        scanner.nextLine();
        
        System.out.print("Enter new Release Date (YYYY-MM-DD): ");
        String releaseDate = scanner.nextLine();
        String dateError = Validator.getDateError(releaseDate);
        if (dateError != null) {
            System.out.println("Error: " + dateError);
            return;
        }
        
    Magazine oldMagazine = magazineService.getItemById(id);
    Magazine updatedMagazine = new Magazine(id, title, publisher, issueNumber, releaseDate, oldMagazine.getTotalCopies());
    // preserve available copies using setter (availableCopies is protected in LibraryItem)
    updatedMagazine.setAvailableCopies(oldMagazine.getAvailableCopies());
        
        magazineService.updateItem(id, updatedMagazine);
        System.out.println("Magazine updated successfully!");
    }

    private void deleteMagazine() throws ItemNotFoundException {
        System.out.print("Enter Magazine ID to delete: ");
        int id = getIntInput();
        
        if (id == -1) return;
        
        try {
            magazineService.deleteItem(id);
            System.out.println("Magazine deleted successfully!");
        } catch (ItemNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewMagazineStock() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                  MAGAZINE STOCK STATUS                            ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.println("║ ID | Title                               | Total | Available | Status   ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");

        for (Magazine magazine : magazineService.getAllItems()) {
            String id = String.format("%2d", magazine.getId());
            String title = String.format("%-35s", magazine.getTitle());
            String total = String.format("%5d", magazine.getTotalCopies());
            String available = String.format("%9d", magazine.getAvailableCopies());
            String status = String.format("%-8s", magazine.getStatus().getDisplayName());
            System.out.println("║ " + id + " | " + title + " | " + total + " | " + available + " | " + status + " ║");
        }

        System.out.println("╚═══════════════════════════════════════════════════════════════════╝\n");
    }

    private void addStockCopies() throws ItemNotFoundException {
        System.out.print("Enter Magazine ID: ");
        int id = getIntInput();
        
        if (id == -1) return;
        
        try {
            Magazine magazine = magazineService.getItemById(id);
            
            System.out.print("Enter number of copies to add: ");
            int copiesToAdd = getIntInput();
            
            if (copiesToAdd == -1) return;
            
            if (copiesToAdd <= 0) {
                System.out.println("Error: Must add at least 1 copy");
                return;
            }
            
            magazine.addCopies(copiesToAdd);
            System.out.println("\nCopies added successfully!");
            System.out.println("Magazine: " + magazine.getTitle());
            System.out.println("Total Copies Now: " + magazine.getTotalCopies());
            System.out.println("Available Copies: " + magazine.getAvailableCopies());
            
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