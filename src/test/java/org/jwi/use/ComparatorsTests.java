package org.jwi.use;

import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComparatorsTests
{
    private static final boolean VERBOSE = !System.getProperties().containsKey("SILENT");

    private static final PrintStream PS = VERBOSE ? System.out : new PrintStream(new OutputStream()
    {
        public void write(int b)
        {
            //DO NOTHING
        }
    });

    static private final String[] elements1 = {"aborigine1", "aborigine2", "Aborigine1", "Aborigine2"};
    static private final String[] elements2 = {"Aborigine1", "Aborigine2", "aborigine1", "aborigine2"};
    static private final String[] elements3 = {"aborigine1", "Aborigine2", "Aborigine1", "aborigine2"};
    static private final String[] elements4 = {"Aborigine1", "aborigine2", "aborigine1", "Aborigine2"};

    static private final String[] expectedIC = new String[]{ //
            "aborigine1", "aborigine2"}; // compareToIgnoreCase
    static private final String[] expectedIC2 = new String[]{ //
            "Aborigine1", "Aborigine2"}; // compareToIgnoreCase
    static private final String[] expectedIC3 = new String[]{ //
            "aborigine1", "Aborigine2"}; // compareToIgnoreCase

    static private final String[] expectedIC4 = new String[]{ //
            "Aborigine1", "aborigine2"}; // compareToIgnoreCase

    static private final String[][] expected = new String[][]{ //
            {"Aborigine1", "Aborigine2", "aborigine1", "aborigine2"}, // compareTo
            {"Aborigine1", "aborigine1", "Aborigine2", "aborigine2"}, // cmpLOUpperFirst
            {"aborigine1", "Aborigine1", "aborigine2", "Aborigine2"} // cmpLOLowerFirst
    };

    @Test
    public void test1()
    {
        testSeries(elements1, expectedIC, expected);
    }

    @Test
    public void test2()
    {
        testSeries(elements2, expectedIC2, expected);
    }

    @Test
    public void test3()
    {
        testSeries(elements3, expectedIC3, expected);
    }

    @Test
    public void test4()
    {
        testSeries(elements4, expectedIC4, expected);
    }

    public static void testSeries(String[] elements, String[] expectedIC, String[][] expected)
    {
        PS.printf("INPUT %s%n", Arrays.toString(elements));

        SortedSet<String> set1 = generate(String::compareToIgnoreCase, elements);
        PS.println("compareToIgnoreCase");
        PS.printf("\tout\t%s%n", Arrays.toString(set1.toArray()));
        PS.printf("\texp\t%s%n", Arrays.toString(expectedIC));
        assertStreamEquals(Arrays.stream(expectedIC), set1.stream());

        SortedSet<String> set2 = generate(String::compareTo, elements);
        PS.println("compareTo");
        PS.printf("\tout\t%s%n", Arrays.toString(set2.toArray()));
        PS.printf("\texp\t%s%n", Arrays.toString(expected[0]));
        assertStreamEquals(Arrays.stream(expected[0]), set2.stream());

        SortedSet<String> set3 = generate(Comparators.cmpLOUpperFirst, elements);
        PS.println("cmpLOUpperFirst");
        PS.printf("\tout\t%s%n", Arrays.toString(set3.toArray()));
        PS.printf("\texp\t%s%n", Arrays.toString(expected[1]));
        assertStreamEquals(Arrays.stream(expected[1]), set3.stream());

        SortedSet<String> set4 = generate(Comparators.cmpLOLowerFirst, elements);
        PS.println("cmpLOLowerFirst");
        PS.printf("\tout\t%s%n", Arrays.toString(set4.toArray()));
        PS.printf("\texp\t%s%n", Arrays.toString(expected[2]));
        assertStreamEquals(Arrays.stream(expected[2]), set4.stream());

        PS.println();
    }

    static SortedSet<String> generate(Comparator<String> cmp, String... items)
    {
        //System.out.printf("\tin\t%s%n", Arrays.toString(items));
        SortedSet<String> set = new TreeSet<>(cmp);
        set.addAll(Arrays.asList(items));
        set.addAll(Arrays.asList(items)); // again
        return set;
    }

    static <A, R> String forAll(Set<A> set, Function<A, R> f)
    {
        return set.stream().map(f).map(s -> "\t" + s).collect(Collectors.joining("\n"));
    }

    static void assertStreamEquals(Stream<?> s1, Stream<?> s2)
    {
        Iterator<?> iter1 = s1.iterator(), iter2 = s2.iterator();
        while (iter1.hasNext() && iter2.hasNext())
        {
            assertEquals(iter1.next(), iter2.next());
        }
        assert !iter1.hasNext() && !iter2.hasNext();
    }
}
