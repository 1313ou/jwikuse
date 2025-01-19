package org.kwi.use

import edu.mit.jwi.item.SenseEntry
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
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
        val ses1: Collection<SenseEntry> = Sensekeys.findSensekeysOf(kwi1!!, lemma)
        val ses2: Collection<SenseEntry> = Sensekeys.findSensekeysOf(kwi2!!, lemma)
        PS.println("\n⯆$lemma")
        for (se in ses1) {
            PS.printf("1 %s %s%n", se.senseKey, se.offset)
        }
        for (se in ses2) {
            PS.printf("2 %s %s%n", se.senseKey, se.offset)
        }
    }

    fun lookupSensekey(skStr: String) {
        val se1 = Sensekeys.lookupSensekey(kwi1!!, skStr)
        val se2 = Sensekeys.lookupSensekey(kwi2!!, skStr)
        PS.println("\n⯈$skStr")
        PS.printf("1 %s %s%n", se1!!.senseKey, se1.offset)
        PS.printf("2 %s %s%n", se2!!.senseKey, se2.offset)
    }

    companion object {

        private lateinit var PS: PrintStream

        private var kwi1: KWI? = null

        private var kwi2: KWI? = null

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            PS = makePS()
            kwi1 = makeKWI()
            kwi2 = makeKWI("SOURCE2")
        }
    }
}
