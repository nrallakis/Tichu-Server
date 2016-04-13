package gr.nrallakis.tichu.server.database;

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
            else if (obj == (int)obj) {
                sqlBuilder.append((int) obj);
            }
            sqlBuilder.append(", ");
        }
        // Delete the last comma and whitespace appended
        sqlBuilder.deleteCharAt(sqlBuilder.length()-2);
        sqlBuilder.deleteCharAt(sqlBuilder.length()-1);
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

    public String getSQL() {
        String sql = sqlBuilder.toString();
        if (sql.endsWith(" ")) {
            sql = sql.substring(0, sql.length()-1);
        }
        return sql;
    }
}
