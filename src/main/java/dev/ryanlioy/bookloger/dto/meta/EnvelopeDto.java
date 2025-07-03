package dev.ryanlioy.bookloger.dto.meta;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnvelopeDto<T> {
    public EnvelopeDto(T content) {
        this.content = content;
    }

    private T content;
    private List<ErrorDto> errors;
}
