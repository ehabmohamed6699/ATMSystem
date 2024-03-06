package entities;

import exceptions.InsufficientAmountException;
import exceptions.UserNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Account implements Serializable {
    public int ID;
    static int counter = AccountsManager.usersNum();

    public String getPassword() {
        return password;
    }

    private String password;

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    private ArrayList<Transaction> transactions;

    public double getBalance() {
        return balance;
    }

    private double balance;

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Account(String password, double balance) {
        this.ID = counter++;
        this.password = password;
        this.transactions = new ArrayList<>();
        this.balance = balance;
        addAccountFile();
    }

    private void addAccountFile(){
        File f = new File(String.format("%s/%d.txt",AccountsManager.getAccountsDir() ,ID));
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Account findAccount(int ID) throws UserNotFoundException {
        File f = new File("accounts/"+String.valueOf(ID)+".txt");
        if(f.exists()){
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                return (Account) ois.readObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }else{
            throw new UserNotFoundException();
        }
    }

    public void deposit(double amount){
        balance+=amount;
        transactions.add(new Transaction(new Date(), Type.DEPOSIT, amount));
        addAccountFile();
    }

    public void withdraw(double amount) throws InsufficientAmountException{
        if(amount <= balance){
            balance -= amount;
            transactions.add(new Transaction(new Date(), Type.WITHDRAW, amount));
            addAccountFile();
        }else{
            throw new InsufficientAmountException("You don't have enough balance");
        }
    }

    public void transfer(int ID, double amount) throws InsufficientAmountException, UserNotFoundException {
        if(amount <= balance){
            Date now = new Date();
            Account a = findAccount(ID);
            balance -= amount;
            transactions.add(new Transaction(now, Type.TRANSFER, amount));
            addAccountFile();
            a.setBalance(a.getBalance() + amount);
            a.transactions.add(new Transaction(now, Type.RECEIVE, amount));
            a.addAccountFile();
        }else{
            throw new InsufficientAmountException("You don't have enough balance");
        }
    }
}
