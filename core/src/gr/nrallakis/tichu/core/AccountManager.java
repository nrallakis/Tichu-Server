package gr.nrallakis.tichu.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import gr.nrallakis.tichu.database.DBManager;

public class AccountManager {

    private static final int DEFAULT_POINTS = 1000;
    private static final int DEFAULT_LEVEL = 1;
    private static final int DEFAULT_XP = 0;

    private static AccountManager instance = new AccountManager();
    private DBManager dbManager;

    private AccountManager() {
        dbManager = DBManager.getInstance();
        dbManager.connect();
    }

    public static AccountManager getInstance() {
        return instance;
    }

    /**
     * If an error occurs, return a string representing.
     * Else if everything goes well, return null.
     *
     * @param username ex. Nicholas R.
     * @param id       facebook id
     */
    public void registerAccount(String username, String id) {
        String date = new SimpleDateFormat("dd.MM.yyyy 'at' hh:mm").format(new Date());
        dbManager.executeUpdate("INSERT INTO ACCOUNTS values('" + username + "', '" + date + "', " + DEFAULT_POINTS + ", '" + id + "', " + DEFAULT_LEVEL + ", " + DEFAULT_XP + ")");
    }

    /**
     * Attempts to login, if there is no account with this id,
     * a new account is created
     * Use only id for guest
     */
    public void login(String id) {
        this.login("Guest-" + id, id);
    }

    public void login(String username, String id) {
        if (!isAccountRegistered(id)) {
            registerAccount(username, id);
        }
    }

    private boolean isAccountRegistered(String accountId) {
        try {
            ResultSet rs = dbManager.executeQuery("SELECT ID FROM ACCOUNTS WHERE ID == '" + accountId + "'");
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("No id: " + accountId + " exists in the database");
        }
        return false;
    }

    public int getAccountRankPoints(String accountId) {
        try {
            ResultSet rs = dbManager.executeQuery("SELECT RANK_POINTS FROM ACCOUNTS WHERE ID == '" + accountId + "'");
            rs.next();
            return rs.getInt("rank_points");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getAccountName(String accountId) {
        try {
            ResultSet rs = dbManager.executeQuery("SELECT USERNAME FROM ACCOUNTS WHERE ID == '" + accountId + "'");
            rs.next();
            return rs.getString("username");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
