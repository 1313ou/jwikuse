package org.jwi.use

import edu.mit.jwi.item.SenseEntry
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.OutputStream
import java.io.PrintStream

class SensekeysCrossTests {

    @Test
    fun lookupSensekeys() {
        lookupSensekey("you_bet%4:02:00::")
        lookupSensekey("electric%5:00:00:exciting:00")
    }

    @Test
    fun findSensekeys() {
        findSensekeysOf("aborigine")
        findSensekeysOf("Aborigine")
    }

    fun findSensekeysOf(lemma: String) {
        val ses1: Collection<SenseEntry> = Sensekeys.findSensekeysOf(jwi1!!, lemma)
        val ses2: Collection<SenseEntry> = Sensekeys.findSensekeysOf(jwi2!!, lemma)
        PS.println("\n⯆$lemma")
        for (se in ses1) {
            PS.printf("1 %s %s%n", se.senseKey, se.offset)
        }
        for (se in ses2) {
            PS.printf("2 %s %s%n", se.senseKey, se.offset)
        }
    }

    fun lookupSensekey(skStr: String) {
        val se1 = Sensekeys.lookupSensekey(jwi1!!, skStr)
        val se2 = Sensekeys.lookupSensekey(jwi2!!, skStr)
        PS.println("\n⯈$skStr")
        PS.printf("1 %s %s%n", se1!!.senseKey, se1.offset)
        PS.printf("2 %s %s%n", se2!!.senseKey, se2.offset)
    }

    companion object {

        private val VERBOSE = !System.getProperties().containsKey("SILENT")

        private val PS: PrintStream = if (VERBOSE) System.out else PrintStream(object : OutputStream() {
            override fun write(b: Int) {
                //DO NOTHING
            }
        })

        private var jwi1: JWI? = null

        private var jwi2: JWI? = null

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            val wnHome1 = System.getProperty("SOURCE")
            val wnHome2 = System.getProperty("SOURCE2")
            jwi1 = JWI(wnHome1)
            jwi2 = JWI(wnHome2)
        }
    }
}
