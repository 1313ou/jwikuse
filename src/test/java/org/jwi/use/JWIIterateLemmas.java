package org.jwi.use;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class JWIIterateLemmas
{
	// private static final boolean verbose = !System.getProperties().containsKey("SILENT");

	private static JWI jwi;

	@BeforeAll
	public static void init() throws IOException
	{
		String wnHome = System.getProperty("SOURCE");
		jwi = new JWI(wnHome);
	}

	@Test
	public void iterateLemmas()
	{
		jwi.forAllLemmas(System.out::println);
	}

	@Test
	public void searchLemmas()
	{
		jwi.forAllLemmas((w) -> {
			if (w.matches("arb.*"))
			{
				System.out.println(w);
			}
		});
	}
}
