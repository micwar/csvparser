package com.mw.parser.controller;

import com.mw.parser.dto.ParseResult;
import com.mw.parser.dto.Record;
import com.mw.parser.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/")
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping(value = "/uploadFile")
    @ResponseBody
    public ResponseEntity<ParseResult> uploadFile(@RequestParam("file") MultipartFile file) {
        logger.info("Received file: {}", file.getOriginalFilename());
        return new ResponseEntity<>(apiService.parseAndUploadFile(file), HttpStatus.OK);
    }

    @GetMapping(value = "/getById/{id}")
    @ResponseBody
    public ResponseEntity<Record> getById(@PathVariable("id") String id) {
        logger.info("Retrieving record with id: {}", id);
        return new ResponseEntity<>(apiService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteById/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") String id) {
        logger.info("Removing record with id: {}", id);
        return new ResponseEntity<>(apiService.deleteById(id), HttpStatus.OK);
    }

}
