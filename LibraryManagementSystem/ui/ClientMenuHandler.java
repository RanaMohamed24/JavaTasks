package ui;

import models.client.Client;
import services.client.ClientService;
import validation.Validator;
import java.util.Scanner;

public class ClientMenuHandler {
    private ClientService clientService;
    private Scanner scanner;

    public ClientMenuHandler(ClientService clientService, Scanner scanner) {
        this.clientService = clientService;
        this.scanner = scanner;
    }

    public void manageClients() {
        boolean managing = true;
        while (managing) {
            System.out.println("\n--- Client Management ---");
            System.out.println("1. Add Client");
            System.out.println("2. View All Clients");
            System.out.println("3. Search Client by ID");
            System.out.println("4. Update Client");
            System.out.println("5. Delete Client");
            System.out.println("6. Back");
            System.out.print("Choose: ");
            System.out.println("(Enter the option number and press Enter. Invalid inputs return to the previous menu.)");
            
            int choice = getIntInput();
            try {
                switch (choice) {
                    case 1:
                        addClient();
                        break;
                    case 2:
                        clientService.displayAllClients();
                        break;
                    case 3:
                        searchClient();
                        break;
                    case 4:
                        updateClient();
                        break;
                    case 5:
                        deleteClient();
                        break;
                    case 6:
                        managing = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please select one of the displayed menu numbers.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addClient() throws Exception {
        System.out.print("Enter Client ID: ");
        int id = getIntInput();
        
        if (id == -1) return;
        
        String idError = Validator.getIdError(id);
        if (idError != null) {
            System.out.println("Error: " + idError);
            return;
        }
        
        if (!Validator.isIdUniqueInClients(id, clientService.getAllClients())) {
            System.out.println("Error: " + Validator.getUniqueIdClientError(id));
            return;
        }
        
        scanner.nextLine();
        
        System.out.print("Enter Client Name: ");
        String name = scanner.nextLine();
        String nameError = Validator.getNameError(name);
        if (nameError != null) {
            System.out.println("Error: " + nameError);
            return;
        }
        
        System.out.print("Enter Client Email: ");
        String email = scanner.nextLine();
        String emailError = Validator.getEmailError(email);
        if (emailError != null) {
            System.out.println("Error: " + emailError);
            return;
        }
        
        if (!Validator.isEmailUniqueInClients(email, clientService.getAllClients())) {
            System.out.println("Error: " + Validator.getUniqueEmailError(email));
            return;
        }
        
        Client client = new Client(id, name, email);
        clientService.addClient(client);
        System.out.println("Client added successfully!");
    }

    private void searchClient() throws Exception {
        System.out.print("Enter Client ID to search: ");
        int id = getIntInput();
        
        if (id == -1) return;
        
        try {
            Client client = clientService.getClientById(id);
            System.out.println("\n" + client.getClientDetails());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateClient() throws Exception {
        System.out.print("Enter Client ID to update: ");
        int id = getIntInput();
        
        if (id == -1) return;
        
        try {
            clientService.getClientById(id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        
        scanner.nextLine();
        
        System.out.print("Enter new Client Name: ");
        String name = scanner.nextLine();
        String nameError = Validator.getNameError(name);
        if (nameError != null) {
            System.out.println("Error: " + nameError);
            return;
        }
        
        System.out.print("Enter new Client Email: ");
        String email = scanner.nextLine();
        String emailError = Validator.getEmailError(email);
        if (emailError != null) {
            System.out.println("Error: " + emailError);
            return;
        }
        
        try {
            Client oldClient = clientService.getClientById(id);
            if (!oldClient.getEmail().equalsIgnoreCase(email)) {
                if (!Validator.isEmailUniqueInClients(email, clientService.getAllClients())) {
                    System.out.println("Error: " + Validator.getUniqueEmailError(email));
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        
        Client updatedClient = new Client(id, name, email);
        clientService.updateClient(id, updatedClient);
        System.out.println("Client updated successfully!");
    }

    private void deleteClient() throws Exception {
        System.out.print("Enter Client ID to delete: ");
        int id = getIntInput();
        
        if (id == -1) return;
        
        try {
            clientService.deleteClient(id);
            System.out.println("Client deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int getIntInput() {
        try {
            int value = scanner.nextInt();
            return value;
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Invalid input: please enter a whole number and press Enter. Returning to previous menu.");
            return -1;
        }
    }
}