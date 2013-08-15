package edu.wayne.utils.commands;

import java.io.IOException;

import edu.wayne.utils.remote.RConnection;
import edu.wayne.utils.remote.RIdentity;
import edu.wayne.utils.remote.RNode;

public class TaskExecute extends RCommandImpl {
	
	/**
	 * TaskRunCommand Constructor.
	 * @param command the ssh command
	 * @param target the target node to excute the command.
	 */
	public TaskExecute(String command, RNode target) {
		super(command, target);
	}
	

	/** the default behavior is doing nothing. The subclass can override it by
	 * implementing its own logic.
	 */
	@Override
	public  boolean undo(){
		return true;
	}

	/**
	 * Execute a concrete command on target. 
	 * <p>
	 * command and target should not be null.
	 */
	@Override
	protected CommandMessage execute(String command, RNode target,
			CommandHandler handler) {
		return target.executeTask(command, handler);
	}


	/**
	 * the default behavior returns true. It depends on subclasses to decide
	 * the return result.
	 */
	@Override
	public boolean checkStdOutMsg(String msg) {
		return true;
	}

}
