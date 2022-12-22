package org.jwi.use;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;

import edu.mit.jwi.item.ISenseEntry;

public class SensekeysCrossTests
{
	private static final boolean verbose = !System.getProperties().containsKey("SILENT");

	private static JWI jwi1;

	private static JWI jwi2;

	@BeforeAll
	public static void init() throws IOException
	{
		String wnHome1 = System.getProperty("SOURCE");
		String wnHome2 = System.getProperty("SOURCE2");
		jwi1 = new JWI(wnHome1);
		jwi2 = new JWI(wnHome2);
	}

	@Test
	public void lookupSensekeys()
	{
		lookupSensekey("you_bet%4:02:00::");
		lookupSensekey("electric%5:00:00:exciting:00");
	}

	@Test
	public void findSensekeys()
	{
		findSensekeysOf("aborigine");
		findSensekeysOf("Aborigine");
	}

	public void findSensekeysOf(String lemma)
	{
		Collection<ISenseEntry> ses1 = Sensekeys.findSensekeysOf(jwi1, lemma);
		Collection<ISenseEntry> ses2 = Sensekeys.findSensekeysOf(jwi2, lemma);
		if (verbose)
		{
			System.out.println("\n⯆" + lemma);
			for (ISenseEntry se : ses1)
			{
				System.out.printf("1 %s %s%n", se.getSenseKey(), se.getOffset());
			}
			for (ISenseEntry se : ses2)
			{
				System.out.printf("2 %s %s%n", se.getSenseKey(), se.getOffset());
			}
		}
	}

	public void lookupSensekey(String skStr)
	{
		ISenseEntry se1 = Sensekeys.lookupSensekey(jwi1, skStr);
		ISenseEntry se2 = Sensekeys.lookupSensekey(jwi2, skStr);
		if (verbose)
		{
			System.out.println("\n⯈" + skStr);
			System.out.printf("1 %s %s%n", se1.getSenseKey(), se1.getOffset());
			System.out.printf("2 %s %s%n", se2.getSenseKey(), se2.getOffset());
		}
	}
}
