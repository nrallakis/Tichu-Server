package gr.nrallakis.tichu.server.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import gr.nrallakis.tichu.server.database.SQLBuilder;

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
        assertEquals("SELECT X", sql.getSQL());
    }

    @Test
    public void testInsertIntoX() {
        sql.insertInto("X");
        assertEquals("INSERT INTO X", sql.getSQL());
    }

    @Test
    public void testWhereXEqualY() {
        sql.where("X").equal("Y");
        assertEquals("WHERE X == 'Y'", sql.getSQL());
    }

    @Test
    public void testValuesAString5DString9() {
        sql.values("A", 5, "D", 9);
        assertEquals("VALUES('A', " + 5 + ", 'D', 9)", sql.getSQL());
    }

    @Test
    public void testGetSqlTrimsLastSpace() {
        sql.where("X");
        assertEquals("WHERE X", sql.getSQL());
    }

    @Test
    public void testSelectXFromX2WhereX3EqualSX4String() {
        sql.select("X").from("X2").where("X3").equal("X4");
        assertEquals("SELECT X FROM X2 WHERE X3 == 'X4'", sql.getSQL());
    }




}
