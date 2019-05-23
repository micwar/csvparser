package com.mw.parser.service;

import com.mw.parser.database.model.FileRecord;
import com.mw.parser.dto.ObjectConverter;
import com.mw.parser.dto.Record;
import com.mw.parser.engine.FileParser;
import com.mw.parser.dto.ParseResult;
import com.mw.parser.exception.CSVParserException;
import com.mw.parser.repository.FileRecordRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ApiService {

    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

    private final FileRecordRepository fileRecordRepository;
    private final ObjectConverter objectConverter;

    public ApiService(FileRecordRepository fileRecordRepository, ObjectConverter objectConverter) {
        this.fileRecordRepository = fileRecordRepository;
        this.objectConverter = objectConverter;
    }

    public ParseResult parseAndUploadFile(MultipartFile file) {

        ParseResult parseResult = new ParseResult(Boolean.TRUE);

        List<FileRecord> lines = null;

        try {
            lines = FileParser.readCSVFile(FileRecord.class, file.getInputStream(), ',');
        } catch (IOException e) {
            logger.error("Corrupted file", e);
        } catch (CSVParserException e) {
            parseResult.setSuccess(Boolean.FALSE);
            parseResult.setMessage(e.getMessage());
        }

        if(CollectionUtils.isNotEmpty(lines)) {
            fileRecordRepository.saveAll(lines);
            logger.info("{} lines inserted/updated", lines.size());
        } else if(parseResult.getSuccess() != Boolean.FALSE) {
            parseResult.setSuccess(Boolean.FALSE);
            parseResult.setMessage("File is empty.");
        }

        return parseResult;
    }

    public Record getById(String id) {
        Optional<FileRecord> fileRecord = fileRecordRepository.findById(id);

        Record record = null;

        if(fileRecord.isPresent()) {
            record = objectConverter.toDTO(fileRecord.get());
        } else {
            logger.info("Record with id '{}' is not present.", id);
        }

        return record;
    }

    public Boolean deleteById(String id) {

        Boolean result = Boolean.TRUE;

        try {
            fileRecordRepository.deleteById(id);
            logger.info("Record with id '{}' successfully deleted.", id);
        } catch (EmptyResultDataAccessException | UnexpectedRollbackException ex) {
            logger.info("Record with id '{}' does not exist.", id);
            result = Boolean.FALSE;
        }

        return result;
    }
}
