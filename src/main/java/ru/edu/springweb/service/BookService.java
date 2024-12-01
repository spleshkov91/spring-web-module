package ru.edu.springweb.service;

import org.springframework.stereotype.Service;
import ru.edu.springweb.entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();
    private int nextId = 1;

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst();
    }

    public Book addBook (Book book) {
        book.setId(nextId++);
        books.add(book);
        return book;
    }

    public Optional<Book> updateBook(int id, Book updateBook) {
        return getBookById(id).map(existingBook -> {
            existingBook.setTitle(updateBook.getTitle());
            existingBook.setAuthor(updateBook.getAuthor());
            return existingBook;
        });
    }

    public boolean deleteBook(int id) {
        return books.removeIf(book -> book.getId() == id);
    }
}
