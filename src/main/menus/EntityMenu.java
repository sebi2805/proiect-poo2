package main.menus;
import main.services.BaseService;
import main.util.Option;

import java.util.List;
import java.util.Scanner;

public abstract class EntityMenu<T> {
    protected final Scanner scanner = new Scanner(System.in);
    protected final BaseService<T> service;

    public EntityMenu(BaseService<T> service) {
        this.service = service;
    }

    public void displayMenu() {
        while (true) {
            printMenuOptions();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    displayAll();
                    break;
                case 2:
                    searchById();
                    break;
                case 3:
                    add();
                    break;
                case 4:
                    update();
                    break;
                case 5:
                    delete();
                    break;
                case 0:
                    return; // Exit to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println("\n");

        }
    }

    protected abstract void printMenuOptions();

    protected abstract void displayAll();

    protected abstract void searchById();

    protected abstract void add();

    protected abstract void update();

    protected abstract void delete();
    protected void waitForUserInput() {
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    protected String getUserChoice(List<Option> options, String prompt) {
        System.out.println(prompt);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i).getLabel());
        }
        System.out.print("Enter your choice: ");
        String input = scanner.nextLine();

        if (input.isEmpty()) {
            return null;
        }

        int choice = Integer.parseInt(input);
        return options.get(choice - 1).getValue();
    }

}