package com.andima.billing.persistence.domain.fixture;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.hibernate.jdbc.Work;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static junit.framework.TestCase.fail;

public class JpaAssertion {
    public static void assertThatTableExiste(EntityManager manager, final String tableName) {
        SessionImpl session = (SessionImpl) manager.unwrap(Session.class);

        final ResultCollector rc = new ResultCollector();
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ResultSet tables = connection.getMetaData().getTables(null, null, "%", null);
                while (tables.next()) {
                    if (tables.getString(3).toUpperCase().equals(tableName.toUpperCase())) {
                        rc.found = true;
                    }
                }
                if (!rc.found) {
                    fail("Table not found in schema :  " + tableName);
                }
            }
        });
    }

    public static void assertThatTableHasColumn(EntityManager manager, final String tableName, final String columnName) {
        SessionImpl session = (SessionImpl) manager.unwrap(Session.class);

        final ResultCollector rc = new ResultCollector();

        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                if (isExistInUpperAndLowerCase(connection, tableName, columnName))
                    rc.found = true;
            }
        });

        if (!rc.found) {
            fail("Column [" + columnName + "] not found on table : " + tableName);
        }
    }

    private static boolean isExistInUpperAndLowerCase(Connection connection, String tableName, String columnName) throws SQLException {
        return isTableHasColumnInConnection(connection, tableName, columnName)
                || isTableHasColumnInConnection(connection, tableName.toLowerCase(), columnName)
                || isTableHasColumnInConnection(connection, tableName.toUpperCase(), columnName);
    }

    private static boolean isTableHasColumnInConnection(Connection connection, String tableName, String columnName) throws SQLException {
        boolean found = false;
        ResultSet columns = connection.getMetaData().getColumns(null, null, tableName, null);
        while (columns.next()) {
            if (columns.getString(4).toUpperCase().equals(columnName.toUpperCase())) {
                found = true;
            }
        }
        return found;
    }

    static class ResultCollector {
        public boolean found = false;
    }

}