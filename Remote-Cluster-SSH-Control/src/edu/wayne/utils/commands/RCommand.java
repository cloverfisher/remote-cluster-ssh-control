package edu.wayne.utils.commands;

public interface RCommand {

	/**
	 * Execute a command. It will start a command, block and wait for result.
	 * Check the result, determine if a command has been executed successfully.
	 * Any message got from the command including stdout, stderr message, and
	 * exception will be put together returned to client by calling 
	 * showMessage(). If a command handler is defined, the client needs to
	 * determine if it was executed successfully. If execute() returns false,
	 * there are must be something wrong in execution; while if it returns
	 * true, the client needs to check it using CommandHandler.
	 * @return true if command executes successfully. otherwise, false;
	 */
	public boolean execute();
	
	/** set your own handler */
	public void setCommandHandler(CommandHandler handler);
	
	/** undo operation */
	public boolean undo();
	
	/** get command message */
	public CommandMessage getMessage();
}
