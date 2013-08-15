package edu.wayne.utils.commands;

public class CommandMessage {

	/** message type */
	private int msgType = 0;
	
	/** bit mask for stdout message */
	public static final int CMD_MSG_STDOUT = 1;
	
	/** bit mask for stderr message */
	public static final int CMD_MSG_STDERR = 2;
	
	/** bit mask for exception message */
	public static final int CMD_MSG_EXCEPTION = 4;
	
	/** message received from ssh stdout */
	private String msgStdOut;
	
	/** message received from ssh stderr */
	private String msgStdErr;
	
	/** message received from any exception */
	private String msgException;
	
	/** the exit code */
	private int exitCode = 0;
	
	/** check message type, type can be any of these three or combined:
	 *  CommandMessage.CMD_MSG_STDOUT, 
	 *  CommandMessage.CMD_MSG_STDERR, 
	 *  CommandMessage.CMD_MSG_EXCEPTION, 
	 * @param type message type
	 * @return whether a such type of message exists.
	 */
	public boolean isMsgType(int type) {
		return ((msgType & type) != 0);
	}
	
	/** set stdout message. if message if null, it will unset the previous
	 *  stdout message.
	 * @param message
	 */
	public void setMsgStdOut(String message) {
		if (message != null) {
			this.msgType |= CommandMessage.CMD_MSG_STDOUT;
			this.msgStdOut = message;
		} else {
			this.msgType &= (~CommandMessage.CMD_MSG_STDOUT);
			this.msgStdOut = null;
		}
	}
	/** set stderr message. if message if null, it will unset the previous
	 *  stderr message.
	 * @param message
	 */
	public void setMsgStdErr(String message) {
		if (message != null) {
			this.msgType |= CommandMessage.CMD_MSG_STDERR;
			this.msgStdErr = message;
		} else {
			this.msgType &= (~CommandMessage.CMD_MSG_STDERR);
			this.msgStdErr = null;
		}
	}
	/** set exception message. if message if null, it will unset the previous
	 *  exception message.
	 * @param message
	 */
	public void setMsgException(String message) {
		if (message != null) {
			this.msgType |= CommandMessage.CMD_MSG_EXCEPTION;
			this.msgException = message;
		} else {
			this.msgType &= (~CommandMessage.CMD_MSG_EXCEPTION);
			this.msgException = null;
		}
	}
	/**
	 * set exit code
	 * @param code
	 */
	public void setExitCode(int code) {
		this.exitCode = code;
	}
	
	/**
	 * @return stdout message
	 */
	public String getMsgStdOut() {
		return this.msgStdOut;
	}
	/**
	 * @return stderr message
	 */
	public String getMsgStdErr() {
		return this.msgStdErr;
	}
	/**
	 * 
	 * @return exception message
	 */
	public String getMsgException() {
		return this.msgException;
	}
	
	/**
	 * 
	 * @return exit code
	 */
	public int getExitCode() {
		return this.exitCode;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("exit code: ");
		builder.append(this.exitCode);
		builder.append("\n");
		if (this.msgStdOut != null) {
			builder.append("message: \n");
			builder.append(this.msgStdOut);
			builder.append("\n");
		}
		if (this.msgStdErr != null) {
			builder.append("error: \n");
			builder.append(this.msgStdErr);
			builder.append("\n");
		}
		if (this.msgException != null) {
			builder.append("exception: \n");
			builder.append(this.msgException);
			builder.append("\n");
		}
		return builder.toString();
	}
}
