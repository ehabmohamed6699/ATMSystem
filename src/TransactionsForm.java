import entities.Transaction;

import javax.swing.*;


import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

class TransactionTableModel extends AbstractTableModel {
    private ArrayList<Transaction> transactions;
    private String[] columnNames = {"ID", "Date", "Type", "Amount"};

    public TransactionTableModel(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public int getRowCount() {
        return transactions.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Transaction transaction = transactions.get(row);
        switch (col) {
            case 0: // ID
                return transaction.getID();
            case 1: // Date
                return transaction.getDate();
            case 2: // Type
                return transaction.getType();
            case 3: // Amount
                return transaction.getAmount();
            default:
                return null;
        }
    }
}

public class TransactionsForm extends JFrame{
    private JPanel transactionsPanel;

    public TransactionsForm(String title){
        super(title);
        setBounds(450, 250, 500, 300);

        String[] labels = {"ID", "Date", "Type", "Amount"};
        TransactionTableModel model = new TransactionTableModel(SystemForm.currentAccount.getTransactions());
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(table);

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

}
