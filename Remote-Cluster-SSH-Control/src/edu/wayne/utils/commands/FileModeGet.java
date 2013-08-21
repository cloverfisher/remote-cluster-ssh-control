package edu.wayne.utils.commands;

import edu.wayne.utils.remote.RNode;

public class FileModeGet extends RCommandImpl {
	
	public FileModeGet(String file, RNode target) {
		super("stat -c %a " + file, target);
	}
}
