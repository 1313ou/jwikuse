package org.jwi.use

import edu.mit.jwi.DeserializedRAMDictionary
import edu.mit.jwi.RAMDictionary
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.jwi.use.JWIIterateTests.Companion.iterateAll
import org.jwi.use.JWIWalkTests.Companion.walk
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.PrintStream

class JWIExportTests {

    @Test
    fun export() {
        val d = jwi.dict as RAMDictionary
        d.export(FileOutputStream(dest))
        val d2 = DeserializedRAMDictionary(dest)
        val jwi2 = JWI(d2)
        iterateAll(jwi2, System.out)
        walk(jwi2, "love", System.out)
    }

    companion object {

        private lateinit var source: String

        private lateinit var dest: String

        private lateinit var PS: PrintStream

        private lateinit var jwi: JWI

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            PS = makePS()
            source = System.getProperty("SOURCE")
            dest = System.getProperty("SER") ?: "$source.ser"
            jwi = JWI(source, null, ramFactory)
        }
    }
}
