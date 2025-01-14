package org.jwi.use

import edu.mit.jwi.RAMDictionary
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.PrintStream

class JWIExportTests {

    @Test
    fun export() {

        val d = jwi.dict as RAMDictionary
        d.export(FileOutputStream(f))
        // var d = DeserializedRAMDictionary(f)

    }

    companion object {

        private val f = File("ser")

        private lateinit var PS: PrintStream

        private lateinit var source: String

        private lateinit var jwi: JWI

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            source = System.getProperty("SOURCE")
            jwi = JWI(source, null, ramFactory)
            PS = makePS()
        }
    }
}
