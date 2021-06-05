package logger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MiLogger {
    private final Logger logger;

    public MiLogger() {
        this.logger = Logger.getLogger(MiLogger.class.getName());
        logger.setLevel(Level.ALL);
    }
}
