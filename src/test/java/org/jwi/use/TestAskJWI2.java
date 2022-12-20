package org.jwi.use;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestAskJWI2
{
	private static String word;

	private static JWI jwi1;

	private static JWI jwi2;

	@BeforeAll
	public static void init() throws IOException
	{
		word = System.getProperty("WORD");
		String wnHome1 = System.getenv("WNHOME31");
		String wnHome2 = System.getenv("WNHOMEXX");
		jwi1 = new JWI(wnHome1);
		jwi2 = new JWI(wnHome2);
	}

	@Test
	public void walkWord()
	{
		walk2(word);
	}

	@Test
	public void walkWord1()
	{
		System.out.println(jwi1.wnHome);
		jwi1.walk(word);
	}

	@Test
	public void walkWord2()
	{
		System.out.println(jwi2.wnHome);
		jwi2.walk(word);
	}

	private void walk2(String lemma)
	{
		System.out.println(jwi1.wnHome);
		jwi1.walk(lemma);
		System.out.println(jwi2.wnHome);
		jwi2.walk(lemma);
	}
}
