package services.library;

import models.library.Book;
import java.util.List;
import java.util.stream.Collectors;

public class BookService extends BaseLibraryService<Book> {
    
    public BookService(String libraryName) {
        super(libraryName);
    }

   

    public int getBooksByAuthor(String author) {
        return (int) items.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .count();
    }

    public void displayBooksByAuthor(String author) {
        System.out.println("\n--- Books by " + author + " ---");
    List<Book> matches = items.stream()
        .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());

        if (matches.isEmpty()) {
            System.out.println("No books found by author: " + author);
            return;
        }

        matches.forEach(book -> System.out.println(book.getItemDetails()));
    }
}