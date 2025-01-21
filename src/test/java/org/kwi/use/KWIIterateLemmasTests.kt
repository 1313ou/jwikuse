package org.kwi.use

import edu.mit.jwi.DictionaryFactory.fromFile
import edu.mit.jwi.IDictionary
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.OutputStream
import java.io.PrintStream
import java.util.*
import java.util.function.Consumer

class KWIIterateLemmasTests {

    @Test
    fun iterateLemmas() {
        kwi!!.forAllLemmas(Consumer { x: String? -> PS.println(x) })
    }

    @Test
    fun searchLemmas() {
        val start = System.getProperty("TARGET")
        val actual: Set<String> = kwi!!.dict.getLemmasStartingWith(start, null, 0)

        val expected: MutableSet<String?> = TreeSet<String?>()
        kwi!!.forAllLemmas(Consumer { w: String? ->
            if (w!!.startsWith(start)) {
                expected.add(w)
            }
        })
        Assertions.assertEquals(expected, actual)
    }

    companion object {

        private val VERBOSE = !System.getProperties().containsKey("SILENT")

        private val PS: PrintStream = if (VERBOSE) System.out else PrintStream(object : OutputStream() {
            override fun write(b: Int) {
                //DO NOTHING
            }
        })

        private var kwi: KWI? = null

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            val wnHome = System.getProperty("SOURCE")
            kwi = KWI(fromFile(wnHome))
        }
    }
}
