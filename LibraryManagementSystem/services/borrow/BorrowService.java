package services.borrow;

import models.client.Client;
import models.library.LibraryItem;
import models.borrow.BorrowRecord;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BorrowService {
    private List<BorrowRecord> borrowRecords;
    private int nextBorrowId;
    private static final int BORROW_PERIOD_DAYS = 14;

    public BorrowService() {
        this.borrowRecords = new ArrayList<>();
        this.nextBorrowId = 1;
    }

    public BorrowRecord borrowItem(Client client, LibraryItem item) throws Exception {
        if (client == null) {
            throw new Exception("Client cannot be null");
        }
        if (item == null) {
            throw new Exception("Item cannot be null");
        }
        
        if (client.hasActiveBorrow(item.getId())) {
            throw new Exception("Client already has an active borrow for this item!");
        }

        if (!item.isAvailable()) {
            throw new Exception("Item is not available! (" + item.getAvailableCopies() + "/" + item.getTotalCopies() + " copies available)");
        }

        if (!item.borrowCopy()) {
            throw new Exception("Failed to borrow item. No copies available!");
        }

        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(BORROW_PERIOD_DAYS);
        
        BorrowRecord record = new BorrowRecord(nextBorrowId++, client.getId(), 
                                             item.getId(), item.getTitle(), 
                                             borrowDate, dueDate);
        
        borrowRecords.add(record);
        client.addBorrowRecord(record);
        
        return record;
    }

    public BorrowRecord returnItem(Client client, int itemId, LibraryItem item) throws Exception {
        if (client == null) {
            throw new Exception("Client cannot be null");
        }

        BorrowRecord record = client.getActiveBorrowRecord(itemId);
        if (record == null) {
            throw new Exception("Client does not have an active borrow for this item!");
        }

        if (!item.returnCopy()) {
            throw new Exception("Failed to return item. Copy count mismatch!");
        }

        record.setReturnDate(LocalDate.now());
        record.setStatus("RETURNED");
        
        return record;
    }

    public boolean isItemAvailable(int itemId, LibraryItem item) {
        return item.isAvailable();
    }

    public List<BorrowRecord> getOverdueItems() {
        return borrowRecords.stream()
                .filter(BorrowRecord::isOverdue)
                .collect(Collectors.toList());
    }

    public void displayAllBorrowRecords() {
        if (borrowRecords.isEmpty()) {
            System.out.println("No borrow records in the system.");
            return;
        }
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    ALL BORROW RECORDS                             ║");
        System.out.println("║ Total: " + borrowRecords.size() + padRight("", 62 - String.valueOf(borrowRecords.size()).length()) + "║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Item Name                           | Status   | Due Date           ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        
        for (BorrowRecord record : borrowRecords) {
            String itemName = padRight(record.getItemTitle(), 35);
            String status = padRight(record.getStatus(), 8);
            String dueDate = padRight(record.getDueDate().toString(), 18);
            System.out.println("║ " + itemName + "| " + status + "| " + dueDate + "║");
            
            if (record.isOverdue()) {
                System.out.println("║" + padRight(" [OVERDUE - " + record.getDaysOverdue() + " days]", 69) + "║");
            }
            if (record.getReturnDate() != null) {
                System.out.println("║" + padRight(" [Returned: " + record.getReturnDate() + "]", 69) + "║");
            }
        }
        
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝\n");
    }

    public void displayOverdueItems() {
        List<BorrowRecord> overdueItems = getOverdueItems();
        if (overdueItems.isEmpty()) {
            System.out.println("No overdue items.");
            return;
        }
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    OVERDUE ITEMS                                  ║");
        System.out.println("║ Total Overdue: " + overdueItems.size() + padRight("", 54 - String.valueOf(overdueItems.size()).length()) + "║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Item Name                           | Days Overdue | Client ID      ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        
        for (BorrowRecord record : overdueItems) {
            String itemName = padRight(record.getItemTitle(), 35);
            String daysOverdue = padRight(String.valueOf(record.getDaysOverdue()), 12);
            String clientId = padRight(String.valueOf(record.getClientId()), 14);
            System.out.println("║ " + itemName + "| " + daysOverdue + "| " + clientId + "║");
        }
        
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝\n");
    }

    public int getTotalBorrowRecords() {
        return borrowRecords.size();
    }

    public int getTotalActiveBorrows() {
        return (int) borrowRecords.stream()
                .filter(record -> record.getStatus().equals("BORROWED"))
                .count();
    }

    public int getTotalReturnedBorrows() {
        return (int) borrowRecords.stream()
                .filter(record -> record.getStatus().equals("RETURNED"))
                .count();
    }

    public int getTotalOverdueItems() {
        return getOverdueItems().size();
    }

    public List<BorrowRecord> getAllBorrowRecords() {
        return new ArrayList<>(borrowRecords);
    }

    public List<BorrowRecord> getBorrowRecordsByClientId(int clientId) {
        return borrowRecords.stream()
                .filter(record -> record.getClientId() == clientId)
                .collect(Collectors.toList());
    }

    public void displayBorrowStatistics() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    BORROWING SYSTEM STATISTICS         ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Total Borrow Records: " + padRight(String.valueOf(getTotalBorrowRecords()), 17) + "║");
        System.out.println("║ Active Borrows: " + padRight(String.valueOf(getTotalActiveBorrows()), 23) + "║");
        System.out.println("║ Returned Items: " + padRight(String.valueOf(getTotalReturnedBorrows()), 23) + "║");
        System.out.println("║ Overdue Items: " + padRight(String.valueOf(getTotalOverdueItems()), 24) + "║");
        System.out.println("║ Borrow Period: " + padRight(BORROW_PERIOD_DAYS + " days", 24) + "║");
        System.out.println("╚════════════════════════════════════════╝\n");
    }

    public static int getBorrowPeriodDays() {
        return BORROW_PERIOD_DAYS;
    }

    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}

