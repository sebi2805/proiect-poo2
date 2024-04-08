package main;

import main.entities.Client;
import main.gui.panels.ClientSearchPanel;
import main.storage.FileService;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Asigură-te că interfața grafică este creată și actualizată de pe firul de execuție EDT
//        SwingUtilities.invokeLater(() -> {
//            createAndShowGUI();
//        });
        FileService fileService = FileService.getInstance();
        fileService.getClientManager().findById("0401abea-95fb-4980-b462-d90efd79a987").ifPresent(client -> {
            System.out.println(client.getName());
        });
    }

    private static void createAndShowGUI() {
        // Creează fereastra principală
        JFrame frame = new JFrame("Client Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adaugă panel-ul de căutare a clienților
        ClientSearchPanel searchPanel = new ClientSearchPanel();
        frame.add(searchPanel);

        // Setează dimensiunea ferestrei
        frame.pack();
        // Centrează fereastra pe ecran
        frame.setLocationRelativeTo(null);
        // Face fereastra vizibilă
        frame.setVisible(true);
    }
}
