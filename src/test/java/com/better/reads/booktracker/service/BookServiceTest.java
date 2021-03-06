package com.better.reads.booktracker.service;

import com.better.reads.booktracker.model.Book;
import com.better.reads.booktracker.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;


    @Test
    public void test_GetAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "test book1", "test author", 120L));
        books.add(new Book(2, "test book2", "test author", 220L));
        books.add(new Book(3, "test book3", "test author", 320L));

        Mockito.when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();
        Assertions.assertEquals(result.size(), books.size());

    }

    @Test
    public void test_findBookById() {
        Book sampleBook = new Book(1, "test book1", "test author", 120L);
        Mockito.when(bookRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(sampleBook));
        Optional<Book> result = bookService.getBooksById(1L);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(result.get().getBookid(), sampleBook.getBookid());
        Assertions.assertEquals(result.get().getBookName(), sampleBook.getBookName());
        Assertions.assertEquals(result.get().getAuthor(), sampleBook.getAuthor());
        Assertions.assertEquals(result.get().getPrice(), sampleBook.getPrice());
    }

    @Test
    public void test_saveOrUpdate() {
        Book sampleBook = new Book(1, "test book1", "test author", 120L);
        bookService.saveOrUpdate(sampleBook);
        Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any(Book.class));
    }

    @Test
    public void test_delete() {
        bookService.delete(1L);
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

}
