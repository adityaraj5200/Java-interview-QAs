package com.aditya.onlinebooksystem.controller;

import com.aditya.onlinebooksystem.dto.BookResponseDto;
import com.aditya.onlinebooksystem.dto.BookFilterRequestDto;
import com.aditya.onlinebooksystem.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * Get all books
     */
    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        List<BookResponseDto> books = bookService.getAllBooks();
        return books.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(books);
    }


    /**
     * Get a book by its ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        BookResponseDto book = bookService.getBookById(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    /**
     * Filter books based on pages, rating, or search by title
     * Accepts optional query params:
     * - minPages
     * - maxPages
     * - rating
     * - title (for partial match)
     */
    @GetMapping("/search")
    public ResponseEntity<List<BookResponseDto>> searchBooks(@Valid BookFilterRequestDto filterRequest) {
        List<BookResponseDto> books = bookService.filterAndSearchBooks(filterRequest);
        return books.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(books);
    }
}
