package edu.wayne.utils.commands;

import java.util.Arrays;

import edu.wayne.utils.remote.RNode;

/**
 * If a directory to be created exists, simply return true.
 * Otherwise, create that directory on remote node.
 * Add: 
 * (1) supports recursive directory creation. 
 * 	If some parts of the directory do not exist, create it using specified 
 * 	permission.
 * (2) supports recursive undo operation
 * @author Dajun
 *
 */
public class DirMake extends RCommandImpl{
	
	/** the directory wrapper object */
	private FilePath directory;
	
	/** used for undo operation.
	 * e.g. /a/b/c/ if b does not exist, then
	 * existLevel = 1, which means /, /a exists, but /a/b, /a/b/c don't.
	 */
	private int existLevel;

	/**
	 * Class Constructor, using default file permission "0644".
	 * @param directory
	 * @param target
	 */
	public DirMake(String directory, RNode target) {
		super("mkdir -p " + directory, target);
		this.directory = new FilePath(directory);
		existLevel = -1;
	}

	@Override
	protected CommandMessage execute(String command, RNode target,
			CommandHandler handler) {
		//calculate exist level
		for (int i = 0; i < this.directory.size(); i++) {
			if (new DirExist(this.directory.getSubPath(i), target).execute()) {
				existLevel = i;
			} 
		}
		
		if (this.existLevel == this.directory.size()) {
			CommandMessage msg = new CommandMessage();
			msg.setMsgStdOut("mkdir: do not create diretory `" 
					+ this.directory + "\': directory exists");
			return msg;
		}
		
		// create non-exist directories
		return target.executeTask(command, handler);
	}
	
	@Override
	public boolean undo() {
		for (int i = this.directory.size(); i > this.existLevel; i--) {
			DirDel dirDel = new DirDel(
					this.directory.getSubPath(i), this.target);
			boolean r = dirDel.execute();
			if (!r) return false;
		}
		return true;
	}
	
	
}
