package org.jwi.use;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import edu.mit.jwi.item.ISenseEntry;
import edu.mit.jwi.item.ISenseKey;

public class TestSensekeys
{
	private static boolean verbose = !System.getProperties().containsKey("SILENT");

	private static JWI jwi1;

	private static JWI jwi2;

	@BeforeAll
	public static void init() throws IOException
	{
		String wnHome1 = System.getProperty("SOURCE");
		String wnHome2 = System.getProperty("SOURCE2");
		//jwi1 = new JWI(wnHome1, JWI.Mode.XX);
		//jwi2 = new JWI(wnHome1, JWI.Mode.XX_POOLS);
		jwi1 = new JWI(wnHome1);
		jwi2 = new JWI(wnHome2);
	}

	@Test
	public void lookupSensekeys() throws IOException
	{
		lookupSensekey("you_bet%4:02:00::");
		lookupSensekey("electric%5:00:00:exciting:00");
	}

	@Test
	public void findSensekeys() throws IOException
	{
		findSensekeysOf("aborigine");
		findSensekeysOf("Aborigine");
	}

	@Test
	public void findAllSensekeys1()
	{
		findAllSensekeys(jwi1);
	}

	@Test
	public void findAllSensekeys2()
	{
		findAllSensekeys(jwi2);
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
				System.out.printf("1 %-10s %s %s%n", jwi1.mode, se.getSenseKey(), se.getOffset());
			}
			for (ISenseEntry se : ses2)
			{
				System.out.printf("2 %-10s %s %s%n", jwi2.mode, se.getSenseKey(), se.getOffset());
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
			System.out.printf("1 %-10s %s %s%n", jwi1.mode, se1.getSenseKey(), se1.getOffset());
			System.out.printf("2 %-10s %s %s%n", jwi2.mode, se2.getSenseKey(), se2.getOffset());
		}
	}

	private static void findAllSensekeys(JWI jwi)
	{
		AtomicInteger count = new AtomicInteger(0);
		AtomicInteger errCount = new AtomicInteger(0);

		jwi.forAllSenses(s -> {
			ISenseKey sk = s.getSenseKey();
			ISenseEntry se = jwi.getDict().getSenseEntry(sk);
			if (se == null)
			{
				System.err.printf("Sensekey not found %s%n", sk.toString());
				errCount.incrementAndGet();
			}
			else
			{
				int ofs = se.getOffset();
				if (verbose)
				{
					System.out.printf("%s %s%n", sk, ofs);
				}
				count.incrementAndGet();
			}
		});
		System.out.printf("Sensekeys: %d Errors: %d%n", count.get(), errCount.get());
	}
}
