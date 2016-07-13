package gr.nrallakis.tichu.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gr.nrallakis.tichu.server.database.SQLBuilder;

import static org.junit.Assert.assertEquals;

public class TestSQLBuilder {

    SQLBuilder sql;

    @Before
    public void setUp() {
        sql = new SQLBuilder();
    }

    @After
    public void tearDown() {
        sql = null;
    }

    @Test
    public void testSelectX() {
        sql.select("X");
        assertEquals("SELECT X", sql.build());
    }

    @Test
    public void testInsertIntoX() {
        sql.insertInto("X");
        assertEquals("INSERT INTO X", sql.build());
    }

    @Test
    public void testWhereXEqualY() {
        sql.where("X").equal("Y");
        assertEquals("WHERE X == 'Y'", sql.build());
    }

    @Test
    public void testValuesAString5DString9() {
        sql.values("A", 5, "D", 9);
        assertEquals("VALUES('A', " + 5 + ", 'D', 9)", sql.build());
    }

    @Test
    public void testGetSqlTrimsLastSpace() {
        sql.where("X");
        assertEquals("WHERE X", sql.build());
    }

    @Test
    public void testSelectXFromX2WhereX3EqualSX4String() {
        sql.select("X").from("X2").where("X3").equal("X4");
        assertEquals("SELECT X FROM X2 WHERE X3 == 'X4'", sql.build());
    }

    @Test
    public void testBug1() {
        String sql = new SQLBuilder()
                .insertInto("ACCOUNTS")
                .values("Guest-569", "10.05.2016 at 06:55", 1000, "569", 1, 0)
                .build();
        assertEquals("INSERT INTO ACCOUNTS VALUES('Guest-569', '10.05.2016 at 06:55', 1000, '569', 1, 0)", sql);
    }


}
