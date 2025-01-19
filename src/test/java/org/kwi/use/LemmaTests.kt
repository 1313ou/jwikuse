package org.kwi.use

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

class LemmaTests {

    @Test
    fun lemmasToMembers() {
        lemmasStartingWith(kwi, "Earth", "earth")
    }

    companion object {

        private fun lemmasStartingWith(kwi: KWI, vararg starts: String) {
            starts.forEach {
                val lemmas = kwi.dict.getLemmasStartingWith(it.toString())
                PS.println("lemmas starting with $it are ${lemmas.joinToString(separator = ",\n\t", prefix = "\n\t")}")
            }
        }

        private lateinit var PS: PrintStream

        private lateinit var kwi: KWI

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            kwi = makeKWI()
            PS = makePS()
        }
    }
}
