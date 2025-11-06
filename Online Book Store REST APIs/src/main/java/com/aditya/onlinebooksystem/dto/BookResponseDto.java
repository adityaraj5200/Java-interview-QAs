package com.aditya.onlinebooksystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto {

    private Long id;
    private String title;
    private String author;
    private Integer pages;
    private Double rating;
    private String description;
}
