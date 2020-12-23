package server.control;

import java.time.LocalDateTime;;

public class LogManager {

	/**
	 * Prints a message with the structure "Date : message" in
	 * the log console
	 * 
	 * @param message The message to print
	 */
	public static void writeLog(String message)
	{
		System.out.println("<" + LocalDateTime.now() + "> : " + message);
	}
}
