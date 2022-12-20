package org.jwi.use;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestJWI
{
	private static JWI jwi;

	@BeforeAll
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
