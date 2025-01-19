package org.kwi.use

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

class KWIIterateTests {

    @Test
    fun iterateLemmas() {
        kwi.forAllLemmas { PS.println(it) }
    }

    @Test
    fun iterateSenses() {
        kwi.forAllSenses { PS.println(it) }
    }

    @Test
    fun iterateSynsets() {
        kwi.forAllSynsets { PS.println(it) }
    }

    @Test
    fun iterateSenseEntries() {
        kwi.forAllSenseEntries { PS.println(it) }
    }

    @Test
    fun iterateSenseRelations() {
        kwi.forAllSenseRelations { PS.println(it) }
    }

    @Test
    fun iterateSynsetRelations() {
        kwi.forAllSynsetRelations { PS.println(it) }
    }

    @Test
    fun iterateFlatSenseRelations() {
        kwi.forAllFlatSenseRelations { PS.println(it) }
    }

    @Test
    fun iterateFlatSynsetRelations() {
        kwi.forAllFlatSynsetRelations { PS.println(it) }
    }

    @Test
    fun iterateSenseKeys() {
        kwi.forAllSenseKeys { PS.println(it) }
    }

    companion object {

        @JvmStatic
        fun iterateAll(kwi: KWI, ps: PrintStream) {
            kwi.forAllLemmas { ps.println(it) }
            kwi.forAllSenses { ps.println(it) }
            kwi.forAllSenseKeys { ps.println(it) }
            kwi.forAllSynsets { ps.println(it) }
            kwi.forAllSenseEntries { ps.println(it) }
            kwi.forAllSenseRelations { ps.println(it) }
            kwi.forAllFlatSenseRelations { ps.println(it) }
            kwi.forAllSynsetRelations { ps.println(it) }
            kwi.forAllFlatSynsetRelations { ps.println(it) }
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
