package org.jwi.use

import edu.mit.jwi.item.POS
import edu.mit.jwi.morph.SimpleStemmer
import edu.mit.jwi.morph.SimpleStemmer.Companion.cartesianProduct
import edu.mit.jwi.morph.WordnetStemmer
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

    val verbs = arrayOf("works", "does", "finished", "dies")
    val nouns = arrayOf("works", "vertices", "suffixes",  "does", "boatsful", "hearts breakers", "mice", "dice", "dies")
    val both = nouns + verbs

    @Test
    fun wordsToStemsTestNoun() {
        stems(POS.NOUN, *nouns)
    }

    @Test
    fun wordsToStemsTestVerb() {
        stems(POS.VERB, *verbs)
    }

    @Test
    fun wordsToStemsTestAll() {
        stems(null, *both)
    }

    @Test
    fun wordsToDictStemsTestNouns() {
        dictStems(POS.NOUN, "boatsful")
        dictStems(POS.NOUN, *nouns)
    }

    @Test
    fun wordsToDictStemsTestVerbs() {
        dictStems(POS.VERB, *verbs)
    }

    @Test
    fun wordsToDictStemsTestAll() {
        dictStems(null, *both)
    }

    companion object {

        private fun stems(pos: POS? = null, vararg words: String) {
            words.forEach {
                val lemmas = stemmer.findStems(it, null)
                PS.println("stems of '$it' for ${pos ?: "any"} are ${lemmas.joinToString(separator = ",", transform = {"'$it'"})}")
            }
        }

        private fun dictStems(pos: POS? = null, vararg words: String) {
            words.forEach {
                val lemmas = dictStemmer.findStems(it, pos)
                PS.println("dict stems of '$it' for ${pos ?: "any"} are ${lemmas.joinToString(separator = ",", transform = {"'$it'"})}")
            }
        }

        private lateinit var jwi: JWI

        private lateinit var PS: PrintStream

        private lateinit var stemmer: SimpleStemmer

        private lateinit var dictStemmer: WordnetStemmer

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            jwi = makeJWI()
            stemmer = SimpleStemmer()
            dictStemmer = WordnetStemmer(jwi.dict)
            PS = makePS()
        }
    }
}
