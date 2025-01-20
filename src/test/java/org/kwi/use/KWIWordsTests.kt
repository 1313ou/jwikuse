package org.kwi.use

import edu.mit.jwi.item.Index
import edu.mit.jwi.item.POS
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.kwi.use.KWI.Companion.fromFile
import java.io.IOException
import java.io.OutputStream
import java.io.PrintStream

class KWIWordsTests {

    @Test
    fun searchWord() {
        for (pos in POS.entries) {
            val index: Index? = kwi!!.dict.getIndex(word!!, pos)
            if (index != null) {
                val lemma = index.lemma
                PS.println("$pos $lemma")
            }
        }
    }

    companion object {

        private val VERBOSE = !System.getProperties().containsKey("SILENT")

        private val PS: PrintStream = if (VERBOSE) System.out else PrintStream(object : OutputStream() {
            override fun write(b: Int) {
                //DO NOTHING
            }
        })

        private var word: String? = null

        private var kwi: KWI? = null

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            word = System.getProperty("WORD")
            val wnHome = System.getProperty("SOURCE")
            kwi = KWI(fromFile(wnHome))
        }
    }
}
