package com.better.reads.booktracker.controller;

import com.better.reads.booktracker.model.Book;
import com.better.reads.booktracker.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookAppIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    public void init() {
        bookRepository.deleteAll();
    }

    @Test
    public void test_fetch_book_in_db_success() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(new Book( "test book1", "test author", 120L));
        books.add(new Book( "test book2", "test author", 220L));
        books.add(new Book( "test book3", "test author", 320L));
        bookRepository.saveAll(books);

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/book"));

        resultActions
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}, {}, {}]"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]bookid").value(books.get(0).getBookid()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]bookName").value(books.get(0).getBookName()));
    }

    @Test
    public void test_insert_book_into_db_success() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Book("test book", "test author", 120L)))
        );
        resultActions.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void test_update_book() throws Exception {
        Book  book = new Book("test book", "test author", 120L);
        bookRepository.save(book);

        //update book fields
        book.setBookName("test book update");
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book))
        );
        resultActions.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookName").value("test book update"));
    }

    @Test
    public void test_find_book_by_id() throws Exception {
        Book  book = new Book("test book", "test author", 120L);
        bookRepository.save(book);

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/book/{bookid}", book.getBookid())
                        .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));
    }

    @Test
    public void test_find_book_by_id_not_in_db() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/book/{bookid}", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void test_delete_book() throws Exception {
        Book  book = new Book("test book", "test author", 120L);
        bookRepository.save(book);

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/book/{bookid}", book.getBookid())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
