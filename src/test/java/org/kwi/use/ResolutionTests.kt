package org.kwi.use

import edu.mit.jwi.item.POS
import edu.mit.jwi.item.SenseIDWithLemma
import edu.mit.jwi.item.SynsetID
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

class ResolutionTests {

    val yi = SynsetID(2190548, POS.ADJECTIVE)

    @Test
    fun head() {
        val y = kwi.dict.getSynset(yi)!!
        val hs = y.adjHead
        println("head $hs")
    }

    @Test
    fun headSense() {
        val y = kwi.dict.getSynset(yi)!!
        val hsi = y.headSynsetID!!
        val y2 = kwi.dict.getSynset(hsi)!!
        val hs = y2.senses[0]
        println("headsense $hs")
    }

    @Test
    fun sensekey() {
        val si = SenseIDWithLemma(yi, "zero")
        val s = kwi.dict.getSense(si)!!
        val sk = s.senseKey
        println("headsense $sk")
    }

    companion object {

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
