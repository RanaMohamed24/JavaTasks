package validation;

import java.util.regex.Pattern;
import java.util.List;
import models.library.LibraryItem;
import models.client.Client;

public class Validator {
    
    public static boolean isValidId(int id) {
        return id > 0;
    }
    
    public static String getIdError(int id) {
        if (id <= 0) {
            return "ID must be a positive number greater than 0";
        }
        return null;
    }
    
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return name.matches("^[\\p{L}\\d\\s\\-']+$");
    }
    
    public static String getNameError(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Field cannot be empty! Please enter a value";
        }
        if (!isValidName(name)) {
            return "Name must contain letters and numbers only (no special characters)";
        }
        return null;
    }
    
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }
    
    public static String getEmailError(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email cannot be empty! Please enter an email address";
        }
        if (!isValidEmail(email)) {
            return "Invalid email format! (Example: user@example.com)";
        }
        return null;
    }
    
    public static boolean isIdUniqueInItems(int id, List<? extends LibraryItem> items) {
        return items.stream().noneMatch(item -> item.getId() == id);
    }
    
    public static String getUniqueIdItemError(int id) {
        return "ID (" + id + ") already exists! Please use a different ID";
    }
    
    public static boolean isEmailUniqueInClients(String email, List<Client> clients) {
        return clients.stream().noneMatch(client -> client.getEmail().equalsIgnoreCase(email));
    }
    
    public static String getUniqueEmailError(String email) {
        return "Email (" + email + ") is already registered! Please use a different email";
    }
    
    public static boolean isIdUniqueInClients(int id, List<Client> clients) {
        return clients.stream().noneMatch(client -> client.getId() == id);
    }
    
    public static String getUniqueIdClientError(int id) {
        return "ID (" + id + ") already exists! Please use a different ID";
    }
    
    public static boolean isValidPages(int pages) {
        return pages > 0;
    }
    
    public static String getPagesError(int pages) {
        if (pages <= 0) {
            return "Number of pages must be a positive number greater than 0";
        }
        return null;
    }
    
   
    
    
    public static boolean isValidIssueNumber(int issueNumber) {
        return issueNumber > 0;
    }
    
    public static String getIssueNumberError(int issueNumber) {
        if (issueNumber <= 0) {
            return "Issue number must be a positive number greater than 0";
        }
        return null;
    }
    
    public static boolean isValidDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return false;
        }
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        if (!Pattern.matches(dateRegex, date)) {
            return false;
        }
        
        try {
            String[] parts = date.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            
            if (month < 1 || month > 12) return false;
            if (day < 1 || day > 31) return false;
            if (year < 1900) return false;
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static String getDateError(String date) {
        if (date == null || date.trim().isEmpty()) {
            return "Date cannot be empty! Enter date in format YYYY-MM-DD";
        }
        if (!isValidDate(date)) {
            return "Invalid date format! Use format: YYYY-MM-DD (Example: 2025-01-15)";
        }
        return null;
    }
    
    public static boolean isValidAuthor(String author) {
        return isValidName(author);
    }
    
    public static String getAuthorError(String author) {
        return getNameError(author);
    }
    
    public static boolean isValidPublisher(String publisher) {
        return isValidName(publisher);
    }
    
    public static String getPublisherError(String publisher) {
        return getNameError(publisher);
    }
}
