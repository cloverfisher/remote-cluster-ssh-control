package edu.wayne.utils.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * a class to parse a file path.
 * @author Dajun
 *
 */
public class FilePath {

	private List<String> list;
	
	private String origin;
	
	
	public FilePath(String path) {
		this.origin = path;
		this.list = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(path, "/");
		/* record root as part of directory */
		if (path.startsWith("/"))
			this.list.add("/");
		while (tokenizer.hasMoreTokens()) {
			this.list.add(tokenizer.nextToken());
		}
	}
	
	public String[] getArray() {
		String[] result = new String[this.list.size()];
		return this.list.toArray(result);
	}
	
	public int size() {
		return list.size();
	}
	
	/**
	 * get sub path. For example if path = "/a/b/c", then
	 * getSubPath(0) = "/", getSubPath(1)= "/a", getSubPath(2) = "/a/b"
	 * getSubPath(3) = "/a/b/c", getSubPath(4) = "/a/b/c".
	 * @param level
	 * @return
	 */
	public String getSubPath(int level) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i <= Math.min(level, this.list.size()-1); i++) {
			builder.append(this.list.get(i));
			builder.append("/");
		}
		// remove last '/'
		if (this.list.get(0) == "/")
			return builder.substring(1, builder.length()-1);
		return builder.substring(0, builder.length()-1);
	}
	
	public String toString() {
		return this.origin;
	}
	
	public static void main(String[] args) {
		FilePath path = new FilePath("/b/c/");
		String[] dir = path.getArray();
		for (String str : dir) {
			System.out.println(str);
		}
		for (int i = 0; i < path.size(); i++)
			System.out.println(path.getSubPath(i));
	}
}
