package org.jwi.use

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

class JWIWalkTests {

    @Test
    fun walkWords() {
        words.splitToSequence(',').forEach {
            PS.println("@".repeat(80))
            PS.println(it)
            PS.println("@".repeat(80))
            walk(jwi, it, PS)
        }
    }

    companion object {

        private lateinit var PS: PrintStream

        private lateinit var words: String

        private lateinit var jwi: JWI

        @JvmStatic
        fun walk(jwi: JWI, word: String, ps: PrintStream) {
            jwi.walk(word, ps)
        }

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            words = System.getProperty("WORD")
            jwi = makeJWI()
            PS = makePS()
        }
    }
}
