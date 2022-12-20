package org.jwi.use;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestAskJWI
{
	private static String word;

	private static JWI jwi;

	@BeforeAll
	public static void init() throws IOException
	{
		word = System.getProperty("WORD");
		String wnHome = System.getenv("WNHOMEXX");
		jwi = new JWI(wnHome, JWI.Mode.XX);
	}

	@Test
	public void walkWord()
	{
		jwi.walk(word);
	}
}
