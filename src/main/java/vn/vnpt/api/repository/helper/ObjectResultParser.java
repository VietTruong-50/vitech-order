package vn.vnpt.api.repository.helper;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class ObjectResultParser {
    public static Object[] parse(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        Object[] object = new Object[columns];
        for (int i = 1; i <= columns; i++) {
            object[i-1] = rs.getObject(i);
        }

        return object;
    }
}
