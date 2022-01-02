package org.jwi.use;

import java.util.Comparator;

public class Comparators
{
	public static final Comparator<String> cmpLOUpperFirst = (s1, s2) -> {
		int c = s1.compareToIgnoreCase(s2);
		if (c != 0)
		{
			return c;
		}
		return s1.compareTo(s2);
	};

	public static final Comparator<String> cmpLOLowerFirst = (s1, s2) -> {
		int c = s1.compareToIgnoreCase(s2);
		if (c != 0)
		{
			return c;
		}
		return -s1.compareTo(s2);
	};
}
