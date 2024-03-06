package entities;

import java.io.*;
import java.util.Date;
import java.util.Scanner;

public class Transaction implements Serializable{
    public int ID;
    private Date date;
    private Type type;
    private double amount;
    public static int counter;

    public int getID() {
        return ID;
    }

    public Date getDate() {
        return date;
    }

    public Type getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    static File counterFile = new File("transactionsCount.txt");
    static {
        try {
            counter = new Scanner(counterFile).nextInt();
        } catch (FileNotFoundException e) {
            try {
                FileWriter writer = new FileWriter(counterFile);
                writer.write('0');
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }finally {
                counter = 0;
            }

        }
    }

    public Transaction(Date date, Type type, double amount) {
        this.ID = counter++;
        this.date = date;
        this.type = type;
        this.amount = amount;
        try {
            Writer writer = new FileWriter(counterFile);
            writer.write(String.valueOf(counter));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
