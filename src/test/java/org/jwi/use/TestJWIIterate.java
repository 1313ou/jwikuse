package org.jwi.use;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import edu.mit.jwi.item.ISenseEntry;
import edu.mit.jwi.item.ISenseKey;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;

public class TestJWIIterate
{
	private static JWI jwi;

	@BeforeClass
	public static void init() throws IOException
	{
		String wnHome = System.getenv("WNHOMEXX");
		jwi = new JWI(wnHome, JWI.Mode.XX);
	}

	@Test
	public void iterateLemmas() throws IOException
	{
		jwi.forAllLemmas((String l) -> {
		});
	}

	@Test
	public void iterateSenses() throws IOException
	{
		jwi.forAllSenses((IWord s) -> {
		});
	}

	@Test
	public void iterateSynsets() throws IOException
	{
		jwi.forAllSynsets((ISynset s) -> {
		});
	}

	@Test
	public void iterateSenseEntries()
	{
		jwi.forAllSenseEntries((ISenseEntry se) -> {
		});
	}

	@Test
	public void iterateSenseRelations()
	{
		jwi.forAllSenseRelations((IWord r) -> {
		});
	}

	@Test
	public void iterateSynsetRelations()
	{
		jwi.forAllSynsetRelations((ISynset r) -> {
		});
	}

	@Test
	public void iterateSenseKeys()
	{
		jwi.forAllSensekeys((ISenseKey sk) -> {
		});
	}
}
