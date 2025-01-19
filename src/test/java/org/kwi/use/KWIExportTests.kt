package org.kwi.use

import edu.mit.jwi.DeserializedRAMDictionary
import edu.mit.jwi.RAMDictionary
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.FileOutputStream
import java.io.IOException
import java.io.PrintStream

class KWIExportTests {

    @Test
    fun export() {
        val d = kwi.dict as RAMDictionary
        d.export(FileOutputStream(dest))
        val d2 = DeserializedRAMDictionary(dest)
        val kwi2 = KWI(d2)
        KWIIterateTests.Companion.iterateAll(kwi2, System.out)
        KWIWalkTests.Companion.walk(kwi2, "love", System.out)
    }

    companion object {

        private lateinit var source: String

        private lateinit var dest: String

        private lateinit var PS: PrintStream

        private lateinit var kwi: KWI

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            PS = makePS()
            source = System.getProperty("SOURCE")
            dest = System.getProperty("SER") ?: "$source.ser"
            kwi = KWI(source, null, ramFactory)
        }
    }
}
