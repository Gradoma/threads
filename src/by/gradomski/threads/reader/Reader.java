package by.gradomski.threads.reader;

import by.gradomski.threads.exception.FileReaderException;

public interface Reader {
    String read(String filename) throws FileReaderException;
}
