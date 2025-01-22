package org.kwi.use

import edu.mit.jwi.DictionaryFactory.fromSer
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.kwi.use.KWIIterateTests.Companion.iterateAll
import org.kwi.use.KWIWalkTests.Companion.walk
import java.io.IOException
import java.io.PrintStream

class KWIImportTests {

    @Test
    fun import() {
        val kwi = KWI(fromSer(source))
        iterateAll(kwi, System.out)
        walk(kwi, "love", System.out)
    }

    companion object {

        private lateinit var source: String

        private lateinit var PS: PrintStream

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            PS = makePS()
            source = System.getProperty("SER") ?: "$source.ser"
        }
    }
}
