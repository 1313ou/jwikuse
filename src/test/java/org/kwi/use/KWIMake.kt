package org.kwi.use

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream
import kotlin.system.measureTimeMillis

class KWIMake {

    private lateinit var kwi: KWI

    @Test
    @Throws(IOException::class)
    fun makeTest() {
        val timeTaken = measureTimeMillis {
            kwi = makeKWI()
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
