package org.jwi.use

import edu.mit.jwi.item.Word
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.OutputStream
import java.io.PrintStream
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

class SensekeysTests {

    @Test
    fun findAllSensekeys() {
        findAllSensekeys(jwi!!)
    }

    @Test
    fun resolveAllSensekeys() {
        resolveAllSensekeys(jwi!!)
    }

    companion object {

        private val VERBOSE = !System.getProperties().containsKey("SILENT")

        private val PS: PrintStream = if (VERBOSE) System.out else PrintStream(object : OutputStream() {
            override fun write(b: Int) {
                //DO NOTHING
            }
        })

        private var jwi: JWI? = null

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            val wnHome = System.getProperty("SOURCE")
            jwi = JWI(wnHome)
        }

        private fun findAllSensekeys(jwi: JWI) {
            val count = AtomicInteger(0)
            val errCount = AtomicInteger(0)

            jwi.forAllSenses(Consumer forAllSenses@{ s: Word ->
                val sk = s.senseKey
                val se = jwi.dict.getSenseEntry(sk)
                if (se == null) {
                    System.err.printf("Sensekey not found %s%n", sk.toString())
                    errCount.incrementAndGet()
                    return@forAllSenses
                }
                count.incrementAndGet()
            })
            PS.printf("Sensekeys: %d Errors: %d%n", count.get(), errCount.get())
        }

        private fun resolveAllSensekeys(jwi: JWI) {
            val count = AtomicInteger(0)
            val errCount = AtomicInteger(0)

            jwi.forAllSenses(Consumer forAllSenses@{ s: Word? ->
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
    }
}
