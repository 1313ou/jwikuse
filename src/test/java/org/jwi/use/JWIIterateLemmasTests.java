package org.jwi.use;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JWIIterateLemmasTests
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
        jwi.forAllLemmas(PS::println);
    }

    @Test
    public void searchLemmas()
    {
        String start = System.getProperty("TARGET");
        Set<String> actual = jwi.getDict().getWords(start, null);

        Set<String> expected = new TreeSet<>();
        jwi.forAllLemmas((w) -> {
            if (w.startsWith(start))
            {
                expected.add(w);
            }
        });
        assertEquals(expected, actual);
    }
}
