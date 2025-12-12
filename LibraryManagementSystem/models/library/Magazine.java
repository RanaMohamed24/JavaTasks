package models.library;

public class Magazine extends LibraryItem {
    private String publisher;
    private int issueNumber;
    private String releaseDate;

    public Magazine(int id, String title, String publisher, int issueNumber, String releaseDate, int totalCopies) {
        super(id, title, totalCopies);
        this.publisher = publisher;
        this.issueNumber = issueNumber;
        this.releaseDate = releaseDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String getItemDetails() {
        return "MAGAZINE [ID: " + id + ", Title: " + title + ", Publisher: " + publisher +
                ", Issue: " + issueNumber + ", Release Date: " + releaseDate + 
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
