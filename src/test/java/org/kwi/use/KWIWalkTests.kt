package org.kwi.use

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

class KWIWalkTests {

    @Test
    fun walkWords() {
        words.splitToSequence(',').forEach {
            PS.println("@".repeat(80))
            PS.println(it)
            PS.println("@".repeat(80))
            walk(kwi, it, PS)
        }
    }

    companion object {

        @JvmStatic
        fun walk(kwi: KWI, word: String, ps: PrintStream) {
            kwi.walk(word, ps)
        }

        private lateinit var PS: PrintStream

        private lateinit var words: String

        private lateinit var kwi: KWI

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            words = System.getProperty("WORD")
            kwi = makeKWI()
            PS = makePS()
        }
    }
}
