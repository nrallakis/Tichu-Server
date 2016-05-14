package gr.nrallakis.tichu.server.database;

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
    private static final String JDBC_DRIVER = "org.sqlite.JDBC";
    private Connection sqlConnection;

    private DBManager() {
    }

    public static DBManager getInstance() {
        return instance;
    }

    public void connect() {
        try {
            Class.forName(JDBC_DRIVER);
            sqlConnection = DriverManager.getConnection(DB_URL);
            if (sqlConnection != null) {
                printInfo();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void printInfo() throws SQLException {
        System.out.println("Connected to the database");
        DatabaseMetaData dm = sqlConnection.getMetaData();
        System.out.println("Driver name: " + dm.getDriverName());
        System.out.println("Driver version: " + dm.getDriverVersion());
        System.out.println("Product name: " + dm.getDatabaseProductName());
        System.out.println("Product version: " + dm.getDatabaseProductVersion());
        System.out.println("Database connection successful..");
    }

    public ResultSet executeQuery(String sql) {
        if (sql == null)
            throw new IllegalArgumentException("Sql parameter cannot be null");
        try {
            return sqlConnection.prepareStatement(sql).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Sql failed to execute query: " + sql);
            return null;
        }
    }

    public boolean executeUpdate(String sql) {
        if (sql == null)
            throw new IllegalArgumentException("Sql parameter cannot be null");
        try {
            System.out.println(sql);
            sqlConnection.prepareStatement(sql).executeUpdate();
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
