package io.springframework.sbjpa.jdbc.dao;

import io.springframework.sbjpa.jdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
//@ComponentScan(basePackages = {"io.springframework.sbjpa.jdbc.dao"}) // with maven is not working
// To handle the exception: No qualifying bean of type AuthorDao
@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoImplTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void testGetAuthorById() {
        //GIVEN

        //WHEN
        Author author = authorDao.getById(1L);
        //THEN
        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        //GIVEN
        String firstName = "Eric";
        String lastName = "Evans";
        //WHEN
        Author author = authorDao.getByName(firstName, lastName);
        //THEN
        assertThat(author).isNotNull();
        assertThat(author.getId()).isEqualTo(2);
    }

    @Test
    void testSaveAuthor() {
        //GIVEN
        String firstName = "Ismail";
        String lastName = "Echcherrate";
        Author newAuthor = new Author(firstName, lastName);
        //WHEN
        Author author = authorDao.saveNewAuthor(newAuthor);
        //THEN
        assertThat(author).isNotNull();
        assertThat(author.getFirstName()).isEqualTo(firstName);
    }

    @Test
    void testSaveUpdateAuthor() {
        //GIVEN
        Author author = new Author();
        author.setFirstName("JOHN");
        author.setLastName("D");
        Author saved = authorDao.saveNewAuthor(author);
        //WHEN
        saved.setLastName("Doe");
        Author updated = authorDao.updateAuthor(saved);
        //THEN
        assertThat(author).isNotNull();
        assertThat(updated.getLastName()).isEqualTo("Doe");
    }

    @Test
    void testSaveDeleteAuthor() {
        //GIVEN
        Author author = new Author();
        author.setFirstName("Jino");
        author.setLastName("T");

        Author saved= authorDao.saveNewAuthor(author);
        authorDao.deleteDeleteById(saved.getId());

        //WHEN
        Author deleted = authorDao.getById(saved.getId());
        //THEN
        assertThat(deleted).isNull();

    }

}