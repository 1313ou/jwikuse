package org.kwi.use

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

class KWICounts {

    @Test
    fun iterateSenseKeys() {
        countAll(kwi, PS)
    }

    companion object {

        @JvmStatic
        fun countAll(kwi: KWI, ps: PrintStream) {
            val l = kwi.seqAllLemmas().count()
            val s = kwi.seqAllSenses().count()
            val k = kwi.seqAllSenseKeys().count()
            val e = kwi.seqAllSenseEntries().count()
            val ry = kwi.seqAllSynsetRelations().count()
            val fry = kwi.seqAllFlatSynsetRelations().count()
            val y = kwi.seqAllSynsets().count()
            val rs = kwi.seqAllSenseRelations().count()
            val frs = kwi.seqAllFlatSenseRelations().count()
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
