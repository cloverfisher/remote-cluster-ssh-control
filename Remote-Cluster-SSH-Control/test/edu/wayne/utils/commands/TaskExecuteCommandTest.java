package edu.wayne.utils.commands;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;



import edu.wayne.utils.remote.RNode;


public class TaskExecuteCommandTest {

	private static RNode node;
	
	@BeforeClass
	public static void setup() {
		node = TestUtils.getTestNode();
		try {
			node.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void cleanup() {
		if (node != null)
			node.stop();
	}
	
	
	@Test
	public void testWithoutHandler() {
		TaskExecute task = new TaskExecute("/hadoop/software/hadoop-1.0.4/bin/start-all.sh", node);
		boolean succeed = task.execute();
		assertTrue(succeed);
		CommandMessage message = task.getMessage();
		assertEquals(message.getExitCode(), 0);
		assertNull(message.getMsgStdErr());
		assertNull(message.getMsgException());
		assertNotNull(message.getMsgStdOut());
	}
	
	@Test
	public void testCustomerHandler() {
		
		// define customized  command handler
		CommandHandler handler = new CommandHandler(){
			private String getMessage(InputStream is) throws IOException {
				StringBuilder builder = new StringBuilder();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				String line;
				while ((line = br.readLine()) != null) {
					builder.append(line);
					builder.append("\n");
					System.out.println(line);
				}
				if (builder.length() == 0)
					return null;
				return builder.toString();
			}
			
			@Override
			public CommandMessage handle(InputStream std, InputStream serr)
					throws IOException {
				CommandMessage message = new CommandMessage();
				message.setMsgStdOut(getMessage(std));
				message.setMsgStdErr(getMessage(serr));
				return message;
			}
		};
		
		TaskExecute task = new TaskExecute("/hadoop/software/hadoop-1.0.4/bin/stop-all.sh", node);
		task.setCommandHandler(handler);	// set handler here
		
		boolean succeed = task.execute();
		assertTrue(succeed);
		CommandMessage message = task.getMessage();
		assertEquals(message.getExitCode(), 0);
		assertNull(message.getMsgStdErr());
		assertNull(message.getMsgException());
		assertNotNull(message.getMsgStdOut());
	}

}
