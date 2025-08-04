package dev.ryanlioy.booklogger.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateAuthorDto {
    private Long id;
    private Integer age;
    private String name;
    private List<Long> bookIds;
}
