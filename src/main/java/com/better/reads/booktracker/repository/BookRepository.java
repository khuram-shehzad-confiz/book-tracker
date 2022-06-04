package com.better.reads.booktracker.repository;

import com.better.reads.booktracker.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByBookName(String bookName);
}
