package edu.wayne.utils.remote;

import java.io.IOException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionMonitor;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;

public class RConnection {

	/** username and password */
	private RIdentity identity;
	
	/** ssh connection to the remote machine. */
	private Connection conn;
	
	/** ssh scp client to download and upload files */
	private SCPClient scpclnt;
	
	/** default connection time out 5 seconds. */
	private int timeout = 5000;
	
	/**
	 * Class Construction.
	 * @param identity including username and password
	 * @param host the remote address connected to
	 * @param port the port of the host to connect to
	 */
	public RConnection(RIdentity identity, String host, int port) {
		this.identity = identity;
		this.conn = new Connection(host, port);
	}
	
	/**
	 * Connect to the specified server.
	 * @throws IOException
	 */
	public void connect() throws IOException{
		// close previous connection, and any open session and SCP client.
		conn.close();
		scpclnt = null;
		this.conn.connect(null, timeout, timeout);
		if (!conn.authenticateWithPassword(this.identity.getUsername(), 
				this.identity.getPassword()))
			throw new IOException("Authentication to " + conn.getHostname() 
					+ ":" + conn.getPort() + " failed");
		scpclnt = conn.createSCPClient();
	}
	
	/** used for client to monitor the status of the underlying connection.
	 * It is running in a different thread. Be careful of synchronization.
	 * @param monitor implements ConnectionMonitor Interface
	 */
	public void addMonitor(ConnectionMonitor monitor) {
		conn.addConnectionMonitor(monitor);
	}
	
	/**
	 * 
	 * @return available session
	 * @throws IOException
	 */
	public Session getSession() throws IOException{
		// a RConnection may open multiple sessions, but only one
		// scp client.
		return conn.openSession();
	}
	
	/**
	 * put the session back, release the resources it owns.
	 * @param sess
	 */
	public void putSession(Session sess) {
		if (sess != null)
			sess.close();
	}
	
	/**
	 * @return available scp client
	 * @throws IOException
	 */
	public SCPClient getSCPClient() throws IOException {
		if (scpclnt == null) {
			scpclnt = conn.createSCPClient();
		}
		return scpclnt;
	}
	
	/**
	 * close connection
	 */
	public void close() {
		conn.close();
	}
	
}
