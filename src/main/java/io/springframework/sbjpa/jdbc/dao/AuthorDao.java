package io.springframework.sbjpa.jdbc.dao;

import io.springframework.sbjpa.jdbc.domain.Author;

public interface AuthorDao {

    Author getById(Long id);
    Author getByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);
}
