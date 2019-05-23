package com.mw.parser.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ParseResult {

    @NonNull
    private Boolean success;
    private String message;

}
