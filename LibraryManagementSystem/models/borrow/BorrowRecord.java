package models.borrow;
import java.time.LocalDate;

public class BorrowRecord {
    private int borrowId;
    private int clientId;
    private int itemId;
    private String itemTitle;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status;

    public BorrowRecord(int borrowId, int clientId, int itemId, String itemTitle, 
                        LocalDate borrowDate, LocalDate dueDate) {
        this.borrowId = borrowId;
        this.clientId = clientId;
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.status = "BORROWED";
    }

    public int getBorrowId() {
        return borrowId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        this.status = "RETURNED";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isOverdue() {
        if (status.equals("RETURNED")) {
            return false;
        }
        return LocalDate.now().isAfter(dueDate);
    }

    public long getDaysOverdue() {
        if (status.equals("RETURNED")) {
            return 0;
        }
        if (!isOverdue()) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());
    }

    public String getDetailedBorrowInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n┌──────────────────────────────────────────┐\n");
        sb.append("│ BORROW RECORD #").append(String.format("%03d", borrowId)).append("\n");
        sb.append("├──────────────────────────────────────────┤\n");
        sb.append("│ Item: ").append(padRight(itemTitle, 35)).append("│\n");
        sb.append("│ Borrowed Date: ").append(padRight(borrowDate.toString(), 27)).append("│\n");
        sb.append("│ Due Date: ").append(padRight(dueDate.toString(), 31)).append("│\n");
        
        if (returnDate != null) {
            sb.append("│ Returned Date: ").append(padRight(returnDate.toString(), 27)).append("│\n");
        }
        
        sb.append("│ Status: ").append(padRight(status, 34)).append("│\n");
        
        if (isOverdue()) {
            sb.append("│ OVERDUE: ").append(getDaysOverdue()).append(" days                          │\n");
        }
        
        sb.append("└──────────────────────────────────────────┘");
        return sb.toString();
    }

    public String getShortBorrowInfo() {
        String status_display = status.equals("BORROWED") ? "BORROWED" : "RETURNED";
        String overdue = isOverdue() ? String.format(" [OVERDUE %d days]", getDaysOverdue()) : "";
        return String.format("%-35s | %-8s | Due: %s%s", 
                            itemTitle, 
                            status_display, 
                            dueDate, 
                            overdue);
    }

    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}