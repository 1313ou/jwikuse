package org.jwi.use;

import edu.mit.jwi.item.ISenseEntry;
import edu.mit.jwi.item.ISenseKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicInteger;

public class SensekeysTests
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
    public void findAllSensekeys()
    {
        findAllSensekeys(jwi);
    }

    @Test
    public void resolveAllSensekeys()
    {
        resolveAllSensekeys(jwi);
    }

    private static void findAllSensekeys(JWI jwi)
    {
        AtomicInteger count = new AtomicInteger(0);
        AtomicInteger errCount = new AtomicInteger(0);

        jwi.forAllSenses(s -> {
            ISenseKey sk = s.getSenseKey();
            ISenseEntry se = jwi.dict.getSenseEntry(sk);
            if (se == null)
            {
                System.err.printf("Sensekey not found %s%n", sk.toString());
                errCount.incrementAndGet();
                return;
            }
            count.incrementAndGet();
        });
        PS.printf("Sensekeys: %d Errors: %d%n", count.get(), errCount.get());
    }

    private static void resolveAllSensekeys(JWI jwi)
    {
        AtomicInteger count = new AtomicInteger(0);
        AtomicInteger errCount = new AtomicInteger(0);

        jwi.forAllSenses(s -> {
            ISenseKey sk = s.getSenseKey();
            ISenseEntry se = jwi.dict.getSenseEntry(sk);
            if (se == null)
            {
                System.err.printf("Sensekey not found %s%n", sk.toString());
                errCount.incrementAndGet();
                return;
            }
            int ofs = se.getOffset();
            PS.printf("%s %s%n", sk, ofs);
            count.incrementAndGet();
        });
        PS.printf("Sensekeys: %d Errors: %d%n", count.get(), errCount.get());
    }
}
