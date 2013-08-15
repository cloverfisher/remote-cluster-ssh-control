package edu.wayne.utils.commands;

import edu.wayne.utils.remote.RNode;

/**
 * If a directory to be created exists, simply return true.
 * Otherwise, create that directory on remote node.
 * @author Dajun
 *
 */
public class DirMake extends RCommandImpl{
	
	private String directory;
	
	private boolean existBefore = false;

	public DirMake(String directory, RNode target) {
		super("mkdir " + directory, target);
		this.directory = directory;
	}

	@Override
	protected CommandMessage execute(String command, RNode target,
			CommandHandler handler) {
		//check if directory exists
		existBefore = new DirExist(this.directory, target).execute();
		if (existBefore) {
			CommandMessage msg = new CommandMessage();
			msg.setMsgStdOut("mkdir: do not create diretory `" 
					+ this.directory + "\': file exists");
			return msg;
		}
		return target.executeTask(command, handler);
	}
	
	@Override
	public boolean undo() {
		if (!existBefore) {
			//delete that directory
			DirDel dirDel = new DirDel(this.directory, this.target);
			return dirDel.execute();
		}
		return true;
	}

}
