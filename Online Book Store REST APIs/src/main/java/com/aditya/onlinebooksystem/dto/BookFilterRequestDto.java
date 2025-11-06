package com.aditya.onlinebooksystem.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookFilterRequestDto {

    @Min(1)
    private Integer minPages;

    @Min(1)
    private Integer maxPages;

    @Min(0)
    private Double rating;

    private String title; // partial match search
}
