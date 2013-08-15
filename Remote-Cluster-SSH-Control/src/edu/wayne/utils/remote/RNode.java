package edu.wayne.utils.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import ch.ethz.ssh2.ConnectionMonitor;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import edu.wayne.utils.commands.CommandHandler;
import edu.wayne.utils.commands.CommandMessage;

/**
 * Remote Node
 * @author Dajun
 *
 */
public class RNode {
	/** the name of a remote node */
	private String name;
	
	/** the identity to the remote node. */
	private RConnection conn;
	
	/**
	 * Constructor
	 * @param name the node name
	 * @param conn the remote connection
	 */
	public RNode(String name, RConnection conn) {
		this.name = name;
		this.conn = conn;
	}
	
	/**
	 * Constructor
	 * @param name the node name
	 * @param host the ip address of the remote machine to be connected
	 * @param port the port to be connected
	 * @param username authentication user name
	 * @param password authentication password
	 */
	public RNode(String name, String host, int port, 
			String username, String password) {
		this(name, new RConnection(
				new RIdentity(username, password), host, port));
	}
	
	/**
	 * connect to remote server
	 * @throws IOException
	 */
	public void start() throws IOException{
		if (this.conn == null)
			throw new IOException("connection can't be null");
		conn.connect();
	}
	/**
	 * close the node and remote connections.
	 */
	public void stop() {
		if (conn != null)
			conn.close();
	}
	
	/**
	 * execute a task in the node. 
	 * @param command not null
	 * @param handler to handle ssh result stream
	 * @return a CommandMessage object showing the result
	 */
	public CommandMessage executeTask(String command, CommandHandler handler) {
		CommandMessage result = new CommandMessage();
		try {
			Session sess = conn.getSession();
			sess.execCommand(command);
			InputStream stdout = new StreamGobbler(sess.getStdout());
			InputStream stderr = new StreamGobbler(sess.getStderr());
			if (handler != null) {
				result = handler.handle(stdout, stderr);
			} else {
				// check stdout message
				result.setMsgStdOut(getMessage(stdout));
				// check stderr message
				result.setMsgStdErr(getMessage(stderr));
			}
			Integer status = sess.getExitStatus();
			result.setExitCode(status == null ? 0: status);
			conn.putSession(sess);
		} catch(IOException ioe) {
			result.setMsgException(ioe.getMessage());
		}
		return result;
	}
	
	/** command handler is ignored here, maybe used in the future version.*/
	public CommandMessage executeFileCopy(String remoteFile, 
			String localTargetDirectory, CommandHandler handler) {
		CommandMessage message = new CommandMessage();
		try {
			SCPClient client = conn.getSCPClient();
			client.get(remoteFile, localTargetDirectory);
			message.setMsgStdOut("FileRead: copied `" + remoteFile + "\' on "
					+ this.name + " to local directory `" 
					+ localTargetDirectory + "'");
		} catch(IOException ioe) {
			message.setMsgException(getExceptionCause(ioe));
		}
		return message;
	}
	
	private String getExceptionCause(Exception exception) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.getCause().printStackTrace(pw);
		return sw.toString();
	}
	/**
	 * get Message from input stream.
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private String getMessage(InputStream is) throws IOException {
		StringBuilder builder = new StringBuilder();
		BufferedReader br = new BufferedReader(
				new InputStreamReader(is));
		String line;
		while ((line = br.readLine()) != null) {
			builder.append(line);
			builder.append("\n");
		}
		if (builder.length() == 0)
			return null;
		return builder.toString();
	}
	
	/**
	 * @return the node name.
	 */
	public String getName() {
		return this.name;
	}
	
	/** used for client to monitor the status of the underlying connection.
	 * The monitor method is running in a different thread. Be careful of synchronization
	 * and blocking.
	 * @param monitor implements ConnectionMonitor Interface
	 */
	public void addMonitor(ConnectionMonitor monitor) {
		this.conn.addMonitor(monitor);
	}
}
