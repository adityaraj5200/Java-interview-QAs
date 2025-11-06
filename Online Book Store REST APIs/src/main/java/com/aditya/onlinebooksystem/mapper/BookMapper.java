package com.aditya.onlinebooksystem.mapper;

import com.aditya.onlinebooksystem.dto.BookResponseDto;
import com.aditya.onlinebooksystem.model.Book;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Convert Book entity to BookResponseDto
     */
    public BookResponseDto toDto(Book book) {
        return modelMapper.map(book, BookResponseDto.class);
    }

    /**
     * Convert BookResponseDto to Book entity
     * (optional, in case you implement create/update APIs later)
     */
    public Book toEntity(BookResponseDto dto) {
        return modelMapper.map(dto, Book.class);
    }
}
