package services.client;

import models.client.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ClientService {
    private List<Client> clients;

    public ClientService() {
        this.clients = new ArrayList<>();
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public Client getClientById(int id) throws Exception {
        return clients.stream()
                .filter(client -> client.getId() == id)
                .findFirst()
                .orElseThrow(() -> new Exception("Client with ID " + id + " not found!"));
    }

    public void displayAllClients() {
        if (clients.isEmpty()) {
            System.out.println("No clients in the system.");
            return;
        }
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  All Clients");
        System.out.println("╠════════════════════════════════════════╣");
        clients.forEach(client -> System.out.println("║ " + client.getClientDetails()));
        System.out.println("╚════════════════════════════════════════╝\n");
    }

    public void deleteClient(int id) throws Exception {
        boolean removed = clients.removeIf(c -> c.getId() == id);
        if (!removed) {
            throw new Exception("Client with ID " + id + " not found!");
        }
    }

    public void updateClient(int id, Client newClient) throws Exception {
        Optional<Integer> indexOpt = IntStream.range(0, clients.size())
                .filter(i -> clients.get(i).getId() == id)
                .boxed()
                .findFirst();

        if (indexOpt.isPresent()) {
            clients.set(indexOpt.get(), newClient);
            return;
        }

        throw new Exception("Client with ID " + id + " not found!");
    }

    public int getTotalClients() {
        return clients.size();
    }
    
    public List<Client> getAllClients() {
        return new ArrayList<>(clients);
    }

    public Client getClientByEmail(String email) throws Exception {
        return clients.stream()
                .filter(client -> client.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new Exception("Client with email " + email + " not found!"));
    }

    public int getClientsByName(String name) {
        return (int) clients.stream()
                .filter(client -> client.getName().equalsIgnoreCase(name))
                .count();
    }

    public void displayClientsByName(String name) {
        System.out.println("\n--- Clients named " + name + " ---");
        List<Client> matches = clients.stream()
                .filter(client -> client.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());

        if (matches.isEmpty()) {
            System.out.println("No clients found with name: " + name);
            return;
        }

        matches.forEach(client -> System.out.println(client.getClientDetails()));
    }
}
