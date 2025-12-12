package services.library;

import models.library.Magazine;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MagazineService extends BaseLibraryService<Magazine> {
    
    public MagazineService(String libraryName) {
        super(libraryName);
    }

    public Magazine getMagazineByPublisher(String publisher) {
        Optional<Magazine> opt = items.stream()
                .filter(magazine -> magazine.getPublisher().equalsIgnoreCase(publisher))
                .findFirst();
        return opt.orElse(null);
    }

    public int getMagazinesByPublisher(String publisher) {
        return (int) items.stream()
                .filter(magazine -> magazine.getPublisher().equalsIgnoreCase(publisher))
                .count();
    }

    public void displayMagazinesByPublisher(String publisher) {
        System.out.println("\n--- Magazines by " + publisher + " ---");
        List<Magazine> matches = items.stream()
                .filter(magazine -> magazine.getPublisher().equalsIgnoreCase(publisher))
                .collect(Collectors.toList());

        if (matches.isEmpty()) {
            System.out.println("No magazines found by publisher: " + publisher);
            return;
        }

        matches.forEach(magazine -> System.out.println(magazine.getItemDetails()));
    }
}