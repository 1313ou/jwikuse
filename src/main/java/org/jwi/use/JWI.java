package org.jwi.use;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import edu.mit.jwi.Config;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.*;

/**
 * JWI
 *
 * @author Bernard Bou
 */
public class JWI
{
	public final String wnHome;

	private final IDictionary dict;

	public JWI(final String wnHome) throws IOException
	{
		this(wnHome, null);
	}

	public JWI(final String wnHome, final Config config) throws IOException
	{
		this.wnHome = wnHome;
		System.out.printf("FROM %s%n", wnHome);

		// construct the URL to the WordNet dictionary directory
		URL url = new File(wnHome).toURI().toURL();

		// construct the dictionary object and open it
		this.dict = new Dictionary(url, config);

		// open it
		this.dict.open();
	}

	public IDictionary getDict()
	{
		return dict;
	}

	// M A I N   I T E R A T I O N S

	public void forAllSenses(final Consumer<IWord> f)
	{
		for (final POS pos : POS.values())
		{
			Iterator<IIndexWord> it = this.dict.getIndexWordIterator(pos);
			while (it.hasNext())
			{
				IIndexWord idx = it.next();
				final List<IWordID> senseids = idx.getWordIDs();
				for (final IWordID senseid : senseids) // synset id, sense number, and lemma
				{
					IWord sense = this.dict.getWord(senseid);
					if (sense == null)
					{
						System.err.printf("⚠ senseid: %s ➜ null sense", senseid.toString());
						//IWord sense2 = this.dict.getWord(senseid);
						continue;
					}
					if (f != null)
					{
						f.accept(sense);
					}
				}
			}
		}
	}

	public void tryForAllSenses(final Consumer<IWord> f)
	{
		for (final POS pos : POS.values())
		{
			Iterator<IIndexWord> it = this.dict.getIndexWordIterator(pos);
			while (it.hasNext())
			{
				try
				{
					IIndexWord idx = it.next();
					final List<IWordID> senseids = idx.getWordIDs();
					for (final IWordID senseid : senseids) // synset id, sense number, and lemma
					{
						IWord sense = this.dict.getWord(senseid);
						if (sense == null)
						{
							System.err.printf("⚠ senseid: %s ➜ null sense", senseid.toString());
							//IWord sense2 = this.dict.getWord(senseid);
							continue;
						}
						if (f != null)
						{
							f.accept(sense);
						}
					}
				}
				catch (Exception e)
				{
					System.err.println(it + " " + e.getMessage());
				}
			}
		}
	}

	public void forAllSynsets(final Consumer<ISynset> f)
	{
		for (final POS pos : POS.values())
		{
			Iterator<ISynset> it = this.dict.getSynsetIterator(pos);
			while (it.hasNext())
			{
				ISynset synset = it.next();
				if (f != null)
				{
					f.accept(synset);
				}
			}
		}
	}

	public void tryForAllSynsets(final Consumer<ISynset> f)
	{
		for (final POS pos : POS.values())
		{
			Iterator<ISynset> it = this.dict.getSynsetIterator(pos);
			while (it.hasNext())
			{
				try
				{
					ISynset synset = it.next();
					if (f != null)
					{
						f.accept(synset);
					}
				}
				catch (Exception e)
				{
					System.err.println(it + " " + e.getMessage());
				}
			}
		}
	}

	public void forAllSenseEntries(final Consumer<ISenseEntry> f)
	{
		Iterator<ISenseEntry> it = this.dict.getSenseEntryIterator();
		while (it.hasNext())
		{
			ISenseEntry entry = it.next();
			if (f != null)
			{
				f.accept(entry);
			}
		}
	}

	public void tryForAllSenseEntries(final Consumer<ISenseEntry> f)
	{
		Iterator<ISenseEntry> it = this.dict.getSenseEntryIterator();
		while (it.hasNext())
		{
			try
			{
				ISenseEntry entry = it.next();
				if (f != null)
				{
					f.accept(entry);
				}
			}
			catch (Exception e)
			{
				System.err.println(it + " " + e.getMessage());
			}
		}
	}

	// S P E C I F I C   I T E R A T I O N S

	public void forAllLemmas(final Consumer<String> f)
	{
		for (final POS pos : POS.values())
		{
			Iterator<IIndexWord> it = this.dict.getIndexWordIterator(pos);
			while (it.hasNext())
			{
				IIndexWord idx = it.next();
				final List<IWordID> senseids = idx.getWordIDs();
				for (final IWordID senseid : senseids) // synset id, sense number, and lemma
				{
					IWord sense = this.dict.getWord(senseid);
					if (sense == null)
					{
						System.err.printf("⚠ senseid: %s ➜ null sense", senseid.toString());
						// IWord sense2 = this.dict.getWord(senseid);
						continue;
					}
					String lemma = sense.getLemma();
					if (f != null)
					{
						f.accept(lemma);
					}
				}
			}
		}
	}

	public void tryForAllLemmas(final Consumer<String> f)
	{
		for (final POS pos : POS.values())
		{
			Iterator<IIndexWord> it = this.dict.getIndexWordIterator(pos);
			while (it.hasNext())
			{
				IIndexWord idx = it.next();
				final List<IWordID> senseids = idx.getWordIDs();
				for (final IWordID senseid : senseids) // synset id, sense number, and lemma
				{
					try
					{
						IWord sense = this.dict.getWord(senseid);
						if (sense == null)
						{
							System.err.printf("⚠ senseid: %s ➜ null sense", senseid.toString());
							// IWord sense2 = this.dict.getWord(senseid);
							continue;
						}
						String lemma = sense.getLemma();
						if (f != null)
						{
							f.accept(lemma);
						}
					}
					catch (Exception e)
					{
						System.err.println(senseid + " " + e.getMessage());
					}
				}
			}
		}
	}

	public void forAllSynsetRelations(final Consumer<ISynset> f)
	{
		for (final POS pos : POS.values())
		{
			Iterator<ISynset> it = this.dict.getSynsetIterator(pos);
			while (it.hasNext())
			{
				ISynset synset = it.next();
				List<ISynsetID> relatedIds = synset.getRelatedSynsets();
				for (ISynsetID relatedId : relatedIds)
				{
					ISynset related = this.dict.getSynset(relatedId);
					if (f != null)
					{
						f.accept(related);
					}
				}
			}
		}
	}

	public void tryForAllSynsetRelations(final Consumer<ISynset> f)
	{
		for (final POS pos : POS.values())
		{
			Iterator<ISynset> it = this.dict.getSynsetIterator(pos);
			while (it.hasNext())
			{
				try
				{
					ISynset synset = it.next();
					List<ISynsetID> relatedIds = synset.getRelatedSynsets();
					for (ISynsetID relatedId : relatedIds)
					{
						try
						{
							ISynset related = this.dict.getSynset(relatedId);
							if (f != null)
							{
								f.accept(related);
							}
						}
						catch (Exception e)
						{
							System.err.println(relatedId + " " + e.getMessage());
						}
					}
				}
				catch (Exception e)
				{
					System.err.println(it + " " + e.getMessage());
				}
			}
		}
	}

	public void forAllSenseRelations(final Consumer<IWord> f)
	{
		for (final POS pos : POS.values())
		{
			Iterator<IIndexWord> it = this.dict.getIndexWordIterator(pos);
			while (it.hasNext())
			{
				IIndexWord idx = it.next();
				final List<IWordID> senseids = idx.getWordIDs();
				for (final IWordID senseid : senseids) // synset id, sense number, and lemma
				{
					IWord sense = this.dict.getWord(senseid);
					if (sense == null)
					{
						System.err.printf("⚠ senseid: %s ➜ null sense", senseid.toString());
						//IWord sense2 = this.dict.getWord(senseid);
						continue;
					}
					List<IWordID> relatedIds = sense.getRelatedWords();
					for (IWordID relatedId : relatedIds)
					{
						IWord related = this.dict.getWord(relatedId);
						if (f != null)
						{
							f.accept(related);
						}
					}
				}
			}
		}
	}

	public void tryForAllSenseRelations(final Consumer<IWord> f)
	{
		for (final POS pos : POS.values())
		{
			Iterator<IIndexWord> it = this.dict.getIndexWordIterator(pos);
			while (it.hasNext())
			{
				IIndexWord idx = it.next();
				final List<IWordID> senseids = idx.getWordIDs();
				for (final IWordID senseid : senseids) // synset id, sense number, and lemma
				{
					try
					{
						IWord sense = this.dict.getWord(senseid);
						if (sense == null)
						{
							System.err.printf("⚠ senseid: %s ➜ null sense", senseid.toString());
							//IWord sense2 = this.dict.getWord(senseid);
							continue;
						}
						List<IWordID> relatedIds = sense.getRelatedWords();
						for (IWordID relatedId : relatedIds)
						{
							IWord related = this.dict.getWord(relatedId);
							if (f != null)
							{
								f.accept(related);
							}
						}
					}
					catch (Exception e)
					{
						System.err.println(senseid + " " + e.getMessage());
					}
				}
			}
		}
	}

	// S E N S E   E X P L O R A T I O N

	public void forAllSenseIDs(final String lemma, final Consumer<IWordID> f)
	{
		for (final POS pos : POS.values())
		{
			final IIndexWord idx = this.dict.getIndexWord(lemma, pos);
			if (idx != null)
			{
				final List<IWordID> senseids = idx.getWordIDs();
				for (final IWordID senseid : senseids) // synset id, sense number, and lemma
				{
					if (f != null)
					{
						f.accept(senseid);
					}
				}
			}
		}
	}

	public void forAllSenses(final String lemma, final Consumer<IWord> f)
	{
		for (final POS pos : POS.values())
		{
			final IIndexWord idx = this.dict.getIndexWord(lemma, pos);
			if (idx != null)
			{
				final List<IWordID> senseids = idx.getWordIDs();
				for (final IWordID senseid : senseids) // synset id, sense number, and lemma
				{
					IWord sense = this.dict.getWord(senseid);
					if (f != null)
					{
						f.accept(sense);
					}
				}
			}
		}
	}

	public void forAllSensekeys(final Consumer<ISenseKey> f)
	{
		for (final POS pos : POS.values())
		{
			Iterator<IIndexWord> it = this.dict.getIndexWordIterator(pos);
			while (it.hasNext())
			{
				IIndexWord idx = it.next();
				final List<IWordID> senseids = idx.getWordIDs();
				for (final IWordID senseid : senseids) // synset id, sense number, and lemma
				{
					IWord sense = this.dict.getWord(senseid);
					if (sense == null)
					{
						System.err.printf("⚠ senseid: %s ➜ null sense", senseid.toString());
						//IWord sense2 = this.dict.getWord(senseid);
						continue;
					}
					ISenseKey sensekey = sense.getSenseKey();
					if (f != null)
					{
						f.accept(sensekey);
					}
				}
			}
		}
	}

	public void tryForAllSensekeys(final Consumer<ISenseKey> f)
	{
		for (final POS pos : POS.values())
		{
			Iterator<IIndexWord> it = this.dict.getIndexWordIterator(pos);
			while (it.hasNext())
			{
				IIndexWord idx = it.next();
				final List<IWordID> senseids = idx.getWordIDs();
				for (final IWordID senseid : senseids) // synset id, sense number, and lemma
				{
					try
					{
						IWord sense = this.dict.getWord(senseid);
						if (sense == null)
						{
							System.err.printf("⚠ senseid: %s ➜ null sense", senseid.toString());
							//IWord sense2 = this.dict.getWord(senseid);
							continue;
						}
						ISenseKey sensekey = sense.getSenseKey();
						if (f != null)
						{
							f.accept(sensekey);
						}
					}
					catch (Exception e)
					{
						System.err.println(senseid + " " + e.getMessage());
					}
				}
			}
		}
	}

	// T R E E   E X P L O R A T I O N S

	public void walk(final String lemma, final PrintStream ps)
	{
		for (final POS pos : POS.values())
		{
			walk(lemma, pos, ps);
		}
	}

	public void walk(final String lemma, final POS pos, final PrintStream ps)
	{
		// a line in an index file
		final IIndexWord idx = this.dict.getIndexWord(lemma, pos);
		if (idx != null)
		{
			// index
			ps.println();
			ps.println("================================================================================");
			ps.println("■ pos = " + pos.name());
			// ps.println("lemma = " + idx.getLemma());
			walk(idx, ps);
		}
	}

	public void walk(final IIndexWord idx, final PrintStream ps)
	{
		Set<IPointer> pointers = idx.getPointers();
		for (IPointer ptr : pointers)
		{
			ps.println("has relation = " + ptr.toString());
		}

		// senseid=(lemma, synsetid, sensenum)
		final List<IWordID> senseids = idx.getWordIDs();
		for (final IWordID senseid : senseids) // synset id, sense number, and lemma
		{
			walk(senseid, ps);
		}
	}

	public void walk(final IWordID senseid, final PrintStream ps)
	{
		ps.println("--------------------------------------------------------------------------------");
		//ps.println("senseid = " + senseid.toString());

		// sense=(senseid, lexid, sensekey, synset)
		IWord sense = this.dict.getWord(senseid);
		walk(sense, ps);

		// synset
		final ISynsetID synsetid = senseid.getSynsetID();
		final ISynset synset = this.dict.getSynset(synsetid);
		ps.printf("● synset = %s%n", toString(synset));

		walk(synset, 1, ps);
	}

	public void walk(final IWord sense, final PrintStream ps)
	{
		ps.printf("● sense: %s lexid: %d sensekey: %s%n", sense.toString(), sense.getLexicalID(), sense.getSenseKey());

		// adj marker
		AdjMarker marker = sense.getAdjectiveMarker();
		if (marker != null)
		{
			ps.println("  marker = " + marker);
		}

		// sensekey
		ISenseKey senseKey = sense.getSenseKey();
		ISenseEntry senseEntry = this.dict.getSenseEntry(senseKey);
		if (senseEntry == null)
		{
			System.err.printf("⚠ Missing sensekey %s for sense at offset %d with pos %s%n", senseKey.toString(), sense.getSynset().getOffset(), sense.getPOS().toString());
			// throw new IllegalArgumentException(String.format("%s at offset %d with pos %s%n", senseKey.toString(), sense.getSynset().getOffset(),sense.getPOS().toString()));
		}

		// lexical relations
		Map<IPointer, List<IWordID>> relatedMap = sense.getRelatedMap();
		walk(relatedMap, ps);

		// verb frames
		List<IVerbFrame> verbFrames = sense.getVerbFrames();
		walk(verbFrames, sense.getLemma(), ps);

		ps.printf("  sensenum: %s tag cnt:%s%n", senseEntry == null ? "<missing>" : senseEntry.getSenseNumber(), senseEntry == null ? "<missing>" : senseEntry.getTagCount());
	}

	public void walk(final Map<IPointer, List<IWordID>> relatedMap, final PrintStream ps)
	{
		if (relatedMap != null)
		{
			for (Map.Entry<IPointer, List<IWordID>> entry : relatedMap.entrySet())
			{
				IPointer pointer = entry.getKey();
				for (IWordID relatedId : entry.getValue())
				{
					IWord related = this.dict.getWord(relatedId);
					ps.printf("  related %s lemma:%s synset:%s%n", pointer, related.getLemma(), related.getSynset().toString());
				}
			}
		}
	}

	public void walk(final List<IVerbFrame> verbFrames, final String lemma, final PrintStream ps)
	{
		if (verbFrames != null)
		{
			for (IVerbFrame verbFrame : verbFrames)
			{
				ps.printf("  verb frame: %s : %s%n", verbFrame.getTemplate(), verbFrame.instantiateTemplate(lemma));
			}
		}
	}

	public void walk(final ISynset synset, final int level, final PrintStream ps)
	{
		final String indentSpace = new String(new char[level]).replace('\0', '\t');
		final Map<IPointer, List<ISynsetID>> links = synset.getRelatedMap();
		for (final IPointer p : links.keySet())
		{
			ps.printf("%s🡆 %s%n", indentSpace, p.getName());
			final List<ISynsetID> relations2 = links.get(p);
			walk(relations2, p, level, ps);
		}
	}

	public void walk(final List<ISynsetID> relations2, final IPointer p, final int level, final PrintStream ps)
	{
		final String indentSpace = new String(new char[level]).replace('\0', '\t');
		for (final ISynsetID synsetid2 : relations2)
		{
			final ISynset synset2 = this.dict.getSynset(synsetid2);
			ps.printf("%s%s%n", indentSpace, toString(synset2));

			walk(synset2, p, level + 1, ps);
		}
	}

	public void walk(final ISynset synset, final IPointer p, final int level, final PrintStream ps)
	{
		final String indentSpace = new String(new char[level]).replace('\0', '\t');
		final List<ISynsetID> relations2 = synset.getRelatedSynsets(p);
		for (final ISynsetID synsetid2 : relations2)
		{
			final ISynset synset2 = this.dict.getSynset(synsetid2);
			ps.printf("%s%s%n", indentSpace, toString(synset2));
			if (canRecurse(p))
			{
				walk(synset2, p, level + 1, ps);
			}
		}
	}

	// H E L P E R S

	public static String toString(final ISynset synset)
	{
		return getMembers(synset) + synset.getGloss();
	}

	public static String getMembers(final ISynset synset)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append('{');
		boolean first = true;
		for (final IWord sense : synset.getWords())
		{
			if (first)
			{
				first = false;
			}
			else
			{
				sb.append(' ');
			}
			sb.append(sense.getLemma());
		}
		sb.append('}');
		sb.append(' ');
		return sb.toString();
	}

	private static boolean canRecurse(IPointer p)
	{
		String symbol = p.getSymbol();
		switch (symbol)
		{
			case "@": // hypernym
			case "~": // hyponym
			case "%p": // part holonym
			case "#p": // part meronym
			case "%m": // member holonym
			case "#m": // member meronym
			case "%s": // substance holonym
			case "#s": // substance meronym
			case "*": // entail
			case ">": // cause
				return true;
		}
		return false;
	}

	/**
	 * Main
	 *
	 * @param args arguments
	 * @throws IOException io exception
	 */
	public static void main(final String[] args) throws IOException
	{
		final String wnHome = args[0];
		final String lemma = args[1];
		new JWI(wnHome, null).walk(lemma, System.out);
	}
}
