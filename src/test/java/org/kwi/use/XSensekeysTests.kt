package org.kwi.use

import edu.mit.jwi.data.parse.SenseKeyParser
import edu.mit.jwi.item.SenseKey
import edu.mit.jwi.item.Synset.Sense
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

class XSensekeysTests {

    //@Test
    fun findAllSensekeys() {
        findAllSensekeys(kwi)
    }

    //@Test
    fun resolveAllSensekeys() {
        resolveAllSensekeys(kwi)
    }

    @Test
    fun sensekeysToSenses() {
        resolveSpecificSensekeys(kwi, "earth%1:15:00::", "earth%1:15:01::")
    }

    @Test
    fun lemmasToMembers() {
        resolveSpecificLemmas(kwi, "earth", "Earth")
    }

    companion object {

        private fun resolveSpecificSensekeys(kwi: KWI, vararg sks: String) {
            sks.forEach {
                val sk: SenseKey = SenseKeyParser.parseLine(it)
                val sense = kwi.dict.getSense(sk)
                PS.println("sensekey $sk refers to sense $sense with lemma ${sense?.lemma}")
            }
        }

        private fun resolveSpecificLemmas(kwi: KWI, vararg words: String) {
            words.forEach {
                val lemmas = kwi.dict.getLemmasStartingWith(it.toString())
                PS.println("word $it refers to lemmas ${lemmas.joinToString(separator = ",")}")
            }
        }

        private fun findAllSensekeys(kwi: KWI) {
            val count = AtomicInteger(0)
            val errCount = AtomicInteger(0)

            kwi.forAllSenses { s: Sense ->
                val sk = s.senseKey
                val se = kwi.dict.getSenseEntry(sk)
                if (se == null) {
                    System.err.printf("Sensekey not found %s%n", sk.toString())
                    errCount.incrementAndGet()
                    return@forAllSenses
                }
                count.incrementAndGet()
            }
            PS.printf("Sensekeys: %d Errors: %d%n", count.get(), errCount.get())
        }

        private fun resolveAllSensekeys(kwi: KWI) {
            val count = AtomicInteger(0)
            val errCount = AtomicInteger(0)

            kwi.forAllSenses(Consumer forAllSenses@{ s: Sense? ->
                val sk = s!!.senseKey
                val se = kwi.dict.getSenseEntry(sk)
                if (se == null) {
                    System.err.printf("Sensekey not found %s%n", sk.toString())
                    errCount.incrementAndGet()
                    return@forAllSenses
                }
                val ofs = se.offset
                PS.printf("%s %s%n", sk, ofs)
                count.incrementAndGet()
            })
            PS.printf("Sensekeys: %d Errors: %d%n", count.get(), errCount.get())
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
