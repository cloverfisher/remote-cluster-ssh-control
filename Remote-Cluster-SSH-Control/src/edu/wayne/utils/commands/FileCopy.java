package edu.wayne.utils.commands;

import edu.wayne.utils.remote.RNode;

public class FileCopy extends RCommandImpl{
	/** read src on remote node */
	private String src;
	/** store it to dest on local file system */
	private String destDir;
	
	public FileCopy(String src, String destDir, RNode target) {
		super(src, target);
		this.src = src;
		this.destDir = destDir;
	}

	/**
	 * copy src from target to dest.
	 * if either src or dest is null, simply return;
	 */
	@Override
	protected CommandMessage execute(String command, RNode target,
			CommandHandler handler) {
		if (this.src == null || this.destDir == null) {
			CommandMessage msg = new CommandMessage();
			msg.setMsgStdOut("FileRead: ignore with src = `" + src + "\'"
					+ ", and destDir = `" + destDir + "'");
			return msg;
		}
		return target.executeFileCopy(this.src, this.destDir, handler);
	}
	

}
