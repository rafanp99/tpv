package logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Clase destinada a la obtencion y creacion del logger
 * @author Rafael Niñoles Parra
 */
public class TPVLogger {

	private static Logger logger = null;
	private final static String NOMBRE_FICHERO = "logging.txt";
	// Patron Factory Method
	public static Logger getLogger() {
		if (logger!=null){
			return logger;
		}
		// Eliminamos la configuraci�n del log ra�z
		Logger rootLogger = Logger.getLogger("");
		Handler[] handlers = rootLogger.getHandlers();
		if (handlers.length>0 && handlers[0] instanceof ConsoleHandler) {
			rootLogger.removeHandler(handlers[0]);
		}

		// Creamos el nuevo Logger con todos los niveles para consola y fichero
		Logger result = Logger.getLogger(NOMBRE_FICHERO);

		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.ALL);

		Handler fileTxt = null;
		try {
			fileTxt = new FileHandler(NOMBRE_FICHERO,true);
			SimpleFormatter simpleFormatter = new SimpleFormatter();
			fileTxt.setFormatter(simpleFormatter);
			fileTxt.setLevel(Level.INFO);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}

		result.addHandler(consoleHandler);
		result.addHandler(fileTxt);
		result.setLevel(Level.FINE);

		logger = result;
		return result;
	}

}