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
        String firstName= "Eric";
        String lastName = "Evans";
        //WHEN
        Author author = authorDao.getByName(firstName,lastName);
        //THEN
        assertThat(author).isNotNull();
        assertThat(author.getId()).isEqualTo(2);
    }
}