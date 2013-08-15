package edu.wayne.utils.commands;

import edu.wayne.utils.remote.RNode;

public class DirExist extends RCommandImpl{
	
	public DirExist(String directory, RNode target) {
		super("test -d " + directory, target);
	}

}
