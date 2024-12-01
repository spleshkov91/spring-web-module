package ru.edu.springweb.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.edu.springweb.entity.Book;
import ru.edu.springweb.service.BookService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(
                Arrays.asList(new Book(1, "Book1", "Author1"), new Book(2, "Book2", "Author2"))
        );

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Book1"));
    }

    @Test
    void testGetBookById() throws Exception {
        when(bookService.getBookById(1)).thenReturn(Optional.of(new Book(1, "Book1", "Author1")));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book1"));
    }

    @Test
    void testAddBook() throws Exception {
        Book book = new Book(0, "Book1", "Author1");
        when(bookService.addBook(any(Book.class))).thenReturn(new Book(1, "Book1", "Author1"));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Book1\",\"author\":\"Author1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateBook() throws Exception {
        when(bookService.updateBook(Mockito.eq(1), any(Book.class)))
                .thenReturn(Optional.of(new Book(1, "UpdatedTitle", "UpdatedAuthor")));

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"UpdatedTitle\",\"author\":\"UpdatedAuthor\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("UpdatedTitle"));
    }

    @Test
    void testDeleteBook() throws Exception {
        when(bookService.deleteBook(1)).thenReturn(true);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }
}