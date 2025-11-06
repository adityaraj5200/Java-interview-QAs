package com.aditya.onlinebooksystem.service;

import com.aditya.onlinebooksystem.dto.BookResponseDto;
import com.aditya.onlinebooksystem.dto.BookFilterRequestDto;

import java.util.List;

public interface BookService {

    /**
     * Get all books
     */
    List<BookResponseDto> getAllBooks();

    /**
     * Get a book by its ID
     */
    BookResponseDto getBookById(Long id);

    /**
     * Filter and search books based on pages, rating, or title
     */
    List<BookResponseDto> filterAndSearchBooks(BookFilterRequestDto filterRequest);
}
