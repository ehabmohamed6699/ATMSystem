import exceptions.InsufficientAmountException;
import exceptions.UserNotFoundException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class User extends JFrame{
    private JPanel userFromPanel;
    private JLabel balanceLabel;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton transferButton;
    private JButton transactionsButton;
    private JButton logoutButton;

    private TransactionsForm transactionsForm;
    SystemForm parent;
    public User(String title, boolean visible, SystemForm parent){
        super(title);
        this.parent = parent;
        setContentPane(userFromPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(400,200,500,300);
        balanceLabel.setText(String.valueOf(SystemForm.currentAccount.getBalance()));
        setVisible(visible);
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = JOptionPane.showInputDialog(depositButton,"Enter amount to deposit");
                if(value != null){

                    try{
                        while (value.length() == 0 || Double.parseDouble(value) <= 0){
                            String message;
                            if(value.length() == 0){
                                message = "Enter the amount to deposit";
                            }else{
                                message = "Amount must be greater than 0";
                            }
                            JOptionPane.showMessageDialog(depositButton, message, "Error", JOptionPane.ERROR_MESSAGE);
                            value = JOptionPane.showInputDialog(depositButton,"Enter amount to deposit");
                        }
                        SystemForm.currentAccount.deposit(Double.parseDouble(value));
                        balanceLabel.setText(String.valueOf(SystemForm.currentAccount.getBalance()));
                        JOptionPane.showMessageDialog(depositButton, "Successful operation");
                    }catch (NumberFormatException nfe){
                        JOptionPane.showMessageDialog(depositButton, "Invalid amount format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = JOptionPane.showInputDialog(depositButton,"Enter amount to withdraw");
                if(value != null){
                    try {
                        while (value.length() == 0 || Double.parseDouble(value) <= 0){
                            String message;
                            if(value.length() == 0){
                                message = "Enter the amount to withdraw";
                            }else{
                                message = "Amount must be greater than 0";
                            }
                            JOptionPane.showMessageDialog(depositButton, message, "Error", JOptionPane.ERROR_MESSAGE);
                            value = JOptionPane.showInputDialog(depositButton,"Enter amount to withdraw");
                        }
                        SystemForm.currentAccount.withdraw(Double.parseDouble(value));
                        balanceLabel.setText(String.valueOf(SystemForm.currentAccount.getBalance()));
                        JOptionPane.showMessageDialog(depositButton, "Successful operation");
                    }catch (InsufficientAmountException iae){
                        JOptionPane.showMessageDialog(depositButton, iae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }catch (NumberFormatException nfe){
                        JOptionPane.showMessageDialog(depositButton, "Invalid amount format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = JOptionPane.showInputDialog(depositButton,"Enter amount to transfer");
                if(value != null){
                    try {
                        while (value.length() == 0 || Double.parseDouble(value) <= 0){
                            String message;
                            if(value.length() == 0){
                                message = "Enter the amount to transfer";
                            }else{
                                message = "Amount must be greater than 0";
                            }
                            JOptionPane.showMessageDialog(depositButton, message, "Error", JOptionPane.ERROR_MESSAGE);
                            value = JOptionPane.showInputDialog(depositButton,"Enter amount to transfer");
                        }
                        String ID = JOptionPane.showInputDialog(depositButton,"Enter account ID");
                        while (ID.length() == 0){
                            String message = "Enter the account ID";
                            JOptionPane.showMessageDialog(depositButton, message, "Error", JOptionPane.ERROR_MESSAGE);
                            ID = JOptionPane.showInputDialog(depositButton,"Enter account ID");
                        }
                        SystemForm.currentAccount.transfer(Integer.parseInt(ID),Double.parseDouble(value));
                        balanceLabel.setText(String.valueOf(SystemForm.currentAccount.getBalance()));
                        JOptionPane.showMessageDialog(depositButton, "Successful operation");
                    }catch (InsufficientAmountException iae){
                        JOptionPane.showMessageDialog(depositButton, iae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }catch (NumberFormatException nfe){
                        JOptionPane.showMessageDialog(depositButton, "Invalid amount format", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (UserNotFoundException ex) {
                        JOptionPane.showMessageDialog(depositButton, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        transactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(transactionsForm != null){
                    transactionsForm.dispatchEvent(new WindowEvent(transactionsForm, WindowEvent.WINDOW_CLOSING));
                }
                transactionsForm = new TransactionsForm("Transactions");
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(logoutButton, "Logged out successfully");
                SystemForm.currentAccount = null;
                setVisible(false);
                parent.toggleVisible();
            }
        });
    }
}
