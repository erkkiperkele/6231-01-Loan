package IO;

import Contracts.IFileLogger;

import java.io.*;
import java.util.*;

public class FileLogger implements IFileLogger {

    private FileOutputStream _logger;

    public FileLogger(String path) {

        try {
            _logger = new FileOutputStream(path, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        _logger.close();
    }

    @Override
    public void info(String message) {
        write(" INFO: " + message);
    }

    @Override
    public void warning(String message) {
        write(" WARN: " + message);
    }

    @Override
    public void error(String message) {
        write(" ERROR: " + message);
    }

    private void write(String message) {

        String outputMessage = new Date() + message + "\n";

        try {
            _logger.write(outputMessage.getBytes());
            System.out.println(outputMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
