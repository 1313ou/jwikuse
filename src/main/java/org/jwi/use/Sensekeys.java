package org.jwi.use;

import java.util.ArrayList;
import java.util.Collection;

import edu.mit.jwi.data.parse.SenseKeyParser;
import edu.mit.jwi.item.ISenseEntry;
import edu.mit.jwi.item.ISenseKey;
import edu.mit.jwi.item.IWord;

public class Sensekeys
{
	public static Collection<ISenseEntry> findSensekeysOf(JWI jwi, String lemma)
	{
		Collection<ISenseEntry> result = new ArrayList<>();
		jwi.forAllSenseIDs(lemma, (si) -> {

			IWord sense = jwi.getDict().getWord(si);
			ISenseKey generatedSk = sense.getSenseKey();
			// lookup
			ISenseEntry se = jwi.getDict().getSenseEntry(generatedSk);
			result.add(se);
		});
		return result;
	}

	public static ISenseEntry lookupSensekey(JWI jwi, String skStr)
	{
		ISenseKey parsedSk = SenseKeyParser.getInstance().parseLine(skStr);
		assert skStr.equals(parsedSk.toString());
		// lookup
		return jwi.getDict().getSenseEntry(parsedSk);
	}
}
