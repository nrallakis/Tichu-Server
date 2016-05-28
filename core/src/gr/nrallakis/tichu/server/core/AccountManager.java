package gr.nrallakis.tichu.server.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import gr.nrallakis.tichu.server.database.DBManager;
import gr.nrallakis.tichu.server.database.SQLBuilder;

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
     * Registers an account to the db.
     *
     * @param username ex. Nicholas R.
     * @param id the facebook id
     */
    public void registerAccount(String username, String id) {
        String date = new SimpleDateFormat("dd.MM.yyyy 'at' hh:mm").format(new Date());
        String update = new SQLBuilder()
                .insertInto("ACCOUNTS")
                .values(username, date, DEFAULT_POINTS, id, DEFAULT_LEVEL, DEFAULT_XP)
                .build();
        dbManager.executeUpdate(update);
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
            String query = new SQLBuilder().select("ID").from("ACCOUNTS").where("ID").equal(accountId).build();
            ResultSet rs = dbManager.executeQuery(query);
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
            String query = new SQLBuilder().select("RANK_POINTS").from("ACCOUNTS").where("ID").equal(accountId).build();
            ResultSet rs = dbManager.executeQuery(query);
            rs.next();
            return rs.getInt("rank_points");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getAccountName(String accountId) {
        try {
            String query = new SQLBuilder().select("USERNAME").from("ACCOUNTS").where("ID").equal(accountId).build();
            ResultSet rs = dbManager.executeQuery(query);
            rs.next();
            return rs.getString("username");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
