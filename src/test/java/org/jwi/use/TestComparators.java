package org.jwi.use;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

public class TestComparators
{
	static private final String[] elements = {"aborigine1", "aborigine2", "Aborigine1", "Aborigine2"};
	static private final String[] elements2 = {"Aborigine1", "Aborigine2", "aborigine1", "aborigine2"};
	static private final String[] elements3 = {"aborigine1", "Aborigine2", "Aborigine1", "aborigine2"};
	static private final String[] elements4 = {"Aborigine1", "aborigine2", "aborigine1", "Aborigine2"};

	@Test
	public void test1()
	{
		testSeries(elements);
	}

	@Test
	public void test2()
	{
		testSeries(elements2);
	}

	@Test
	public void test3()
	{
		testSeries(elements3);
	}

	@Test
	public void test4()
	{
		testSeries(elements4);
	}

	public static void testSeries(String[] elements)
	{
		System.out.printf("INPUT %s%n", Arrays.toString(elements));
		System.out.println("compareToIgnoreCase");
		Set<String> set1 = generate(String::compareToIgnoreCase, elements);
		forAll(set1, String::toString);

		System.out.println("compareTo");
		Set<String> set2 = generate(String::compareTo, elements);
		forAll(set2, String::toString);

		System.out.println("cmpLOUpperFirst");
		Set<String> set3 = generate(Comparators.cmpLOUpperFirst, elements);
		forAll(set3, String::toString);

		System.out.println("cmpLOLowerFirst");
		Set<String> set4 = generate(Comparators.cmpLOLowerFirst, elements);
		forAll(set4, String::toString);
		System.out.println();
	}

	static Set<String> generate(Comparator<String> cmp, String... items)
	{
		//System.out.printf("\tin\t%s%n", Arrays.toString(items));
		Set<String> set = new TreeSet<>(cmp);
		set.addAll(Arrays.asList(items));
		set.addAll(Arrays.asList(items)); // again
		return set;
	}

	static <A, R> void forAll(Set<A> set, Function<A, R> f)
	{
		//System.out.printf("\tout\t%s%n", Arrays.toString(set.toArray()));
		for (A item : set)
		{
			R r = f.apply(item);
			System.out.printf("\t%s%n", r);
		}
	}
}
