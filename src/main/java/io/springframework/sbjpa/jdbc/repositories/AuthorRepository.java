package io.springframework.sbjpa.jdbc.repositories;

import io.springframework.sbjpa.jdbc.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
