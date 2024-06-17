package persistence;

import java.sql.SQLException;

public interface IConnectionDB<T> {
    public T open() throws SQLException;

    public void close();
}
