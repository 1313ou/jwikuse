package org.kwi.use

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.kwi.use.Comparators
import java.io.OutputStream
import java.io.PrintStream
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors
import java.util.stream.Stream

class ComparatorsTests {

    @Test
    fun test1() {
        testSeries(elements1, expectedIC, expected)
    }

    @Test
    fun test2() {
        testSeries(elements2, expectedIC2, expected)
    }

    @Test
    fun test3() {
        testSeries(elements3, expectedIC3, expected)
    }

    @Test
    fun test4() {
        testSeries(elements4, expectedIC4, expected)
    }

    companion object {

        private val VERBOSE = !System.getProperties().containsKey("SILENT")

        private val PS: PrintStream = if (VERBOSE) System.out else PrintStream(object : OutputStream() {
            override fun write(b: Int) {
                //DO NOTHING
            }
        })

        private val elements1 = arrayOf<String>("aborigine1", "aborigine2", "Aborigine1", "Aborigine2")
        private val elements2 = arrayOf<String>("Aborigine1", "Aborigine2", "aborigine1", "aborigine2")
        private val elements3 = arrayOf<String>("aborigine1", "Aborigine2", "Aborigine1", "aborigine2")
        private val elements4 = arrayOf<String>("Aborigine1", "aborigine2", "aborigine1", "Aborigine2")

        private val expectedIC: Array<String> = arrayOf<String>( //
            "aborigine1", "aborigine2"
        ) // compareToIgnoreCase
        private val expectedIC2: Array<String> = arrayOf<String>( //
            "Aborigine1", "Aborigine2"
        ) // compareToIgnoreCase
        private val expectedIC3: Array<String> = arrayOf<String>( //
            "aborigine1", "Aborigine2"
        ) // compareToIgnoreCase

        private val expectedIC4: Array<String> = arrayOf<String>( //
            "Aborigine1", "aborigine2"
        ) // compareToIgnoreCase

        private val expected = arrayOf<Array<String>>( //
            arrayOf<String>("Aborigine1", "Aborigine2", "aborigine1", "aborigine2"),  // compareTo
            arrayOf<String>("Aborigine1", "aborigine1", "Aborigine2", "aborigine2"),  // cmpLOUpperFirst
            arrayOf<String>("aborigine1", "Aborigine1", "aborigine2", "Aborigine2") // cmpLOLowerFirst
        )

        fun testSeries(elements: Array<String>, expectedIC: Array<String>, expected: Array<Array<String>>) {
            PS.printf("INPUT %s%n", elements.contentToString())

            val set1: SortedSet<String?> = generate(Comparator { obj: String?, str: String? -> obj!!.compareTo(str!!, ignoreCase = true) }, *elements)
            PS.println("compareToIgnoreCase")
            PS.printf("\tout\t%s%n", set1.toTypedArray().contentToString())
            PS.printf("\texp\t%s%n", expectedIC.contentToString())
            assertStreamEquals(Arrays.stream<String?>(expectedIC), set1.stream())

            val set2: SortedSet<String?> = generate(Comparator { obj: String?, anotherString: String? -> obj!!.compareTo(anotherString!!) }, *elements)
            PS.println("compareTo")
            PS.printf("\tout\t%s%n", set2.toTypedArray().contentToString())
            PS.printf("\texp\t%s%n", expected[0].contentToString())
            assertStreamEquals(Arrays.stream<String?>(expected[0]), set2.stream())

            val set3: SortedSet<String?> = generate(Comparators.cmpLOUpperFirst, *elements)
            PS.println("cmpLOUpperFirst")
            PS.printf("\tout\t%s%n", set3.toTypedArray().contentToString())
            PS.printf("\texp\t%s%n", expected[1].contentToString())
            assertStreamEquals(Arrays.stream<String?>(expected[1]), set3.stream())

            val set4: SortedSet<String?> = generate(Comparators.cmpLOLowerFirst, *elements)
            PS.println("cmpLOLowerFirst")
            PS.printf("\tout\t%s%n", set4.toTypedArray().contentToString())
            PS.printf("\texp\t%s%n", expected[2].contentToString())
            assertStreamEquals(Arrays.stream<String?>(expected[2]), set4.stream())

            PS.println()
        }

        fun generate(cmp: Comparator<String?>?, vararg items: String?): SortedSet<String?> {
            //PS.printf("\tin\t%s%n", Arrays.toString(items))
            val set: SortedSet<String?> = TreeSet<String?>(cmp)
            set.addAll(listOf<String?>(*items))
            set.addAll(listOf<String?>(*items)) // again
            return set
        }

        fun <A, R> forAll(set: MutableSet<A?>, f: Function<A?, R?>?): String {
            return set.stream().map<R?>(f).map<String?> { s: R? -> "\t" + s }.collect(Collectors.joining("\n"))
        }

        fun assertStreamEquals(s1: Stream<*>, s2: Stream<*>) {
            val iter1: MutableIterator<*> = s1.iterator()
            val iter2: MutableIterator<*> = s2.iterator()
            while (iter1.hasNext() && iter2.hasNext()) {
                Assertions.assertEquals(iter1.next(), iter2.next())
            }
            assert(!iter1.hasNext() && !iter2.hasNext())
        }
    }
}
