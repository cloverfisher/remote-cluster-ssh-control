package edu.wayne.utils.commands;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.wayne.utils.commands.CommandMessage;

public class CommandMessageTest {

	@Test
	public void testIsMsgType() {
		CommandMessage msg = new CommandMessage();
		assertEquals(msg.getMsgStdOut(), null);
		assertEquals(msg.getMsgStdErr(), null);
		assertEquals(msg.getMsgException(), null);
		assertTrue(!msg.isMsgType(CommandMessage.CMD_MSG_STDOUT));
		assertTrue(!msg.isMsgType(CommandMessage.CMD_MSG_STDERR));
		assertTrue(!msg.isMsgType(CommandMessage.CMD_MSG_EXCEPTION));
		
		msg.setMsgStdOut("message stdout");
		assertEquals(msg.getMsgStdOut(), "message stdout");
		assertEquals(msg.getMsgStdErr(), null);
		assertEquals(msg.getMsgException(), null);
		assertTrue(msg.isMsgType(CommandMessage.CMD_MSG_STDOUT));
		assertTrue(!msg.isMsgType(CommandMessage.CMD_MSG_STDERR));
		assertTrue(!msg.isMsgType(CommandMessage.CMD_MSG_EXCEPTION));
		
		
		msg.setMsgStdErr("message stderr");
		assertEquals(msg.getMsgStdOut(), "message stdout");
		assertEquals(msg.getMsgStdErr(), "message stderr");
		assertEquals(msg.getMsgException(), null);
		assertTrue(msg.isMsgType(CommandMessage.CMD_MSG_STDOUT));
		assertTrue(msg.isMsgType(CommandMessage.CMD_MSG_STDERR));
		assertTrue(!msg.isMsgType(CommandMessage.CMD_MSG_EXCEPTION));
		
		msg.setMsgException("message exception");
		assertEquals(msg.getMsgStdOut(), "message stdout");
		assertEquals(msg.getMsgStdErr(), "message stderr");
		assertEquals(msg.getMsgException(), "message exception");
		assertTrue(msg.isMsgType(CommandMessage.CMD_MSG_STDOUT));
		assertTrue(msg.isMsgType(CommandMessage.CMD_MSG_STDERR));
		assertTrue(msg.isMsgType(CommandMessage.CMD_MSG_EXCEPTION));
		
		msg.setMsgException(null);
		assertEquals(msg.getMsgStdOut(), "message stdout");
		assertEquals(msg.getMsgStdErr(), "message stderr");
		assertEquals(msg.getMsgException(), null);
		assertTrue(msg.isMsgType(CommandMessage.CMD_MSG_STDOUT));
		assertTrue(msg.isMsgType(CommandMessage.CMD_MSG_STDERR));
		assertTrue(!msg.isMsgType(CommandMessage.CMD_MSG_EXCEPTION));
		
		msg.setMsgStdErr(null);
		assertEquals(msg.getMsgStdOut(), "message stdout");
		assertEquals(msg.getMsgStdErr(), null);
		assertEquals(msg.getMsgException(), null);
		assertTrue(msg.isMsgType(CommandMessage.CMD_MSG_STDOUT));
		assertTrue(!msg.isMsgType(CommandMessage.CMD_MSG_STDERR));
		assertTrue(!msg.isMsgType(CommandMessage.CMD_MSG_EXCEPTION));
		
		msg.setMsgStdOut(null);
		assertEquals(msg.getMsgStdOut(), null);
		assertEquals(msg.getMsgStdErr(), null);
		assertEquals(msg.getMsgException(), null);
		assertTrue(!msg.isMsgType(CommandMessage.CMD_MSG_STDOUT));
		assertTrue(!msg.isMsgType(CommandMessage.CMD_MSG_STDERR));
		assertTrue(!msg.isMsgType(CommandMessage.CMD_MSG_EXCEPTION));
	}

	

}
