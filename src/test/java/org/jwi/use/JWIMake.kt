package org.jwi.use

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream
import kotlin.system.measureTimeMillis

class JWIMake {

    private lateinit var jwi: JWI

    @Test
    @Throws(IOException::class)
    fun makeTest() {
        val timeTaken = measureTimeMillis {
            jwi = makeJWI()
        }
        println("Time taken loading : $timeTaken ms")
    }

    companion object {

        private lateinit var PS: PrintStream

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            PS = makePS()
        }
    }
}
