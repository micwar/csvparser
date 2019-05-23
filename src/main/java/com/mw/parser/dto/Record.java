package com.mw.parser.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class Record {

    private String id;
    private String name;
    private String description;
    private Date lastUpdateTime;

}
