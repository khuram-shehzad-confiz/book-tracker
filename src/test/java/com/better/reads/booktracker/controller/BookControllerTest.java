package com.better.reads.booktracker.controller;

import com.better.reads.booktracker.model.Book;
import com.better.reads.booktracker.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
    @Autowired
    private ObjectMapper objectMapper;

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
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}, {}, {}]"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]bookid").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]bookName").value("test book1"));
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
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Book(1, "test book update", "test author", 120L)))
        );
        resultActions.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookName").value("test book update"));
    }

    @Test
    public void test_find_book_by_id() throws Exception {
        Mockito.when(bookService.getBooksById(Mockito.anyLong()))
                .thenReturn(java.util.Optional.of(new Book(1, "test book", "testAuthor", 120L)));
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/book/{bookid}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookid").value(1));
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
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/book/{bookid}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
