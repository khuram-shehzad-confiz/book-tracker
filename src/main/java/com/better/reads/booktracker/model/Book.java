package com.better.reads.booktracker.model;

import javax.persistence.*;

@Table
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long bookid;
    @Column
    private String bookName;
    @Column
    private String author;
    @Column
    private long price;

    public Book() {
    }

    public Book(long bookid, String bookName, String author, long price) {
        this.bookid = bookid;
        this.bookName = bookName;
        this.author = author;
        this.price = price;
    }

    public Book(String bookName, String author, long price) {
        this.bookName = bookName;
        this.author = author;
        this.price = price;
    }

    public long getBookid() {
        return bookid;
    }

    public void setBookid(long bookid) {
        this.bookid = bookid;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
