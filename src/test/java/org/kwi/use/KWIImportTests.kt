package org.kwi.use

import edu.mit.jwi.DeserializedRAMDictionary
import edu.mit.jwi.RAMDictionary
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.kwi.use.KWI.Companion.fromSer
import org.kwi.use.KWIIterateTests.Companion.iterateAll
import org.kwi.use.KWIWalkTests.Companion.walk
import java.io.FileOutputStream
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

        private lateinit var kwi: KWI

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            PS = makePS()
            source = System.getProperty("SER") ?: "$source.ser"
        }
    }
}
