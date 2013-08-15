import edu.wayne.utils.remote.RNode;

/**
 * Utilities for junit test cases.
 * @author Dajun
 *
 */
public class TestUtils {

	private static String username = "ei8449";
	private static String nodename = "energyintcit1";
	private static String password = "xxxxxxxxxxxx";
	private static String host = "141.217.48.208";
	private static int	port = 10001;
	
	public static RNode getTestNode() {
		RNode node = new RNode(nodename, host, port, username, password);
		return node;
	}
}

