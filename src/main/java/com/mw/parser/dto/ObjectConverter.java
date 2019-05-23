package com.mw.parser.dto;

import com.mw.parser.database.model.FileRecord;
import org.springframework.stereotype.Service;

@Service
public class ObjectConverter {

    public Record toDTO(FileRecord fileRecord) {
        return Record.builder()
                .id(fileRecord.getPrimaryKey())
                .name(fileRecord.getName())
                .description(fileRecord.getDescription())
                .lastUpdateTime(fileRecord.getUpdateTime())
                .build();
    }
}
