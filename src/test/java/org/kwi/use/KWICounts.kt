package org.kwi.use

import edu.mit.jwi.Sequences.seqAllFlatSenseRelations
import edu.mit.jwi.Sequences.seqAllFlatSynsetRelations
import edu.mit.jwi.Sequences.seqAllLemmas
import edu.mit.jwi.Sequences.seqAllSenseEntries
import edu.mit.jwi.Sequences.seqAllSenseKeys
import edu.mit.jwi.Sequences.seqAllSenseRelations
import edu.mit.jwi.Sequences.seqAllSenses
import edu.mit.jwi.Sequences.seqAllSynsetRelations
import edu.mit.jwi.Sequences.seqAllSynsets
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

class KWICounts {

    @Test
    fun count() {
        countAll(kwi, PS)
    }

    companion object {

        @JvmStatic
        fun countAll(kwi: KWI, ps: PrintStream) {
            val l = kwi.dict.seqAllLemmas().count()
            val s = kwi.dict.seqAllSenses().count()
            val k = kwi.dict.seqAllSenseKeys().count()
            val e = kwi.dict.seqAllSenseEntries().count()
            val ry = kwi.dict.seqAllSynsetRelations().count()
            val fry = kwi.dict.seqAllFlatSynsetRelations().count()
            val y = kwi.dict.seqAllSynsets().count()
            val rs = kwi.dict.seqAllSenseRelations().count()
            val frs = kwi.dict.seqAllFlatSenseRelations().count()
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
