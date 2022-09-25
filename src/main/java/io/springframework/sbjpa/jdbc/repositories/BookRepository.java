package io.springframework.sbjpa.jdbc.repositories;

import io.springframework.sbjpa.jdbc.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
