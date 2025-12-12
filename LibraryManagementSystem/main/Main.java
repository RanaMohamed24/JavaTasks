package main;
import ui.MenuSystem;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║  Library Management System            ║");
        System.out.println("║  with Comprehensive Validation        ║");
        System.out.println("╚═══════════════════════════════════════╝\n");
        
        MenuSystem menu = new MenuSystem();
        menu.displayMainMenu();
    }
}