package models.library;

import models.CrudEntity;

public abstract class LibraryItem implements CrudEntity<LibraryItem> {
    protected int id;
    protected String title;
    protected int totalCopies;
    protected int availableCopies;
    protected ItemStatus status;

    public enum ItemStatus {
        AVAILABLE("Available"),
        PARTIALLY_BORROWED("Partially Borrowed"),
        FULLY_BORROWED("Fully Borrowed"),
        UNAVAILABLE("Unavailable");

        private final String displayName;

        ItemStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public LibraryItem(int id, String title, int totalCopies) {
        this.id = id;
        this.title = title;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        updateStatus();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
        if (this.availableCopies > totalCopies) {
            this.availableCopies = totalCopies;
        }
        updateStatus();
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    // Allow controlled update of available copies from outside (e.g., when updating item details)
    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
        updateStatus();
    }

    public int getBorrowedCopies() {
        return totalCopies - availableCopies;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public boolean borrowCopy() {
        if (availableCopies > 0) {
            availableCopies--;
            updateStatus();
            return true;
        }
        return false;
    }

    public boolean returnCopy() {
        if (availableCopies < totalCopies) {
            availableCopies++;
            updateStatus();
            return true;
        }
        return false;
    }

    public boolean isAvailable() {
        return availableCopies > 0;
    }

    public void addCopies(int count) {
        if (count > 0) {
            totalCopies += count;
            availableCopies += count;
            updateStatus();
        }
    }

    public void removeCopies(int count) throws Exception {
        if (count > getBorrowedCopies()) {
            throw new Exception("Cannot remove " + count + " copies. Only " + getBorrowedCopies() + " borrowed copies available to remove.");
        }
        totalCopies -= count;
        updateStatus();
    }

    private void updateStatus() {
        if (availableCopies == totalCopies) {
            this.status = ItemStatus.AVAILABLE;
        } else if (availableCopies == 0) {
            this.status = ItemStatus.FULLY_BORROWED;
        } else if (availableCopies > 0) {
            this.status = ItemStatus.PARTIALLY_BORROWED;
        } else {
            this.status = ItemStatus.UNAVAILABLE;
        }
    }

    public abstract String getItemDetails();

    // --- CrudEntity implementations ---
    @Override
    public LibraryItem create() {
        // Domain-level create would typically persist to a repository; here we return this instance
        return this;
    }

    @Override
    public LibraryItem read() {
        return this;
    }

    @Override
    public void update(LibraryItem other) {
        if (other == null) return;
        this.title = other.title;
        this.setTotalCopies(other.totalCopies);
        this.setAvailableCopies(other.availableCopies);
        // id is intentionally not overwritten
    }

    @Override
    public void delete() {
        // Mark as unavailable by removing copies
        this.availableCopies = 0;
        this.totalCopies = 0;
        updateStatus();
    }
}
