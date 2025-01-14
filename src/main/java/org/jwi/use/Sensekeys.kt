package org.jwi.use

import edu.mit.jwi.data.parse.SenseKeyParser
import edu.mit.jwi.item.SenseEntry
import edu.mit.jwi.item.IWordID
import java.util.function.Consumer

object Sensekeys {

    @JvmStatic
    fun findSensekeysOf(jwi: JWI, lemma: String): Collection<SenseEntry> {
        val result: MutableCollection<SenseEntry> = ArrayList<SenseEntry>()
        jwi.forAllSenseIDs(lemma, Consumer { si: IWordID ->
            val sense = jwi.dict.getWord(si)
            val generatedSk = sense!!.senseKey
            // lookup
            val se = jwi.dict.getSenseEntry(generatedSk)!!
            result.add(se)
        })
        return result
    }

    @JvmStatic
    fun lookupSensekey(jwi: JWI, skStr: String): SenseEntry? {
        val parsedSk = SenseKeyParser.parseLine(skStr)
        assert(skStr == parsedSk.toString())
        // lookup
        return jwi.dict.getSenseEntry(parsedSk)
    }
}
