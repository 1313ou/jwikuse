package org.jwi.use;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class JWICrossTests
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

    private static JWI jwi1;

    private static JWI jwi2;

    @BeforeAll
    public static void init() throws IOException
    {
        word = System.getProperty("WORD");
        String wnHome1 = System.getProperty("SOURCE");
        String wnHome2 = System.getProperty("SOURCE2");
        jwi1 = new JWI(wnHome1);
        jwi2 = new JWI(wnHome2);
    }

    @Test
    public void walkWord()
    {
        walk2(word);
    }

    @Test
    public void walkWord1()
    {
        PS.println(jwi1.wnHome);
        jwi1.walk(word, PS);
    }

    @Test
    public void walkWord2()
    {
        PS.println(jwi2.wnHome);
        jwi2.walk(word, PS);
    }

    private void walk2(String lemma)
    {
        PS.println(jwi1.wnHome);
        jwi1.walk(lemma, PS);
        System.out.println(jwi2.wnHome);
        jwi2.walk(lemma, PS);
    }
}
