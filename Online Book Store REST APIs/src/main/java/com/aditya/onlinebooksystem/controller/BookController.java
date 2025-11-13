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
<<<<<<< HEAD
        return books.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(books);
    }


=======
        return ResponseEntity.ok(books);
    }

>>>>>>> 3562bd1d8d57c6d84bbfddbdcfd4eca6ada9e9b6
    /**
     * Get a book by its ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        BookResponseDto book = bookService.getBookById(id);
<<<<<<< HEAD
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
=======
        return ResponseEntity.ok(book);
>>>>>>> 3562bd1d8d57c6d84bbfddbdcfd4eca6ada9e9b6
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
<<<<<<< HEAD
    public ResponseEntity<List<BookResponseDto>> searchBooks(@Valid BookFilterRequestDto filterRequest) {
        List<BookResponseDto> books = bookService.filterAndSearchBooks(filterRequest);
        return books.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(books);
=======
    public ResponseEntity<List<BookResponseDto>> searchBooks(
            @Valid BookFilterRequestDto filterRequest
    ) {
        List<BookResponseDto> books = bookService.filterAndSearchBooks(filterRequest);
        return ResponseEntity.ok(books);
>>>>>>> 3562bd1d8d57c6d84bbfddbdcfd4eca6ada9e9b6
    }
}
