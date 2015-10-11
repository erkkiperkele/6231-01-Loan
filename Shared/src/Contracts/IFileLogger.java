package Contracts;

import java.io.Closeable;

/**
 * A simple File logger interface.
 */
public interface IFileLogger extends Closeable {
    void info(String message);
    void warning(String message);
    void error(String message);
}
