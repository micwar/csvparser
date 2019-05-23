package com.mw.parser.exception;

public class CSVParserException extends Exception {

    public CSVParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public CSVParserException(String message) {
        super(message);
    }
}
