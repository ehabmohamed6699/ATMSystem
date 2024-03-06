import entities.Account;
import entities.AccountsManager;
import exceptions.InvalidPasswordException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SystemForm extends JFrame{
    private JPanel appPanel;
    private JLabel titleLabel;
    private JLabel accIDLabel;
    private JLabel accPassLabel;
    private JTextField accIDTextfield;
    private JTextField accPassTextfield;
    private JButton logInButton;
    private JButton signupButton;

    public static Account currentAccount;

    public SystemForm(String title) {
        super(title);
        setContentPane(appPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(400,200,300,300);
        SystemForm parent = this;
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(accIDTextfield.getText().length() == 0 || accPassTextfield.getText().length() == 0){
                    JOptionPane.showMessageDialog(logInButton, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    Account acc;
                    try {
                        acc = login();
                        if(acc == null){
                            JOptionPane.showMessageDialog(logInButton, "There's no account with this ID", "Error", JOptionPane.ERROR_MESSAGE);
                        }else{
                            currentAccount = acc;
                            JOptionPane.showMessageDialog(logInButton, "Logged in successfully");
                            accPassTextfield.setText("");
                            accIDTextfield.setText("");
                            setVisible(false);
                            User user = new User(String.valueOf(currentAccount.ID), true, parent);
                        }

                    }catch (InvalidPasswordException ipe){
                        JOptionPane.showMessageDialog(logInButton, ipe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(logInButton, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        setVisible(true);
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignupForm signupForm = new SignupForm();
            }
        });
    }
    public void toggleVisible(){
        if(isVisible()){
            setVisible(false);
        }else{
            setVisible(true);
        }
    }
    public Account login() throws InvalidPasswordException {
        File f = new File(String.format("%s/%s.txt",AccountsManager.getAccountsDir() ,accIDTextfield.getText()));
        if(!f.exists()){
            return null;
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            Account a = (Account) ois.readObject();
            if(a.getPassword().equals(accPassTextfield.getText())){
                return a;
            }else{
                throw new InvalidPasswordException("Invalid password");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
//        initiateWorkspace();
        SystemForm app = new SystemForm("ATM System");
    }
    static void freeWorkspace(){
        File f = new File("transactionsCount.txt");
        f.delete();
        f = new File("accounts");
        for (File file: f.listFiles())
            if (!file.isDirectory())
                file.delete();
    }
    static void initiateWorkspace(){
        freeWorkspace();
        Account a = new Account("1234",120);
        Account b = new Account("1234",120);
    }
}
