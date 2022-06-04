package com.better.reads.booktracker.contoller;

import com.better.reads.booktracker.model.Book;
import com.better.reads.booktracker.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService booksService;
    @GetMapping("/test")
    public String test(){
        return "success";
    }
    @GetMapping("/book")
    private List<Book> getAllBooks()
    {
        return booksService.getAllBooks();
    }
    @GetMapping("/book/{bookid}")
    private Book getBooks(@PathVariable("bookid") int bookid)
    {
        return booksService.getBooksById(bookid);
    }
    @DeleteMapping("/book/{bookid}")
    private void deleteBook(@PathVariable("bookid") int bookid)
    {
        booksService.delete(bookid);
    }
    @PostMapping("/book")
    private long saveBook(@RequestBody Book books)
    {
        booksService.saveOrUpdate(books);
        return books.getBookid();
    }
    @PutMapping("/book")
    private Book update(@RequestBody Book books)
    {
        booksService.saveOrUpdate(books);
        return books;
    }
}
