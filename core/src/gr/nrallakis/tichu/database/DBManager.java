package gr.nrallakis.tichu.database;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {

    private static DBManager instance = new DBManager();
    private static final String DB_URL = "jdbc:sqlite:serverdata.db";
    private Connection connection;

    private DBManager() {
    }

    public static DBManager getInstance() {
        return instance;
    }

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
            if (connection != null) {
                System.out.println("Connected to the database");
                DatabaseMetaData dm = (DatabaseMetaData) connection.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
                System.out.println("Database connection succesful..");
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql) {
        if (sql == null) System.out.println("sql cannot be null");
        try {
            return connection.prepareStatement(sql).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("FAIL");
            return null;
        }
    }

    public boolean executeUpdate(String sql) {
        try {
            connection.prepareStatement(sql).executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int nextId() {
        Preferences prefs = Gdx.app.getPreferences("data");
        int value = prefs.getInteger("id") + 1;
        prefs.putInteger("id", value);
        prefs.flush();
        return value;
    }
}
