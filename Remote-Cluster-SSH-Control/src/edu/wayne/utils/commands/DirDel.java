package edu.wayne.utils.commands;

import edu.wayne.utils.remote.RNode;

public class DirDel extends RCommandImpl{

	public DirDel(String directory, RNode target) {
		super("rmdir " + directory, target);
		this.directory = directory;
	}

	private String directory;
	
	private boolean dirExistBefore = false;
	
	
	@Override
	protected CommandMessage execute(String command, RNode target,
			CommandHandler handler) {
		//check if directory exists
		dirExistBefore = new DirExist(this.directory, target).execute();
		if (!dirExistBefore) {
			CommandMessage msg = new CommandMessage();
			msg.setMsgStdOut("rmdir: do not delete `" 
					+ this.directory + "\': File does not exists");
			return msg;
		}
		return target.executeTask(command, handler);
	}
	
	@Override
	public boolean undo() {
		if (dirExistBefore) {
			//create that directory
			DirMake dirMake = new DirMake(this.directory, this.target);
			return dirMake.execute();
		}
		return true;
	}
}
