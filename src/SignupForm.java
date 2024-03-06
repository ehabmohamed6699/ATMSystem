import entities.Account;
import exceptions.InvalidPasswordException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupForm extends JFrame{
    private JPanel signupPanel;
    private JTextField initialAmountTextfield;
    private JTextField passwordTextfield;
    private JButton createButton;

    public SignupForm(){
        super("Signup");
        setContentPane(signupPanel);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(450,250,300,300);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(initialAmountTextfield.getText().length() == 0 || passwordTextfield.getText().length() == 0){
                    JOptionPane.showMessageDialog(createButton, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    try {
                        Double initialAmount = Double.parseDouble(initialAmountTextfield.getText());
                        if(initialAmount < 0){
                            JOptionPane.showMessageDialog(createButton, "Invalid amount can't be less than 0", "Error", JOptionPane.ERROR_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(createButton, "Account created successfully, your ID is " + new Account(passwordTextfield.getText(), initialAmount).ID);
                            setVisible(false);
                        }
                    }catch (NumberFormatException nfe){
                        JOptionPane.showMessageDialog(createButton, "Invalid data, enter number in initial amount", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        setVisible(true);
    }
}
