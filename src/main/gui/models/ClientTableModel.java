package main.gui.models;

import main.entities.Client;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ClientTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Name", "Email", "Phone"};
    private List<Client> clients;

    public ClientTableModel(List<Client> clients) {
        this.clients = clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
        fireTableDataChanged(); 
    }

    @Override
    public int getRowCount() {
        return clients.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Client client = clients.get(rowIndex);
        switch (columnIndex) {
            case 0: return client.getId();
            case 1: return client.getName();
            case 2: return client.getEmail();
            case 3: return client.getPhone();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}