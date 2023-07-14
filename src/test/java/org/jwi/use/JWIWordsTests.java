package org.jwi.use;

import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.POS;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class JWIWordsTests
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
    public void searchWord()
    {
        for (final POS pos : POS.values())
        {
            IIndexWord index = jwi.getDict().getIndexWord(word, pos);
            if (index != null)
            {
                String lemma = index.getLemma();
                System.out.println(pos + " " + lemma);
            }
        }
    }
}
