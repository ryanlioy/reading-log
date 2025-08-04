package dev.ryanlioy.booklogger.meta;

import lombok.Data;

@Data
public class ErrorDto {
    public ErrorDto(String message) {
        this.message = message;
    }
    private String message;
}
