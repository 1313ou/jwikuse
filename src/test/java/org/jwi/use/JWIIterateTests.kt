package org.jwi.use

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

class JWIIterateTests {

    @Test
    fun iterateLemmas() {
        jwi.forAllLemmas { PS.println(it) }
    }

    @Test
    fun iterateSenses() {
        jwi.forAllSenses { PS.println(it) }
    }

    @Test
    fun iterateSynsets() {
        jwi.forAllSynsets { PS.println(it) }
    }

    @Test
    fun iterateSenseEntries() {
        jwi.forAllSenseEntries { PS.println(it) }
    }

    @Test
    fun iterateSenseRelations() {
        jwi.forAllSenseRelations { PS.println(it) }
    }

    @Test
    fun iterateSynsetRelations() {
        jwi.forAllSynsetRelations { PS.println(it) }
    }

    @Test
    fun iterateFlatSenseRelations() {
        jwi.forAllFlatSenseRelations { PS.println(it) }
    }

    @Test
    fun iterateFlatSynsetRelations() {
        jwi.forAllFlatSynsetRelations { PS.println(it) }
    }

    @Test
    fun iterateSenseKeys() {
        jwi.forAllSenseKeys { PS.println(it) }
    }

    companion object {

        private lateinit var PS: PrintStream

        private lateinit var jwi: JWI

        @JvmStatic
        fun iterateAll(jwi: JWI, ps: PrintStream) {
            jwi.forAllLemmas { ps.println(it) }
            jwi.forAllSenses { ps.println(it) }
            jwi.forAllSenseKeys { ps.println(it) }
            jwi.forAllSynsets { ps.println(it) }
            jwi.forAllSenseEntries { ps.println(it) }
            jwi.forAllSenseRelations { ps.println(it) }
            jwi.forAllFlatSenseRelations { ps.println(it) }
            jwi.forAllSynsetRelations { ps.println(it) }
            jwi.forAllFlatSynsetRelations { ps.println(it) }
        }

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            jwi = makeJWI()
            PS = makePS()
        }
    }
}
