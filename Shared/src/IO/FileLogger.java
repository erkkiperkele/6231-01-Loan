package IO;

import Contracts.IFileLogger;
import Data.*;
import java.io.*;
import java.util.*;

public class FileLogger implements IFileLogger, Closeable {

    private Customer _currentCustomer;
    private Map<Integer, FileOutputStream> _loggers;
    private String _rootPath = "./Logs/";


    public void setCurrentCustomer(Customer _currentCustomer) {
        this._currentCustomer = _currentCustomer;
    }

    public FileLogger() {
        _currentCustomer = new Customer(0, "default", "logger", Bank.Invalid);
        _loggers = new HashMap<Integer, FileOutputStream>();
    }

    public FileLogger(Customer currentCustomer) {
        _currentCustomer = currentCustomer;
    }

    @Override
    public void close() throws IOException {
        for (FileOutputStream logger : _loggers.values()) {
            logger.close();
        }
    }

    @Override
    public void info(String message) {
        log(" INFO: " + message);
    }

    @Override
    public void warning(String message) {
        log(" WARN: " + message);
    }

    @Override
    public void error(String message) {
        log(" ERROR: " + message);
    }

    private void log(String message) {

        FileOutputStream logger = getLazyLogger(_currentCustomer);
        String outputMessage = new Date() + message + "\n";

        try {
            logger.write(outputMessage.getBytes());
            System.out.println(outputMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileOutputStream getLazyLogger(Customer currentCustomer) {

        FileOutputStream logger = _loggers.get(currentCustomer.getId());

        if (logger == null) {
            String path =
                    _rootPath
                            + currentCustomer.getFirstName()
                            + "_"
                            + currentCustomer.getLastName()
                            + "_"
                            + currentCustomer.getBank()
                            + ".txt";
            try {
                File f = new File(path);
                f.getParentFile().mkdirs();
                f.createNewFile();

                FileOutputStream myLogger = new FileOutputStream(path);

                _loggers.put(currentCustomer.getId(), myLogger);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return _loggers.get(currentCustomer.getId());
    }
}
