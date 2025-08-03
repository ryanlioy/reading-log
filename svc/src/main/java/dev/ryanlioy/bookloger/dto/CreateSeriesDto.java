package dev.ryanlioy.bookloger.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateSeriesDto {
    private String title;
    private String description;
    private String author;
    private List<Long> books;
}
