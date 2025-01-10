package org.jwi.use

import edu.mit.jwi.data.parse.SenseKeyParser.Companion.instance
import edu.mit.jwi.item.ISenseEntry
import edu.mit.jwi.item.IWordID
import java.util.function.Consumer

object Sensekeys {

    @JvmStatic
    fun findSensekeysOf(jwi: JWI, lemma: String): Collection<ISenseEntry> {
        val result: MutableCollection<ISenseEntry> = ArrayList<ISenseEntry>()
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
    fun lookupSensekey(jwi: JWI, skStr: String): ISenseEntry? {
        val parsedSk = instance!!.parseLine(skStr)
        assert(skStr == parsedSk.toString())
        // lookup
        return jwi.dict.getSenseEntry(parsedSk)
    }
}
