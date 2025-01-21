package org.kwi.use

import edu.mit.jwi.*
import edu.mit.jwi.DictionaryFactory.fromFile
import edu.mit.jwi.DictionaryFactory.makeFactory
import edu.mit.jwi.Sequences.seqAllFlatSenseRelations
import edu.mit.jwi.Sequences.seqAllFlatSynsetRelations
import edu.mit.jwi.Sequences.seqAllLemmas
import edu.mit.jwi.Sequences.seqAllSenseEntries
import edu.mit.jwi.Sequences.seqAllSenseIDs
import edu.mit.jwi.Sequences.seqAllSenseKeys
import edu.mit.jwi.Sequences.seqAllSenseRelations
import edu.mit.jwi.Sequences.seqAllSenses
import edu.mit.jwi.Sequences.seqAllSynsetRelations
import edu.mit.jwi.Sequences.seqAllSynsets
import edu.mit.jwi.Sequences.seqMembers
import edu.mit.jwi.item.*
import edu.mit.jwi.item.Synset.Sense
import java.io.IOException
import java.io.PrintStream
import java.util.function.Consumer
import kotlin.system.measureTimeMillis

/**
 * KWI
 *
 * @author Bernard Bou
 */
class KWI(
    val dict: IDictionary,
) {

    init {
        dict.open()
    }

    // I T E R A T I O N S

    fun forAllLemmas(f: Consumer<String>?) {
        dict.seqAllLemmas().forEach { f?.accept(it) }
    }

    fun forAllSenses(f: Consumer<Sense>?) {
        dict.seqAllSenses().forEach { f?.accept(it) }
    }

    fun forAllSenseKeys(f: Consumer<SenseKey>?) {
        dict.seqAllSenseKeys().forEach { f?.accept(it) }
    }

    fun forAllSynsets(f: Consumer<Synset>?) {
        dict.seqAllSynsets().forEach { f?.accept(it) }
    }

    fun forAllSenseEntries(f: Consumer<SenseEntry>?) {
        dict.seqAllSenseEntries().forEach { f?.accept(it) }
    }

    fun forAllSynsetRelations(f: Consumer<Relation<Synset>>?) {
        dict.seqAllSynsetRelations().forEach { f?.accept(it) }
    }

    fun forAllFlatSynsetRelations(f: Consumer<Pair<Synset, Synset>>?) {
        dict.seqAllFlatSynsetRelations().forEach { f?.accept(it) }
    }

    fun forAllSenseRelations(f: Consumer<Relation<Sense>>?) {
        dict.seqAllSenseRelations().forEach { f?.accept(it) }
    }

    fun forAllFlatSenseRelations(f: Consumer<Pair<Sense, Sense>>?) {
        dict.seqAllFlatSenseRelations().forEach { f?.accept(it) }
    }

    // S E N S E   E X P L O R A T I O N

    fun forAllSenseIDs(lemma: String, f: Consumer<SenseID>?) {
        dict.seqAllSenseIDs(lemma).forEach { f?.accept(it) }
    }

    fun forAllSenses(lemma: String, f: Consumer<Sense>?) {
        dict.seqAllSenses(lemma).forEach { f?.accept(it) }
    }

    fun forAllSenseIDs(lemma: String, pos: POS, f: Consumer<SenseID>?) {
        dict.seqAllSenseIDs(lemma, pos).forEach { f?.accept(it) }
    }

    fun forAllSenses(lemma: String, pos: POS, f: Consumer<Sense>?) {
        dict.seqAllSenses(lemma, pos).forEach { f?.accept(it) }
    }

    // T R E E   E X P L O R A T I O N S

    fun walk(lemma: String, ps: PrintStream) {
        POS.entries.forEach {
            walk(lemma, it, ps)
        }
    }

    fun walk(lemma: String, pos: POS, ps: PrintStream) {
        // a line in an index file
        dict.getIndex(lemma, pos)?.let {
            ps.println()
            ps.println("=".repeat(80))
            ps.println("■ pos = ${pos.name}")
            walk(it, ps)
        }
    }

    fun walk(idx: Index, ps: PrintStream) {
        // pointers
        idx.pointers.forEach {
            ps.println("has relation = $it")
        }
        // senses
        idx.senseIDs.forEach {
            walk(it, ps)
        }
    }

    fun walk(senseid: SenseID, ps: PrintStream) {
        ps.println("-".repeat(80))
        walk(dict.getSense(senseid)!!, ps)

        // synset
        dict.getSynset(senseid.synsetID)!!.let {
            ps.println("● synset = $it")
            walk(it, 1, ps)
        }
    }

    fun walk(sense: Sense, ps: PrintStream) {
        ps.println("● sense = $sense lexid=${sense.lexicalID} sensekey=${sense.senseKey} ${sense.adjectiveMarker?.let { "  marker = $it" } ?: ""}")

        // lexical relations
        walk(sense.relatedSenses, ps)

        // verb frames
        walk(sense.verbFrames, sense.lemma, ps)

        // sense entry
        val senseEntry = dict.getSenseEntry(sense.senseKey)!!
        ps.println("  sensenum=${senseEntry.senseNumber} tagcnt=${senseEntry.tagCount}")
    }

    fun walk(relatedMap: Map<Pointer, List<SenseID>>, ps: PrintStream) {
        relatedMap.entries.forEach {
            val pointer = it.key
            it.value.forEach {
                val related = dict.getSense(it)!!
                ps.println("  $pointer lemma=${related.lemma} synset=${related.synset}")
            }
        }
    }

    fun walk(verbFrames: List<VerbFrame>?, lemma: String, ps: PrintStream) {
        verbFrames?.forEach {
            ps.println("  verb frame=${it.template} ${it.instantiateTemplate(lemma)}")
        }
    }

    fun walk(synset: Synset, level: Int, ps: PrintStream) {
        val indentSpace = "\t".repeat(level)
        val links: Map<Pointer, List<SynsetID>> = synset.relatedSynsets
        links.keys.forEach {
            ps.println("$indentSpace🡆 ${it.name}")
            walk(links[it]!!, it, level, ps)
        }
    }

    fun walk(relations2: List<SynsetID>, p: Pointer, level: Int, ps: PrintStream) {
        val indentSpace = "\t".repeat(level)
        relations2.forEach {
            val synset2 = dict.getSynset(it)!!
            ps.println("$indentSpace$synset2")
            walk(synset2, p, level + 1, ps)
        }
    }

    fun walk(synset: Synset, p: Pointer, level: Int, ps: PrintStream) {
        val indentSpace = "\t".repeat(level)
        synset.getRelatedSynsetsFor(p).forEach {
            val synset2 = dict.getSynset(it)!!
            ps.println("$indentSpace$synset2")
            if (canRecurse(p)) {
                walk(synset2, p, level + 1, ps)
            }
        }
    }

    fun Synset.getMembers(): String {
        return dict.seqMembers(this).joinToString(separator = ",", prefix = "{", postfix = "}")
    }

    fun Synset.toString(): String {
        return "${getMembers()}  $gloss"
    }

    companion object {


         private fun canRecurse(p: Pointer): Boolean {
            val symbol = p.symbol
            when (symbol) {
                "@", "~", "%p", "#p", "%m", "#m", "%s", "#s", "*", ">" -> return true
            }
            return false
        }

        /**
         * Main
         *
         * @param args arguments
         * @throws IOException io exception
         */
        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val wnHome = args[0]
            val lemma = args[1]
            val factoryHint = if (args.size > 2) args[2] else null
            val tag = when (factoryHint) {
                "--ram"      -> "RAM"
                "--no-cache" -> "SOURCE"
                else         -> "DEFAULT"
            }

            lateinit var kwi: KWI
            val timeTaken = measureTimeMillis { kwi = KWI(fromFile(wnHome, factory=makeFactory(tag))) }
            val timeTaken2 = measureTimeMillis { kwi.walk(lemma, System.out) }

            println("Time taken loading : $timeTaken ms")
            println("Time taken browsing: $timeTaken2 ms")
        }
    }
}
