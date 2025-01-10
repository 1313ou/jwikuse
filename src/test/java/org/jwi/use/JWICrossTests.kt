package org.jwi.use

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.OutputStream
import java.io.PrintStream

class JWICrossTests {

    @Test
    fun walkWord() {
        walk2(word!!)
    }

    @Test
    fun walkWord1() {
        PS.println(jwi1!!.wnHome)
        jwi1!!.walk(word!!, PS)
    }

    @Test
    fun walkWord2() {
        PS.println(jwi2!!.wnHome)
        jwi2!!.walk(word!!, PS)
    }

    private fun walk2(lemma: String) {
        PS.println(jwi1!!.wnHome)
        jwi1!!.walk(lemma, PS)
        PS.println(jwi2!!.wnHome)
        jwi2!!.walk(lemma, PS)
    }

    companion object {

        private val VERBOSE = !System.getProperties().containsKey("SILENT")

        private val PS: PrintStream = if (VERBOSE) System.out else PrintStream(object : OutputStream() {
            override fun write(b: Int) {
                //DO NOTHING
            }
        })

        private var word: String? = null

        private var jwi1: JWI? = null

        private var jwi2: JWI? = null

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            word = System.getProperty("WORD")
            val wnHome1 = System.getProperty("SOURCE")
            val wnHome2 = System.getProperty("SOURCE2")
            jwi1 = JWI(wnHome1)
            jwi2 = JWI(wnHome2)
        }
    }
}
