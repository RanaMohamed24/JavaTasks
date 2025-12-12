package models.library;

public class Book extends LibraryItem {
    private String author;
    private int pages;
   
  

    public Book(int id, String title, String author, int pages, int totalCopies) {
        super(id, title, totalCopies);
        this.author = author;
        this.pages = pages;
        
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }



    @Override
    public String getItemDetails() {
    return "BOOK [ID: " + id + ", Title: " + title + ", Author: " + author +
        ", Pages: " + pages + 
       
        ", Total: " + totalCopies + ", Available: " + availableCopies + 
        ", Status: " + status.getDisplayName() + "]";
    }

    public String getStockInfo() {
        return String.format("%-35s | Total: %2d | Available: %2d | Borrowed: %2d | %s",
                title,
                totalCopies,
                availableCopies,
                getBorrowedCopies(),
                status.getDisplayName());
    }
}

