package org.jwi.use;

import edu.mit.jwi.item.ISenseEntry;
import edu.mit.jwi.item.ISenseKey;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class JWIIterateTests
{
    private static final boolean VERBOSE = !System.getProperties().containsKey("SILENT");

    private static final PrintStream PS = VERBOSE ? System.out : new PrintStream(new OutputStream()
    {
        public void write(int b)
        {
            //DO NOTHING
        }
    });

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
        jwi.forAllLemmas((String l) -> {
        });
    }

    @Test
    public void iterateSenses()
    {
        jwi.forAllSenses((IWord s) -> {
        });
    }

    @Test
    public void iterateSynsets()
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
