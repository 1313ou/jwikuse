package org.kwi.use

import edu.mit.jwi.DictionaryFactory.fromFile
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

class KWICrossTests {

    @Test
    fun walkWord() {
        walk2(word!!)
    }

    @Test
    fun walkWord1() {
        PS.println(wnHome1)
        kwi1!!.walk(word!!, PS)
    }

    @Test
    fun walkWord2() {
        PS.println(wnHome2)
        kwi2!!.walk(word!!, PS)
    }

    private fun walk2(lemma: String) {
        PS.println(wnHome1)
        kwi1!!.walk(lemma, PS)
        PS.println(wnHome2)
        kwi2!!.walk(lemma, PS)
    }

    companion object {

        private val PS: PrintStream = makePS()

        private var word: String? = null

        var wnHome1: String? = null

        var wnHome2: String? = null

        private var kwi1: KWI? = null

        private var kwi2: KWI? = null

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            word = System.getProperty("WORD")
            wnHome1 = System.getProperty("SOURCE")
            wnHome2 = System.getProperty("SOURCE2")
            kwi1 = KWI(fromFile(wnHome1!!))
            kwi2 = KWI(fromFile(wnHome2!!))
        }
    }
}
