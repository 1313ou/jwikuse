package org.jwi.use

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

class JWICounts {

    @Test
    fun iterateSenseKeys() {
        val c = jwi.seqAllSenseKeys().count()
        countAll(jwi, PS)
    }

    companion object {

        @JvmStatic
        fun countAll(jwi: JWI, ps: PrintStream) {
            val l = jwi.seqAllLemmas().count()
            val s = jwi.seqAllSenses().count()
            val k = jwi.seqAllSenseKeys().count()
            val e = jwi.seqAllSenseEntries().count()
            val ry = jwi.seqAllSynsetRelations().count()
            val fry = jwi.seqAllFlatSynsetRelations().count()
            val y = jwi.seqAllSynsets().count()
            val rs = jwi.seqAllSenseRelations().count()
            val frs = jwi.seqAllFlatSenseRelations().count()
            val report = """
                lemmas           =$l
                senses           =$s
                sensekeys        =$k
                sense entries    =$e
                sense relations  =$rs ($frs flat) 
                synsets          =$y
                synset relations =$ry ($fry flat)
            """.trimIndent()
            ps.println(report)
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
