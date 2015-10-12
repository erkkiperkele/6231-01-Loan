package Services;

import Contracts.IFileLogger;
import Contracts.ILoggerService;
import Data.Customer;
import IO.FileLogger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoggerService implements ILoggerService, Closeable {
    //    private Map<Integer, IFileLogger> _loggers;
    private IFileLogger logger;
    private final String _rootPath = "./LogsServer/";

    public LoggerService() {

//        _loggers = new HashMap<>();
    }


    @Override
    public void close() throws IOException {
        logger.close();
    }

    @Override
    public IFileLogger getLogger() {

        return getLazyLogger();
    }

    private IFileLogger getLazyLogger() {


        if (this.logger == null) {
            this.logger = createLogger();
        }
        return this.logger;
    }

    private IFileLogger createLogger() {

        String serverName = SessionService.getInstance().getBank().name();
        String path =
                _rootPath
                        + serverName
                        + ".txt";

        createFile(path);

        IFileLogger newLogger = new FileLogger(path);

        return newLogger;
    }

    private void createFile(String path) {
        try {
            File f = new File(path);

            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
