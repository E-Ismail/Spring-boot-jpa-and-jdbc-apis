package io.springframework.sbjpa.jdbc.dao;

import io.springframework.sbjpa.jdbc.domain.Book;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @author admin
 * @Date 9/25/2022
 */

@Component
public class BookDaoImpl implements BookDao {

    private final DataSource dataSource;

    public BookDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Book findById(Long id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("SELECT * FROM Book where id= ?");
            ps.setLong(1, id);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return getBookFromRS(resultSet);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            closeDbResources(connection, ps, resultSet);
        }
        return null;
    }

    @Override
    public Book findByTile(String title) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("SELECT * FROM Book where title= ?");
            ps.setString(1, title);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return getBookFromRS(resultSet);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            closeDbResources(connection, ps, resultSet);
        }
        return null;

    }

    @Override
    public Book saveNewBook(Book book) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Statement statement = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("INSERT INTO book(isbn, publisher, title, author_id) VALUE (?,?,?,?)");
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getPublisher());
            ps.setString(3, book.getTitle());
            ps.setLong(4, book.getAuthorId());
            ps.execute();

            statement = connection.createStatement();
            //Works only with MySQL
            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");


            if (resultSet.next()) {
                Long saveId = resultSet.getLong(1);
                return this.findById(saveId);
            }
            statement.close();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            closeDbResources(connection, ps, resultSet);
        }
        return null;
    }

    @Override
    public Book updateBook(Book book) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("UPDATE book SET isbn=?, title=?, publisher=?, bookdb2.book.author_id=?  where bookdb2.book.title =? and bookdb2.book.isbn=? ");
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getPublisher());
            ps.setLong(4, book.getAuthorId());
            ps.setString(5, book.getTitle());
            ps.setString(6, book.getIsbn());
            ps.execute();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            closeDbResources(connection, ps, null);
        }
        return this.findById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("DELETE FROM bookdb2.book where id =?");
            ps.setLong(1, id);
            ps.execute();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            closeDbResources(connection, ps, null);
        }
    }

    private Book getBookFromRS(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(Long.valueOf(resultSet.getString("id")));
        book.setTitle(resultSet.getString("title"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setPublisher(resultSet.getString("publisher"));
        book.setAuthorId(Long.valueOf(resultSet.getString("author_id")));
        return book;
    }

    private void closeDbResources(Connection connection, PreparedStatement ps, ResultSet resultSet) {
        try {
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
