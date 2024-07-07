package org.jwi.use;

import edu.mit.jwi.Config;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

public class JWITests
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
		// Config config = new Config();
		// config.charSet = Charset.defaultCharset();
	    // jwi = new JWI(wnHome, config);
	    jwi = new JWI(wnHome);
    }

    @Test
    public void walkWord()
    {
        jwi.walk(word, PS);
    }
}
