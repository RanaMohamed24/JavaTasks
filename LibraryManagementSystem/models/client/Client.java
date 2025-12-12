package models.client;

import models.CrudEntity;
import models.borrow.BorrowRecord;
import java.util.ArrayList;
import java.util.List;

public class Client implements CrudEntity<Client> {
    private int id;
    private String name;
    private String email;
    private List<BorrowRecord> borrowHistory;

    public Client(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.borrowHistory = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<BorrowRecord> getBorrowHistory() {
        return new ArrayList<>(borrowHistory);
    }

    public void addBorrowRecord(BorrowRecord record) {
        borrowHistory.add(record);
    }

    public List<BorrowRecord> getActiveBorrows() {
        List<BorrowRecord> activeBorrows = new ArrayList<>();
        for (BorrowRecord record : borrowHistory) {
            if (record.getStatus().equals("BORROWED")) {
                activeBorrows.add(record);
            }
        }
        return activeBorrows;
    }

    public List<BorrowRecord> getReturnedBorrows() {
        List<BorrowRecord> returnedBorrows = new ArrayList<>();
        for (BorrowRecord record : borrowHistory) {
            if (record.getStatus().equals("RETURNED")) {
                returnedBorrows.add(record);
            }
        }
        return returnedBorrows;
    }

    public int getTotalBorrowedItems() {
        return borrowHistory.size();
    }

    public int getActiveBorrowCount() {
        return getActiveBorrows().size();
    }

    public boolean hasActiveBorrow(int itemId) {
        for (BorrowRecord record : borrowHistory) {
            if (record.getItemId() == itemId && record.getStatus().equals("BORROWED")) {
                return true;
            }
        }
        return false;
    }

    public BorrowRecord getActiveBorrowRecord(int itemId) {
        for (BorrowRecord record : borrowHistory) {
            if (record.getItemId() == itemId && record.getStatus().equals("BORROWED")) {
                return record;
            }
        }
        return null;
    }

    public String getClientDetails() {
        return "👤 CLIENT [ID: " + id + ", Name: " + name + ", Email: " + email + 
               ", Total Borrows: " + getTotalBorrowedItems() + 
               ", Active Borrows: " + getActiveBorrowCount() + "]";
    }

    // --- CrudEntity implementations ---
    @Override
    public Client create() {
        // In a full app this would persist; return this instance as a placeholder
        return this;
    }

    @Override
    public Client read() {
        return this;
    }

    @Override
    public void update(Client other) {
        if (other == null) return;
        this.name = other.name;
        this.email = other.email;
        // keep borrow history and id intact
    }

    @Override
    public void delete() {
        // Basic delete: clear identifying info and borrow history
        this.id = 0;
        this.name = "";
        this.email = "";
        this.borrowHistory.clear();
    }

    public void displayBorrowHistory() {
        if (borrowHistory.isEmpty()) {
            System.out.println("\nNo borrow history for client: " + name);
            return;
        }
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║ BORROW HISTORY - " + name + padRight("", 52 - name.length()) + "║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Total: " + borrowHistory.size() + " | Active: " + getActiveBorrowCount() + 
                          " | Returned: " + getReturnedBorrows().size() + padRight("", 40) + "║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Item Name                           | Status   | Due Date           ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        
        for (BorrowRecord record : borrowHistory) {
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

    public void displayActiveBorrows() {
        List<BorrowRecord> activeBorrows = getActiveBorrows();
        if (activeBorrows.isEmpty()) {
            System.out.println("\nNo active borrows for client: " + name);
            return;
        }
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║ ACTIVE BORROWS - " + name + padRight("", 48 - name.length()) + "    ║");
        System.out.println("╠═════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Item Name                           | Status   | Due Date           ║");
        System.out.println("╠═════════════════════════════════════════════════════════════════════╣");
        
        for (BorrowRecord record : activeBorrows) {
            String itemName = padRight(record.getItemTitle(), 32);
            String status = padRight(record.getStatus(), 5);
            String dueDate = padRight(record.getDueDate().toString(), 15);
            System.out.println("║ " + itemName + "| " + status + "| " + dueDate + "║");
            
            if (record.isOverdue()) {
                System.out.println("║" + padRight(" [OVERDUE - " + record.getDaysOverdue() + " days]", 66) + "║");
            }
        }
        
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝\n");
    }

    public void displayBorrowRecordDetailed(int recordIndex) {
        if (recordIndex < 0 || recordIndex >= borrowHistory.size()) {
            System.out.println("Invalid record number!");
            return;
        }
        System.out.println(borrowHistory.get(recordIndex).getDetailedBorrowInfo());
    }

    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}