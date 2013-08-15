package edu.wayne.utils.commands;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.utils.remote.RNode;

public class DirTest {

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
	public void testDirExist() {
		DirExist cmd1 = new DirExist("~", node);
		DirExist cmd2 = new DirExist("~/test"+System.currentTimeMillis(), node);
		assertTrue(cmd1.execute());
		assertFalse(cmd2.execute());
	}
	
	
	@Test
	public void testDirMakeRecursive() {
		long curTime = System.currentTimeMillis();
		String dir = "~/test"+curTime + "/test" + (curTime+1000);
		DirMake dirMake = new DirMake(dir, node);
		boolean succeed = dirMake.execute();
		System.out.println(dirMake.getMessage());
		assertTrue(succeed);
		if (succeed) {
			DirDel dirDel = new DirDel(dir, node);
			assertTrue(dirDel.execute());
			dirDel = new DirDel("~/test"+curTime, node);
			assertTrue(dirDel.execute());
		}
	}
	
	
	@Test
	public void testDirMakeDelBasic() {
		String directory = "~/test"+System.currentTimeMillis();
		DirMake dirMake = new DirMake(directory, node);
		DirExist dirExist = new DirExist(directory, node);
		boolean succeed = dirMake.execute();
		System.out.println(dirMake.getMessage());
		assertTrue(succeed);
		assertTrue(dirExist.execute());
		DirDel dirDel = new DirDel(directory, node);
		succeed = dirDel.execute();
		assertTrue(succeed);
		assertFalse(dirExist.execute());
	}

	@Test
	public void testDirMakeUndo() {
		long current = System.currentTimeMillis();
		String dir1 = "~/test"+current;
		String dir2 = dir1+"/"+(current+10000);
		DirMake dirMake1 = new DirMake(dir1, node);
		DirMake dirMake2 = new DirMake(dir2, node);
		assertTrue(dirMake1.execute());
		assertTrue(dirMake2.execute());
		assertTrue(dirMake2.undo());
		assertTrue(dirMake1.undo());
		assertFalse(new DirExist(dir1,node).execute());
	}
	
	@Test
	public void testDirDelUndo() {
		//build test environment
		long current = System.currentTimeMillis();
		String dir1 = "~/test"+current;
		String dir2 = dir1+"/"+(current+10000);
		DirMake dirMake1 = new DirMake(dir1, node);
		DirMake dirMake2 = new DirMake(dir2, node);
		assertTrue(dirMake1.execute());
		assertTrue(dirMake2.execute());
		// test delete undo
		DirDel dirDel1 = new DirDel(dir1, node);
		DirDel dirDel2 = new DirDel(dir2, node);
		assertTrue(dirDel2.execute());
		assertTrue(dirDel1.execute());
		assertTrue(dirDel1.undo());
		assertTrue(dirDel2.undo());
		assertTrue(new DirExist(dir1, node).execute());
		assertTrue(new DirExist(dir2, node).execute());
		//restore environment
		assertTrue(dirMake2.undo());
		assertTrue(dirMake1.undo());
		assertFalse(new DirExist(dir1,node).execute());
	}
}
