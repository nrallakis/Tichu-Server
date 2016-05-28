package gr.nrallakis.tichu.server.database;

/** A simple to use String appender to make sql
 *  statements avoiding hard-coded Strings*/
public class SQLBuilder {

    private StringBuilder sqlBuilder;

    public SQLBuilder() {
        sqlBuilder = new StringBuilder();
    }

    public SQLBuilder select(String s) {
        sqlBuilder.append("SELECT ");
        sqlBuilder.append(s);
        sqlBuilder.append(" ");
        return this;
    }

    public SQLBuilder insertInto(String s) {
        sqlBuilder.append("INSERT INTO ");
        sqlBuilder.append(s);
        sqlBuilder.append(" ");
        return this;
    }

    public SQLBuilder values(Object...objects) {
        sqlBuilder.append("VALUES(");
        for (Object obj : objects) {
            if (obj instanceof String) {
                sqlBuilder.append("'");
                sqlBuilder.append(obj.toString());
                sqlBuilder.append("'");
            }
            else if (obj instanceof Integer) {
                sqlBuilder.append((int) obj);
            }
            sqlBuilder.append(", ");
        }
        // Delete the last comma and whitespace appended
        sqlBuilder.delete(sqlBuilder.length()-2, sqlBuilder.length());
        sqlBuilder.append(") ");
        return this;
    }

    public SQLBuilder from(String s) {
        sqlBuilder.append("FROM ");
        sqlBuilder.append(s);
        sqlBuilder.append(" ");
        return this;
    }

    public SQLBuilder where(String s) {
        sqlBuilder.append("WHERE ");
        sqlBuilder.append(s);
        sqlBuilder.append(" ");
        return this;
    }

    public SQLBuilder equal(String s) {
        sqlBuilder.append("== '");
        sqlBuilder.append(s);
        sqlBuilder.append("' ");
        return this;
    }

    /** Returns the created sql statement */
    public String build() {
        String sql = sqlBuilder.toString();
        if (sql.endsWith(" ")) {
            sql = sql.substring(0, sql.length()-1);
        }
        return sql;
    }
}
