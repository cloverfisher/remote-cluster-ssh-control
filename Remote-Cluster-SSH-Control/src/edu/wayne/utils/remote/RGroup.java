package edu.wayne.utils.remote;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * a group consists of some amount of
 * remote node.
 * @author Dajun
 *
 */
public class RGroup {
	
	/** the name of group. */
	private String name;
	
	/** hash to support search by the name of a RNode */
	private Map<String, RNode> nodes = null;
	
	/**
	 * Create a RGroup. If name is null, use default name "noname".
	 * @param name the name of a group
	 */
	public RGroup(String name) {
		if (name == null) this.name = "noname";
		this.name = name;
		nodes = new HashMap<String, RNode>();
	}
	
	/**
	 * get the group name. The name can never be null.
	 * @return the group name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * add a node to the group. It uses a hash map to store added nodes.
	 * Thus, previous node which has the same name of the added node will
	 * be replaced.
	 * @param node the node to be put into
	 */
	public void add(RNode node) {
		if (node != null) {
			nodes.put(node.getName(), node);
		}
	}
	/**
	 * get node by name
	 * @param nodeName
	 * @return
	 */
	public RNode get(String nodeName) {
		return nodes.get(nodeName);
	}
	
	/**
	 * remove specified nodeName.
	 * @param nodeName
	 * @return the node removed
	 */
	public RNode remove(String nodeName) {
		return nodes.remove(nodeName);
	}
	
	/**
	 * get all the nodes in the group
	 * @return all the nodes
	 */
	public Collection<RNode> getAll() {
		return nodes.values();
	}
	
}
