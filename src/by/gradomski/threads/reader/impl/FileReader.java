package by.gradomski.threads.reader.impl;

import by.gradomski.threads.exception.FileReaderException;
import by.gradomski.threads.reader.Reader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader implements Reader {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String read(String fileName) throws FileReaderException{
        logger.info("parameter: String: " + fileName);
        Path filePath = Paths.get(fileName);
        long size;
        try{
            size = Files.size(filePath);
        } catch (IOException e){
            logger.error("File not found");
            throw new FileReaderException("file not found", e);
        }
        if (size == 0){
            logger.error("Empty source file");
            throw new FileReaderException("empty source file");
        }
        try (Stream<String> streamLines = Files.lines(filePath)){
            String resultString = streamLines.collect(Collectors.joining());
            logger.info("read from file: " + resultString);
            return resultString;
        } catch (IOException e){
            logger.fatal("IOException");
            throw new FileReaderException("IOException", e);
        }
    }
}
