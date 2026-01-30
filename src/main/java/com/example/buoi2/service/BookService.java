package com.example.buoi2.service;

import com.example.buoi2.exception.BookNotFoundException;
import com.example.buoi2.model.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public List<Book> getAllBooks() {
        return List.copyOf(books);
    }

    public Book getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book addBook(Book book) {
        long id = sequence.incrementAndGet();
        book.setId(id);
        books.add(book);
        return book;
    }

    public Book updateBook(Long id, Book updatedBook) {
        Book existing = books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id));

        existing.setCode(updatedBook.getCode());
        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        return existing;
    }

    public void deleteBook(Long id) {
        boolean removed = books.removeIf(book -> book.getId().equals(id));
        if (!removed) {
            throw new BookNotFoundException(id);
        }
    }
}
