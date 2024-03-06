package entities;

import java.io.File;

public class AccountsManager {
    public static File getAccountsDir() {
        return accountsDir;
    }

    private static File accountsDir = new File("accounts");
    public static int usersNum(){
        return accountsDir.list().length;
    }
}
