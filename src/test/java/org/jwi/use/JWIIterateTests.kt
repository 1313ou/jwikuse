package org.jwi.use

import edu.mit.jwi.item.ISenseEntry
import edu.mit.jwi.item.ISenseKey
import edu.mit.jwi.item.ISynset
import edu.mit.jwi.item.IWord
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.OutputStream
import java.io.PrintStream
import java.util.function.Consumer

class JWIIterateTests {

    @Test
    fun iterateLemmas() {
        jwi!!.forAllLemmas(Consumer { l: String? -> })
    }

    @Test
    fun iterateSenses() {
        jwi!!.forAllSenses(Consumer { s: IWord? -> })
    }

    @Test
    fun iterateSynsets() {
        jwi!!.forAllSynsets(Consumer { s: ISynset? -> })
    }

    @Test
    fun iterateSenseEntries() {
        jwi!!.forAllSenseEntries(Consumer { se: ISenseEntry? -> })
    }

    @Test
    fun iterateSenseRelations() {
        jwi!!.forAllSenseRelations(Consumer { r: IWord? -> })
    }

    @Test
    fun iterateSynsetRelations() {
        jwi!!.forAllSynsetRelations(Consumer { r: ISynset? -> })
    }

    @Test
    fun iterateSenseKeys() {
        jwi!!.forAllSensekeys(Consumer { sk: ISenseKey? -> })
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
