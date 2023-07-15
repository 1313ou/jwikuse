package org.jwi.use;

import edu.mit.jwi.item.ISenseEntry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;

public class SensekeysCrossTests
{
    private static final boolean VERBOSE = !System.getProperties().containsKey("SILENT");

    private static final PrintStream PS = VERBOSE ? System.out : new PrintStream(new OutputStream()
    {
        public void write(int b)
        {
            //DO NOTHING
        }
    });

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
        PS.println("\n⯆" + lemma);
        for (ISenseEntry se : ses1)
        {
            PS.printf("1 %s %s%n", se.getSenseKey(), se.getOffset());
        }
        for (ISenseEntry se : ses2)
        {
            PS.printf("2 %s %s%n", se.getSenseKey(), se.getOffset());
        }
    }

    public void lookupSensekey(String skStr)
    {
        ISenseEntry se1 = Sensekeys.lookupSensekey(jwi1, skStr);
        ISenseEntry se2 = Sensekeys.lookupSensekey(jwi2, skStr);
        PS.println("\n⯈" + skStr);
        PS.printf("1 %s %s%n", se1.getSenseKey(), se1.getOffset());
        PS.printf("2 %s %s%n", se2.getSenseKey(), se2.getOffset());
    }
}
