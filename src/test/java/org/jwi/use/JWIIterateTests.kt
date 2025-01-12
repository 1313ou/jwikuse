package org.jwi.use

import edu.mit.jwi.item.SenseEntry
import edu.mit.jwi.item.SenseKey
import edu.mit.jwi.item.Synset
import edu.mit.jwi.item.Word
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.OutputStream
import java.io.PrintStream
import java.util.function.Consumer

class JWIIterateTests {

    @Test
    fun iterateLemmas() {
        jwi!!.forAllLemmas(Consumer { l: String -> })
    }

    @Test
    fun iterateSenses() {
        jwi!!.forAllSenses(Consumer { s: Word -> })
    }

    @Test
    fun iterateSynsets() {
        jwi!!.forAllSynsets(Consumer { s: Synset -> })
    }

    @Test
    fun iterateSenseEntries() {
        jwi!!.forAllSenseEntries(Consumer { se: SenseEntry -> })
    }

    @Test
    fun iterateSenseRelations() {
        jwi!!.forAllSenseRelations(Consumer { r: Word -> })
    }

    @Test
    fun iterateSynsetRelations() {
        jwi!!.forAllSynsetRelations(Consumer { r: Synset -> })
    }

    @Test
    fun iterateSenseKeys() {
        jwi!!.forAllSensekeys(Consumer { sk: SenseKey -> })
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
    }
}
