package org.jwi.use

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
        findAllSensekeys(jwi)
    }

    //@Test
    fun resolveAllSensekeys() {
        resolveAllSensekeys(jwi)
    }

    @Test
    fun sensekeysToSenses() {
        resolveSpecificSensekeys(jwi, "earth%1:15:00::", "earth%1:15:01::")
    }

    @Test
    fun lemmasToMembers() {
        resolveSpecificLemmas(jwi, "earth", "Earth")
    }

    companion object {

        private fun resolveSpecificSensekeys(jwi: JWI, vararg sks: String) {
            sks.forEach {
                val sk: SenseKey = SenseKeyParser.parseLine(it)
                val sense = jwi.dict.getSense(sk)
                PS.println("sensekey $sk refers to sense $sense with lemma ${sense?.lemma}")
            }
        }

        private fun resolveSpecificLemmas(jwi: JWI, vararg words: String) {
            words.forEach {
                val lemmas = jwi.dict.getLemmasStartingWith(it.toString())
                PS.println("word $it refers to lemmas ${lemmas.joinToString(separator = ",")}")
            }
        }

        private fun findAllSensekeys(jwi: JWI) {
            val count = AtomicInteger(0)
            val errCount = AtomicInteger(0)

            jwi.forAllSenses { s: Sense ->
                val sk = s.senseKey
                val se = jwi.dict.getSenseEntry(sk)
                if (se == null) {
                    System.err.printf("Sensekey not found %s%n", sk.toString())
                    errCount.incrementAndGet()
                    return@forAllSenses
                }
                count.incrementAndGet()
            }
            PS.printf("Sensekeys: %d Errors: %d%n", count.get(), errCount.get())
        }

        private fun resolveAllSensekeys(jwi: JWI) {
            val count = AtomicInteger(0)
            val errCount = AtomicInteger(0)

            jwi.forAllSenses(Consumer forAllSenses@{ s: Sense? ->
                val sk = s!!.senseKey
                val se = jwi.dict.getSenseEntry(sk)
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
