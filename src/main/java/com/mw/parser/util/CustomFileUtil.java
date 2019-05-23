package com.mw.parser.util;

import org.apache.commons.io.IOUtils;

import java.io.*;

public abstract class CustomFileUtil {

    public static final String PREFIX = "stream2file";
    public static final String SUFFIX = ".tmp";

    public static File stream2file(InputStream in) throws IOException {
        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }

    public static boolean endsWithEmptyLine(File file) throws IOException{
        try(RandomAccessFile fileHandler = new RandomAccessFile( file, "r" )) {
            fileHandler.seek(fileHandler.length() - 1);
            int readByte = fileHandler.readByte();

            return readByte == 0xA;
        }
    }
}
