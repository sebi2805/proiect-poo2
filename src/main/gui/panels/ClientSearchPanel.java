package main.gui.panels;

import main.entities.Client;
import main.gui.models.ClientTableModel;
import main.services.ClientService;
import main.services.ServiceManager;
import main.util.SearchCriteriaPerson;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// În clasa de GUI dedicată căutării clienților
public class ClientSearchPanel extends JPanel {
    private final ClientTableModel tableModel;
    private final JTextField nameField;
    private final JTextField emailField;
    private final JTextField phoneField;
    private JTable clientsTable;

    public ClientSearchPanel() {
        // Initializează componentele
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        clientsTable = new JTable();

        // Adaugă componentele la panel
        this.add(nameField);
        this.add(emailField);
        this.add(phoneField);
        this.add(searchButton);
        tableModel = new ClientTableModel(new ArrayList<>());
        clientsTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(clientsTable);
        this.add(scrollPane);

        searchButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            System.out.println("sebi");
            new SwingWorker<List<Client>, Void>() {
                @Override
                protected List<Client> doInBackground() throws Exception {
                    SearchCriteriaPerson criteria = new SearchCriteriaPerson(name, email, phone);
                    return searchClients(criteria);
                }

                @Override
                protected void done() {
                    try {
                        List<Client> clients = get();
                        System.out.println("Clienti in done(): " + clients.size()); // Debugging
                        updateTable(clients);
                    } catch (Exception ex) {
                        ex.printStackTrace(); // sau gestionează eroarea într-un mod mai elegant
                    }
                }
            }.execute();
        });
    }

    public List<Client> searchClients(SearchCriteriaPerson criteria) {
        // Presupunând că serviceManager și clientManager sunt deja inițializați și disponibili\
        System.out.println("2");
        ClientService clientService = ServiceManager.getClientService();
        return clientService.searchClients(criteria);
    }

    private void updateTable(List<Client> clients) {
        System.out.println("Actualizarea tabelului cu " + clients.size() + " clienți."); // Debugging
        tableModel.setClients(clients);
        clientsTable.revalidate();
        clientsTable.repaint();
    }
}
