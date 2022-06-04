package com.better.reads.booktracker.repository;

import com.better.reads.booktracker.model.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    public void init() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("test book1", "test author", 120L));
        books.add(new Book("test book2", "test author", 220L));
        books.add(new Book("test book3", "test author", 320L));

        bookRepository.saveAll(books);
    }

    @AfterEach
    public void destory() {
        bookRepository.deleteAll();
    }

    @Test
    public void test_save_book() {

        Book result = bookRepository.save(new Book("test book", "test author", 120L));
        Assertions.assertTrue(result.getBookid() > 0);
    }

    @Test
    public void test_get_book() {

        Book result = bookRepository.findByBookName("test book1");
        assert result != null;
        Assertions.assertEquals(result.getBookName(), "test book1");
        Assertions.assertEquals(result.getAuthor(), "test author");
        Assertions.assertEquals(result.getPrice(), 120L);

    }

    @Test
    public void test_update_book() {
        Book dbBook = bookRepository.findByBookName("test book1");
        dbBook.setPrice(500L);
        Book result = bookRepository.save(dbBook);
        Assertions.assertEquals(result.getPrice(), dbBook.getPrice());

    }

    @Test
    public void test_delete_book() {
        bookRepository.deleteById(1L);
        Assertions.assertFalse(bookRepository.findById(1L).isPresent());
    }
}
