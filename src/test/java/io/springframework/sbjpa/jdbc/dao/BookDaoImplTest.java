package io.springframework.sbjpa.jdbc.dao;

import io.springframework.sbjpa.jdbc.domain.Author;
import io.springframework.sbjpa.jdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author E.I.
 * @Date 9/25/2022
 */

@ActiveProfiles("local")
@DataJpaTest
//@ComponentScan(basePackages = {"io.springframework.sbjpa.jdbc.dao"}) // with maven is not working
// To handle the exception: No qualifying bean of type AuthorDao
@Import({BookDaoImpl.class, AuthorDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookDaoImplTest {

    @Autowired
    BookDao bookDao;

    @Autowired
    AuthorDao authorDao;

    @Test
    void testGetBookById() {
        //GIVEN
        String bookTitle = "Spring in Action, 5th Edition";
        Long id = 1L;
        //WHEN
        Book book = bookDao.findById(id);
        //THEN
        assertThat(book.getTitle()).isEqualTo(bookTitle);
    }

    @Test
    void testFindByTitle() {
        //GIVEN
        String bookTitle = "Spring in Action, 5th Edition";
        String isbn = "978-1617294945";
        //WHEN
        Book book = bookDao.findByTile(bookTitle);
        //THEN
        assertThat(book).isNotNull();
        assertThat(book.getIsbn()).isEqualTo(isbn);
    }

    @Test
    void testSaveNewBook() {
        //GIVEN
        Book newBook = new Book();
        newBook.setTitle("Parallel transaction in SB");
        newBook.setIsbn("978-0321125320");
        Author author= new Author();
        author.setId(4L);
        newBook.setAuthor(author);
        newBook.setAuthor(author);
        newBook.setPublisher("Addison Wesley");

        //WHEN
        Book book = bookDao.saveNewBook(newBook);
        //THEN
        assertThat(book).isNotNull();
        assertThat(book.getAuthor().getId()).isEqualTo(4);
    }

    @Test
    void testUpdateBook() {
        //GIVEN
        Book newBook = new Book();
        newBook.setTitle("Multi-Threading processing for fun");
        newBook.setIsbn("978-0321125350");
        Author author= new Author();
        author.setId(4L);
        newBook.setAuthor(author);
        newBook.setPublisher("Echcherrate prod");

        //WHEN
        Book savedBook = bookDao.saveNewBook(newBook);
        savedBook.setPublisher("Ech-cherrate production");
        Book updated = bookDao.updateBook(savedBook);
        //THEN
        assertThat(updated).isNotNull();
        assertThat(updated.getPublisher()).isEqualTo("Ech-cherrate production");
    }

    @Test
    void testDeleteBookById() {
        //GIVEN
        Book newBook = new Book();
        newBook.setTitle("A book about DB migration");
        newBook.setIsbn("978-0321125365");
        newBook.setPublisher("Ech-cherrate production");

        //WHEN
        Book savedBook = bookDao.saveNewBook(newBook);
        bookDao.deleteBookById(savedBook.getId());

        Book deleted= bookDao.findById(savedBook.getId());
        //THEN
        assertThat(deleted).isNull();

    }
}