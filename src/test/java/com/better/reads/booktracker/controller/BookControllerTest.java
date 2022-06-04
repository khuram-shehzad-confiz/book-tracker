package com.better.reads.booktracker.controller;

import com.better.reads.booktracker.model.Book;
import com.better.reads.booktracker.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @Test
    public void test_fetch_book_in_db_success() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "test book1", "test author", 120L));
        books.add(new Book(2, "test book2", "test author", 220L));
        books.add(new Book(3, "test book3", "test author", 320L));
        Mockito.when(bookService.getAllBooks()).thenReturn(books);
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/book"));

        resultActions
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json("[{}, {}, {}]"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]bookid").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]bookName").value("test book1"));


    }

}
