package Contracts;

public interface IFileLogger {
    void info(String message);
    void warning(String message);
    void error(String message);
}
