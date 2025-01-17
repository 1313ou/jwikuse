package org.jwi.use

import edu.mit.jwi.item.POS
import edu.mit.jwi.morph.SimpleStemmer
import edu.mit.jwi.morph.SimpleStemmer.Companion.cartesianProduct
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

class StemsTests {

    @Test
    fun cartesianProductTest() {
        val lists = listOf("ABC".toList(), "123".toList(), "abc".toList())
        val product = cartesianProduct(lists)
        product.forEach { println(it.joinToString(separator = "")) }
    }

    @Test
    fun wordsToStemsTestVerb() {
        stems(POS.VERB, "works", "does", "finished")
    }

    @Test
    fun wordsToStemsTestNoun() {
        stems(POS.VERB, "works", "does", "boatsful", "hearts breakers")
    }

    @Test
    fun wordsToStemsTestAll() {
        stems(null, "works", "does", "finished", "boatsful", "hearts breakers")
    }

    companion object {

        private fun stems(pos: POS? = null, vararg words: String) {
            words.forEach {
                val lemmas = stemmer.findStems(it, null)
                PS.println("stems of '$it' for POS $pos are ${lemmas.joinToString(separator = "\n\t", prefix = "\n\t")}")
            }
        }

        private lateinit var PS: PrintStream

        private lateinit var stemmer: SimpleStemmer

        private lateinit var jwi: JWI

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            jwi = makeJWI()
            stemmer = SimpleStemmer()
            PS = makePS()
        }
    }
}
