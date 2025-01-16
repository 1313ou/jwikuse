package org.jwi.use

import edu.mit.jwi.morph.SimpleStemmer
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

class StemsTests {

    @Test
    fun lemmasToMembers() {
        stems(jwi, "works", "does", "finished", "boatsful", "hearts breakers")
    }

    companion object {

        private fun stems(jwi: JWI, vararg words: String) {
            words.forEach {
                val lemmas = stemmer.findStems(it, null)
                PS.println("stems of $it are ${lemmas.joinToString(separator = ",\n\t", prefix = "\n\t")}")
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
