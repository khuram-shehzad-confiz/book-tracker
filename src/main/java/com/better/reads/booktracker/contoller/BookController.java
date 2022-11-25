package com.better.reads.booktracker.contoller;

import com.better.reads.booktracker.model.Book;
import com.better.reads.booktracker.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    BookService booksService;

    @GetMapping()
    public String tet(){
        return "Application running ......";
    }
    @GetMapping("/book")
    private ResponseEntity<List<Book>> getAllBooks() {
        try {
            return new ResponseEntity<>(booksService.getAllBooks(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/book/{bookid}")
    private ResponseEntity<Book> getBooks(@PathVariable("bookid") long bookid) {

        try {
            Optional<Book> optionalBook = booksService.getBooksById(bookid);
            return optionalBook.map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/book")
    private ResponseEntity<?> saveBook(@RequestBody Book books) {
        try {
            booksService.saveOrUpdate(books);
            return new ResponseEntity<>(books.getBookid(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/book")
    private ResponseEntity<?> update(@RequestBody Book books) {
        try {
            booksService.saveOrUpdate(books);
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/book/{bookid}")
    private ResponseEntity<?> deleteBook(@PathVariable("bookid") long bookid) {
        try {
            booksService.delete(bookid);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }


}
