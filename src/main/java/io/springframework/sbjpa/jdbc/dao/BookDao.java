package io.springframework.sbjpa.jdbc.dao;

import io.springframework.sbjpa.jdbc.domain.Book;

/**
 * @author admin
 * @Date 9/25/2022
 */
public interface BookDao {

    Book findById(Long id);

    Book findByTile(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);
}
