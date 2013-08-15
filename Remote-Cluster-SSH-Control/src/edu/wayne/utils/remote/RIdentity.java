package edu.wayne.utils.remote;

/** username and password for a remote connection. */
public class RIdentity {

	private final String username;
	
	private final String password;
	
	public RIdentity(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
}
