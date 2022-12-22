package org.jwi.use;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class JWITests
{
	private static final boolean VERBOSE = !System.getProperties().containsKey("SILENT");

	private static final PrintStream PS = VERBOSE ? System.out : new PrintStream(new OutputStream()
	{
		public void write(int b)
		{
			//DO NOTHING
		}
	});

	private static String word;

	private static JWI jwi;

	@BeforeAll
	public static void init() throws IOException
	{
		word = System.getProperty("WORD");
		String wnHome = System.getProperty("SOURCE");
		jwi = new JWI(wnHome);
	}

	@Test
	public void walkWord()
	{
		jwi.walk(word, PS);
	}
}
