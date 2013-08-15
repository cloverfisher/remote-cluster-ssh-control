package edu.wayne.utils.commands;

import edu.wayne.utils.remote.RNode;

public class RCommandImpl implements RCommand{

	/** ssh command string */
	protected String command;
	
	/** on which node this command will execute */
	protected RNode target;
	
	/** command handler */
	protected CommandHandler handler;
	
	/** message returned from executing the command */
	private CommandMessage message;
	
	/**
	 * TaskRunCommand Constructor.
	 * @param command the ssh command
	 * @param target the target node to excute the command.
	 */
	public RCommandImpl(String command, RNode target) {
		this.command = command;
		this.target = target;
		this.handler = null;
		this.message = null;
	}
	
	/**
	 * if no target or command set before, this method simply returns true;
	 */
	@Override
	public boolean execute(){
		if (this.command == null || this.target == null)
			return true;
		this.message = execute(this.command, this.target, 
				this.handler);
		if (message == null) return true;
		if (this.message.isMsgType(CommandMessage.CMD_MSG_STDERR)
				|| this.message.isMsgType(CommandMessage.CMD_MSG_EXCEPTION)
				|| this.message.getExitCode() != 0)
			return false;
		return checkStdOutMsg(this.message.getMsgStdOut());
	}
	
	/** execute command on target. command and target passed in is not null.
	 *  if handler is not null, forwarding result stream to handler.
	 *  @return a CommandMessage showing the command result.
	 * */
	protected CommandMessage execute(String command, RNode target, 
			CommandHandler handler) {
		return target.executeTask(command, handler);
	}

	/**
	 * check result by message. 
	 * @param msg
	 * @return if the message showing that the command is executed correctly.
	 */
	protected  boolean checkStdOutMsg(String msg) {
		return true;
	}
	
	@Override
	public void setCommandHandler(CommandHandler handler) {
		this.handler = handler;
	}

	/** the default behavior is doing nothing. The subclass can override it by
	 * implementing its own logic.
	 */
	@Override
	public  boolean undo(){
		return true;
	}

	/** this message can never be null. */
	@Override
	public CommandMessage getMessage() {
		return this.message;
	}

}
