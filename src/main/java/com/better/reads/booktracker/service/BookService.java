package com.better.reads.booktracker.service;

import com.better.reads.booktracker.repository.BookRepository;
import com.better.reads.booktracker.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public List<Book> getAllBooks()
    {
        List<Book> books = new ArrayList<>(bookRepository.findAll());
        return books;
    }
    public Optional<Book> getBooksById(long id)
    {
        return bookRepository.findById(id);
    }
    public void saveOrUpdate(Book books)
    {
        bookRepository.save(books);
    }
    public void delete(long id)
    {
        bookRepository.deleteById(id);
    }
}
