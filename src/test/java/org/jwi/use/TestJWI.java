package org.jwi.use;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import edu.mit.jwi.item.ISenseEntry;
import edu.mit.jwi.item.ISenseKey;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;

public class TestJWI
{
	private static JWI jwi;

	@BeforeClass
	public static void init() throws IOException
	{
		String wnHome = System.getenv("WNHOMEXX");
		jwi = new JWI(wnHome, JWI.Mode.XX);
	}

	@Test
	public void walkSpread() throws IOException
	{
		jwi.walk("spread");
	}
}
