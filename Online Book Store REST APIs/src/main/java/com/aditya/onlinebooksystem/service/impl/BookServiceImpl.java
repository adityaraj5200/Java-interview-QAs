package com.aditya.onlinebooksystem.service.impl;

import com.aditya.onlinebooksystem.dto.BookResponseDto;
import com.aditya.onlinebooksystem.dto.BookFilterRequestDto;
import com.aditya.onlinebooksystem.exception.BookNotFoundException;
import com.aditya.onlinebooksystem.model.Book;
import com.aditya.onlinebooksystem.repository.BookRepository;
import com.aditya.onlinebooksystem.service.BookService;
import com.aditya.onlinebooksystem.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookResponseDto> filterAndSearchBooks(BookFilterRequestDto filterRequest) {
        // Fetch all books first (for simplicity, can optimize with JPA Specifications later)
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .filter(book -> filterRequest.getMinPages() == null || book.getPages() >= filterRequest.getMinPages())
                .filter(book -> filterRequest.getMaxPages() == null || book.getPages() <= filterRequest.getMaxPages())
                .filter(book -> filterRequest.getRating() == null || book.getRating() >= filterRequest.getRating())
                .filter(book -> !StringUtils.hasText(filterRequest.getTitle()) ||
                        book.getTitle().toLowerCase().contains(filterRequest.getTitle().toLowerCase()))
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
}
