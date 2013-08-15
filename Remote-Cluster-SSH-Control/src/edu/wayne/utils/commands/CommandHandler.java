package edu.wayne.utils.commands;

import java.io.IOException;
import java.io.InputStream;

public interface CommandHandler {
	/**
	 * If you need your own way to handle command execution result, define
	 * this CommandHandler.
	 * @param stdout  the stdout message stream get from a remote node
	 * @param stderr the stderr message stream get from a remote node
	 * @return A CommandMessage object
	 * @throws IOException
	 */
	public CommandMessage handle(InputStream stdout, InputStream stderr ) throws IOException;
	
}

