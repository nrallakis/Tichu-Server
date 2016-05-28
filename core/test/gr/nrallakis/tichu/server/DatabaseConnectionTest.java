package gr.nrallakis.tichu.server;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

public class DatabaseConnectionTest {

    @Test
    public void testConnection() {
        Connection sqlConnection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            sqlConnection = DriverManager.getConnection("jdbc:sqlite:serverdata.db");
        } catch (ClassNotFoundException | SQLException e) {
        }
        assertNotNull("There is a problem with the database connection", sqlConnection);
    }
}
