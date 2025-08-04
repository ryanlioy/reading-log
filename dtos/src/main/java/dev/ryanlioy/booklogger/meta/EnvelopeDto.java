package dev.ryanlioy.booklogger.meta;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnvelopeDto<T> {
    public EnvelopeDto() {}

    public EnvelopeDto(T content) {
        this.content = content;
    }

    public EnvelopeDto(List<ErrorDto> errors) {
        this.errors = errors;
    }

    private T content;
    private List<ErrorDto> errors;
}
