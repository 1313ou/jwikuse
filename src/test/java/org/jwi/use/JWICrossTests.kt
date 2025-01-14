package org.jwi.use

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

class JWICrossTests {

    @Test
    fun walkWord() {
        walk2(word!!)
    }

    @Test
    fun walkWord1() {
        PS.println(wnHome1)
        jwi1!!.walk(word!!, PS)
    }

    @Test
    fun walkWord2() {
        PS.println(wnHome2)
        jwi2!!.walk(word!!, PS)
    }

    private fun walk2(lemma: String) {
        PS.println(wnHome1)
        jwi1!!.walk(lemma, PS)
        PS.println(wnHome2)
        jwi2!!.walk(lemma, PS)
    }

    companion object {

        private val PS: PrintStream = makePS()

        private var word: String? = null

        var wnHome1: String? = null

        var wnHome2: String? = null

        private var jwi1: JWI? = null

        private var jwi2: JWI? = null

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            word = System.getProperty("WORD")
            wnHome1 = System.getProperty("SOURCE")
            wnHome2 = System.getProperty("SOURCE2")
            jwi1 = JWI(wnHome1!!)
            jwi2 = JWI(wnHome2!!)
        }
    }
}
