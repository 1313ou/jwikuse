package org.jwi.use

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

class LemmaTests {

    @Test
    fun lemmasToMembers() {
        lemmasStartingWith(jwi, "Earth", "earth")
    }

    companion object {

        private fun lemmasStartingWith(jwi: JWI, vararg starts: String) {
            starts.forEach {
                val lemmas = jwi.dict.getLemmasStartingWith(it.toString())
                PS.println("lemmas starting with $it are ${lemmas.joinToString(separator = ",\n\t", prefix = "\n\t")}")
            }
        }

        private lateinit var PS: PrintStream

        private lateinit var jwi: JWI

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            jwi = makeJWI()
            PS = makePS()
        }
    }
}
