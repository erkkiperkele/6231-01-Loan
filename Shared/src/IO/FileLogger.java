package IO;

import Contracts.IFileLogger;
import Data.Customer;

import java.io.*;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

public class FileLogger implements IFileLogger, Closeable {

    private Customer _currentCustomer;
    private Dictionary<Integer, PrintWriter> _loggers;
    private String _rootPath = "./Logs/";



    public void setCurrentCustomer(Customer _currentCustomer) {
        this._currentCustomer = _currentCustomer;
    }

    public FileLogger(Customer currentCustomer) {
        _currentCustomer = currentCustomer;
    }

    @Override
    public void close() throws IOException {
        Enumeration<PrintWriter> loggers = _loggers.elements();
        while( loggers.hasMoreElements())
        {
            loggers.nextElement().close();
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

    private void log(String message)
    {
        PrintWriter logger = getLazyLogger(_currentCustomer);
        logger.println(new Date() + message + "/n");

    }

    private PrintWriter getLazyLogger(Customer currentCustomer) {

        PrintWriter logger = _loggers.get(currentCustomer.getId());

        if (logger == null) {
            String path = _rootPath + currentCustomer.getFirstName() + "_" + currentCustomer.getLastName() + ".txt";


            try {
                logger = new PrintWriter(path, "UTF-8");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            _loggers.put(currentCustomer.getId(), logger);
        }

        return logger;
    }
}
