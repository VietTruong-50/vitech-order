package vn.vnpt.api.repository.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetParser<T> {
	T parseRs(ResultSet rs) throws SQLException;
}
