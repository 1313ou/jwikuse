package org.jwi.use

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

class JWIWalkTests {

    @Test
    fun walkWord() {
        jwi.walk(word, PS)
    }

    companion object {

        private lateinit var PS: PrintStream

        private lateinit var word: String

        private lateinit var jwi: JWI

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            word = System.getProperty("WORD")
            jwi = makeJWI()
            PS = makePS()
        }
    }
}
