package io.springframework.sbjpa.jdbc.dao;

import io.springframework.sbjpa.jdbc.domain.Author;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class AuthorDaoImpl implements AuthorDao {


    private final DataSource dataSource;

    public AuthorDaoImpl(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Override
    public Author getById(Long id) throws RuntimeException {

        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("SELECT * FROM author where id= ?");
            ps.setLong(1, id);
            resultSet = ps.executeQuery();


            if (resultSet.next()) {
                return getAuthorFromRS(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            closeDbResources(connection, ps, resultSet);

        }
        return null;
    }


    @Override
    public Author getByName(String firstName, String lastName) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("SELECT * FROM author  where first_name=? and last_name=?");
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return getAuthorFromRS(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeDbResources(connection, ps, resultSet);
        }
        return null;
    }

    private Author getAuthorFromRS(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(Long.valueOf(resultSet.getString("id")));
        author.setFirstName(resultSet.getString("first_name"));
        author.setFirstName(resultSet.getString("last_name"));

        return author;
    }

    private void closeDbResources(Connection connection, PreparedStatement ps, ResultSet resultSet) {
        try {
            //Release DB resources

            if (resultSet != null) {
                resultSet.close();
            }


            if (ps != null) {
                ps.close();
            }


            if (connection != null) {
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
