package org.jwi.use

import edu.mit.jwi.*
import edu.mit.jwi.data.FileProvider
import edu.mit.jwi.data.LoadPolicy.IMMEDIATE_LOAD
import edu.mit.jwi.item.*
import java.io.File
import java.io.IOException
import java.io.PrintStream
import java.net.URL
import java.util.function.Consumer
import kotlin.system.measureTimeMillis

val defaultFactory: (url: URL, config: Config?) -> IDictionary = { url: URL, config: Config? -> Dictionary(url, config) }
val nonCachingFactory: (url: URL, config: Config?) -> IDictionary = { url: URL, config: Config? -> DataSourceDictionary(FileProvider(url), config) }
val ramFactory: (url: URL, config: Config?) -> IDictionary = { url: URL, config: Config? -> RAMDictionary(url, IMMEDIATE_LOAD, config) }

/**
 * JWI
 *
 * @author Bernard Bou
 */
class JWI(
    val dict: IDictionary,
) {

    @JvmOverloads
    constructor(
        wnHome: String,
        config: Config? = null,
        factory: (url: URL, config: Config?) -> IDictionary = defaultFactory,
    ) : this(makeDict(wnHome, config, factory))

    init {
        dict.open()
    }

    // S E Q U E N C E S

    fun seqAllLemmas(): Sequence<String> = sequence {
        POS.entries.forEach { pos ->
            dict.getIndexWordIterator(pos).forEach {
                val lemma = it.lemma
                yield(lemma)
            }
        }
    }

    fun seqAllSenses(): Sequence<Sense> = sequence {
        POS.entries.forEach { pos ->
            dict.getIndexWordIterator(pos).forEach {
                it.wordIDs.forEach {
                    val sense = dict.getSense(it)!!
                    yield(sense)
                }
            }
        }
    }

    fun seqAllSenseKeys(): Sequence<SenseKey> = sequence {
        seqAllSenses().forEach {
            val sensekey = it.senseKey
            yield(sensekey)
        }
    }

    fun seqAllSenseEntries(): Sequence<SenseEntry> = sequence {
        dict.getSenseEntryIterator().forEach {
            val entry = it
            yield(entry)
        }
    }

    fun seqAllSynsets(): Sequence<Synset> = sequence {
        POS.entries.forEach { pos ->
            dict.getSynsetIterator(pos).forEach {
                val synset = it
                yield(synset)
            }
        }
    }

    fun seqAllSynsetRelations(): Sequence<Relation<Synset>> = sequence {
        seqAllSynsets().forEach { synset ->
            synset.related.keys.forEach { ptr ->
                synset.related[ptr]!!.forEach {
                    val related = dict.getSynset(it)!!
                    yield(Relation(ptr.toString(), synset to related))
                }
            }
        }
    }

    fun seqAllFlatSynsetRelations(): Sequence<Pair<Synset, Synset>> = sequence {
        seqAllSynsets().forEach { synset ->
            synset.allRelated.forEach {
                val related = dict.getSynset(it)!!
                yield(synset to related)
            }
        }
    }

    fun seqAllSenseRelations(): Sequence<Relation<Sense>> = sequence {
        seqAllSenses().forEach { sense ->
            sense.related.keys.forEach { ptr ->
                sense.related[ptr]!!.forEach {
                    val related = dict.getSense(it)!!
                    yield(Relation(ptr.toString(), sense to related))
                }

            }
        }
    }

    fun seqAllFlatSenseRelations(): Sequence<Pair<Sense, Sense>> = sequence {
        seqAllSenses().forEach { sense ->
            sense.related.keys.forEach { ptr ->
                sense.allRelated.forEach {
                    val related = dict.getSense(it)!!
                    yield(sense to related)
                }
            }
        }
    }

    // F R O M   W O R D

    fun seqAllSenseIDs(lemma: String, pos: POS): Sequence<ISenseID> = sequence {
        dict.getIndexWord(lemma, pos)!!.wordIDs.forEach {
            yield(it)
        }
    }

    fun seqAllSenseIDs(lemma: String): Sequence<ISenseID> = sequence {
        POS.entries.forEach { pos ->
            seqAllSenseIDs(lemma, pos).forEach {
                yield(it)
            }
        }
    }

    fun seqAllSenses(lemma: String, pos: POS): Sequence<Sense> = sequence {
        seqAllSenseIDs(lemma, pos)
            .forEach {
                val sense = dict.getSense(it)!!
                yield(sense)
            }
    }

    fun seqAllSenses(lemma: String): Sequence<Sense> = sequence {
        seqAllSenseIDs(lemma)
            .forEach {
                val sense = dict.getSense(it)!!
                yield(sense)
            }
    }

    // I T E R A T I O N S

    fun forAllLemmas(f: Consumer<String>?) {
        seqAllLemmas().forEach { f?.accept(it) }
    }

    fun forAllSenses(f: Consumer<Sense>?) {
        seqAllSenses().forEach { f?.accept(it) }
    }

    fun forAllSenseKeys(f: Consumer<SenseKey>?) {
        seqAllSenseKeys().forEach { f?.accept(it) }
    }

    fun forAllSynsets(f: Consumer<Synset>?) {
        seqAllSynsets().forEach { f?.accept(it) }
    }

    fun forAllSenseEntries(f: Consumer<SenseEntry>?) {
        seqAllSenseEntries().forEach { f?.accept(it) }
    }

    fun forAllSynsetRelations(f: Consumer<Relation<Synset>>?) {
        seqAllSynsetRelations().forEach { f?.accept(it) }
    }

    fun forAllFlatSynsetRelations(f: Consumer<Pair<Synset, Synset>>?) {
        seqAllFlatSynsetRelations().forEach { f?.accept(it) }
    }

    fun forAllSenseRelations(f: Consumer<Relation<Sense>>?) {
        seqAllSenseRelations().forEach { f?.accept(it) }
    }

    fun forAllFlatSenseRelations(f: Consumer<Pair<Sense, Sense>>?) {
        seqAllFlatSenseRelations().forEach { f?.accept(it) }
    }

    // S E N S E   E X P L O R A T I O N

    fun forAllSenseIDs(lemma: String, f: Consumer<ISenseID>?) {
        seqAllSenseIDs(lemma).forEach { f?.accept(it) }
    }

    fun forAllSenses(lemma: String, f: Consumer<Sense>?) {
        seqAllSenses(lemma).forEach { f?.accept(it) }
    }

    fun forAllSenseIDs(lemma: String, pos: POS, f: Consumer<ISenseID>?) {
        seqAllSenseIDs(lemma, pos).forEach { f?.accept(it) }
    }

    fun forAllSenses(lemma: String, pos: POS, f: Consumer<Sense>?) {
        seqAllSenses(lemma, pos).forEach { f?.accept(it) }
    }

    // T R E E   E X P L O R A T I O N S

    fun walk(lemma: String, ps: PrintStream) {
        POS.entries.forEach {
            walk(lemma, it, ps)
        }
    }

    fun walk(lemma: String, pos: POS, ps: PrintStream) {
        // a line in an index file
        dict.getIndexWord(lemma, pos)?.let {
            ps.println()
            ps.println("=".repeat(80))
            ps.println("■ pos = ${pos.name}")
            walk(it, ps)
        }
    }

    fun walk(idx: SenseIndex, ps: PrintStream) {
        // pointers
        idx.pointers.forEach {
            ps.println("has relation = $it")
        }
        // senses
        idx.wordIDs.forEach {
            walk(it, ps)
        }
    }

    fun walk(senseid: ISenseID, ps: PrintStream) {
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
        walk(sense.related, ps)

        // verb frames
        walk(sense.verbFrames, sense.lemma, ps)

        // sense entry
        val senseEntry = dict.getSenseEntry(sense.senseKey)!!
        ps.println("  sensenum=${senseEntry.senseNumber} tagcnt=${senseEntry.tagCount}")
    }

    fun walk(relatedMap: Map<Pointer, List<ISenseID>>, ps: PrintStream) {
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
        val links: Map<Pointer, List<SynsetID>> = synset.related
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
        val indentSpace = String(CharArray(level)).replace('\u0000', '\t')
        synset.getRelatedFor(p).forEach {
            val synset2 = dict.getSynset(it)!!
            ps.println("$indentSpace$synset2")
            if (canRecurse(p)) {
                walk(synset2, p, level + 1, ps)
            }
        }
    }

    companion object {

        // H E L P E R S

        @JvmStatic
        fun makeDict(
            wnHome: String,
            config: Config? = null,
            factory: (url: URL, config: Config?) -> IDictionary,
        ): IDictionary {
            println("FROM $wnHome")

            // construct the URL to the WordNet dictionary directory
            val home = File(wnHome).toURI().toURL()

            // construct the dictionary object and open it
            val dict = factory.invoke(home, config)

            // open it
            dict.open()
            return dict
        }

        @JvmStatic
        fun makeFileFactory(tag: String?): (file: File, config: Config?) -> IDictionary {
            println("File dictionary factory: $tag")
            return when (tag) {
                "SOURCE" -> { file: File, config: Config? -> DataSourceDictionary(file, config) }
                "RAM"    -> { file: File, config: Config? -> Dictionary(file, config) }
                else     -> { file: File, config: Config? -> RAMDictionary(file, IMMEDIATE_LOAD, config) }
            }
        }

        @JvmStatic
        fun makeURLFactory(tag: String?): (url: URL, config: Config?) -> IDictionary {
            println("URL dictionary factory: $tag")
            return when (tag) {
                "SOURCE" -> { url: URL, config: Config? -> DataSourceDictionary(url, config) }
                "RAM"    -> { url: URL, config: Config? -> RAMDictionary(url, IMMEDIATE_LOAD, config) }
                else     -> { url: URL, config: Config? -> Dictionary(url, config) }
            }
        }

        fun toString(synset: Synset): String {
            return getMembers(synset) + synset.gloss
        }

        fun getMembers(synset: Synset): String {
            val sb = StringBuilder()
            sb.append('{')
            var first = true
            for (sense in synset.words) {
                if (first) {
                    first = false
                } else {
                    sb.append(' ')
                }
                sb.append(sense.lemma)
            }
            sb.append('}')
            sb.append(' ')
            return sb.toString()
        }

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
            val factory = when (factoryHint) {
                "--ram"      -> ramFactory
                "--no-cache" -> nonCachingFactory
                else         -> defaultFactory
            }

            lateinit var jwi: JWI
            val timeTaken = measureTimeMillis { jwi = JWI(wnHome, null, factory) }
            val timeTaken2 = measureTimeMillis { jwi.walk(lemma, System.out) }

            println("Time taken loading : $timeTaken ms")
            println("Time taken browsing: $timeTaken2 ms")
        }
    }
}
