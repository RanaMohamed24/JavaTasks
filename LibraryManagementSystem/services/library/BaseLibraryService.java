package services.library;

import models.library.LibraryItem;
import exceptions.ItemNotFoundException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseLibraryService<T extends LibraryItem> {
    protected List<T> items;
    protected String libraryName;

    public BaseLibraryService(String libraryName) {
        this.libraryName = libraryName;
        this.items = new ArrayList<>();
    }

    public void addItem(T item) {
        items.add(item);
    }

    public T getItemById(int id) throws ItemNotFoundException {
        for (T item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        throw new ItemNotFoundException("Item with ID " + id + " not found!");
    }

    public void displayAllItems() {
        if (items.isEmpty()) {
            System.out.println("No items in the library.");
            return;
        }
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  " + libraryName);
        System.out.println("╠════════════════════════════════════════╣");
        for (T item : items) {
            System.out.println("║ " + item.getItemDetails());
        }
        System.out.println("╚════════════════════════════════════════╝\n");
    }

    public void deleteItem(int id) throws ItemNotFoundException {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                items.remove(i);
                return;
            }
        }
        throw new ItemNotFoundException("Item with ID " + id + " not found!");
    }

    public void updateItem(int id, T newItem) throws ItemNotFoundException {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                items.set(i, newItem);
                return;
            }
        }
        throw new ItemNotFoundException("Item with ID " + id + " not found!");
    }

    public int getTotalItems() {
        return items.size();
    }
    
    public List<T> getAllItems() {
        return new ArrayList<>(items);
    }
}

