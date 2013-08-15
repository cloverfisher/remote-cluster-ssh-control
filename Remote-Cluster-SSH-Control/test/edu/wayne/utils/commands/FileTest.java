package edu.wayne.utils.commands;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.utils.remote.RNode;

public class FileTest {

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
	public void testFileReadBasic() {
		FileCopy fileRead = new FileCopy("~/run/node.energyintcit1"
				, "./", node);
		boolean succeed = fileRead.execute();
		System.out.println(fileRead.getMessage());
		assertTrue(succeed);
	}
	
	

}
