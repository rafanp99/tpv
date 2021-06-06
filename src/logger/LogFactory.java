package logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Patron de diseño factory para el Logger
 * @author Rafael Niñoles Parra
 */
public class LogFactory {
	// Patrónn Factory Method

	/**
	 * Devuelve un Logger usando el patron factory
	 * @return El Logger
	 */
	public static Logger getLogger() {
		// Creamos el nuevo Logger con todos los niveles para consola y fichero
		Logger result = Logger.getLogger("logging.txt");

		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.ALL);

		Handler fileTxt = null;
		try {
			fileTxt = new FileHandler("logging.txt");

			SimpleFormatter simpleFormatter = new SimpleFormatter();
			fileTxt.setFormatter(simpleFormatter);
			fileTxt.setLevel(Level.INFO);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}

		result.addHandler(consoleHandler);
		result.addHandler(fileTxt);
		result.setLevel(Level.FINE);

		return result;
	}

}