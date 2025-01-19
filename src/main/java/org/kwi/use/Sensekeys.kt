package org.kwi.use

import edu.mit.jwi.data.parse.SenseKeyParser
import edu.mit.jwi.item.SenseEntry
import edu.mit.jwi.item.SenseID
import java.util.function.Consumer

object Sensekeys {

    @JvmStatic
    fun findSensekeysOf(kwi: KWI, lemma: String): Collection<SenseEntry> {
        val result: MutableCollection<SenseEntry> = ArrayList<SenseEntry>()
        kwi.forAllSenseIDs(lemma, Consumer { si: SenseID ->
            val sense = kwi.dict.getSense(si)
            val generatedSk = sense!!.senseKey
            // lookup
            val se = kwi.dict.getSenseEntry(generatedSk)!!
            result.add(se)
        })
        return result
    }

    @JvmStatic
    fun lookupSensekey(kwi: KWI, skStr: String): SenseEntry? {
        val parsedSk = SenseKeyParser.parseLine(skStr)
        assert(skStr == parsedSk.toString())
        // lookup
        return kwi.dict.getSenseEntry(parsedSk)
    }
}
