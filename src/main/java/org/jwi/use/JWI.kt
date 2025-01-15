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
import kotlin.Throws
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

    fun seqAllSenses(): Sequence<Word> = sequence {
        POS.entries.forEach { pos ->
            dict.getIndexWordIterator(pos).forEach {
                it.wordIDs.forEach {
                    val sense = dict.getWord(it)!!
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

    fun seqAllSenseRelations(): Sequence<Relation<Word>> = sequence {
        seqAllSenses().forEach { sense ->
            sense.related.keys.forEach { ptr ->
                sense.related[ptr]!!.forEach {
                    val related = dict.getWord(it)!!
                    yield(Relation(ptr.toString(), sense to related))
                }

            }
        }
    }

    fun seqAllFlatSenseRelations(): Sequence<Pair<Word, Word>> = sequence {
        seqAllSenses().forEach { sense ->
            sense.related.keys.forEach { ptr ->
                sense.allRelated.forEach {
                    val related = dict.getWord(it)!!
                    yield(sense to related)
                }
            }
        }
    }

    // F R O M   W O R D

    fun seqAllSenseIDs(lemma: String, pos: POS): Sequence<IWordID> = sequence {
        dict.getIndexWord(lemma, pos)!!.wordIDs.forEach {
            yield(it)
        }
    }

    fun seqAllSenseIDs(lemma: String): Sequence<IWordID> = sequence {
        POS.entries.forEach { pos ->
            seqAllSenseIDs(lemma, pos).forEach {
                yield(it)
            }
        }
    }

    fun seqAllSenses(lemma: String, pos: POS): Sequence<Word> = sequence {
        seqAllSenseIDs(lemma, pos)
            .forEach {
                val sense = dict.getWord(it)!!
                yield(sense)
            }
    }

    fun seqAllSenses(lemma: String): Sequence<Word> = sequence {
        seqAllSenseIDs(lemma)
            .forEach {
                val sense = dict.getWord(it)!!
                yield(sense)
            }
    }

    // I T E R A T I O N S

    fun forAllLemmas(f: Consumer<String>?) {
        seqAllLemmas().forEach { f?.accept(it) }
    }

    fun forAllSenses(f: Consumer<Word>?) {
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

    fun forAllSenseRelations(f: Consumer<Relation<Word>>?) {
        seqAllSenseRelations().forEach { f?.accept(it) }
    }

    fun forAllFlatSenseRelations(f: Consumer<Pair<Word, Word>>?) {
        seqAllFlatSenseRelations().forEach { f?.accept(it) }
    }

    // S E N S E   E X P L O R A T I O N

    fun forAllSenseIDs(lemma: String, f: Consumer<IWordID>?) {
        seqAllSenseIDs(lemma).forEach { f?.accept(it) }
    }

    fun forAllSenses(lemma: String, f: Consumer<Word>?) {
        seqAllSenses(lemma).forEach { f?.accept(it) }
    }

    fun forAllSenseIDs(lemma: String, pos: POS, f: Consumer<IWordID>?) {
        seqAllSenseIDs(lemma, pos).forEach { f?.accept(it) }
    }

    fun forAllSenses(lemma: String, pos: POS, f: Consumer<Word>?) {
        seqAllSenses(lemma, pos).forEach { f?.accept(it) }
    }

// T R E E   E X P L O R A T I O N S

    fun walk(lemma: String, ps: PrintStream) {
        for (pos in POS.entries) {
            walk(lemma, pos, ps)
        }
    }

    fun walk(lemma: String, pos: POS, ps: PrintStream) {
        // a line in an index file
        val idx = dict.getIndexWord(lemma, pos)
        if (idx != null) {
            // index
            ps.println()
            ps.println("================================================================================")
            ps.println("■ pos = " + pos.name)
            // ps.println("lemma = " + idx.getLemma());
            walk(idx, ps)
        }
    }

    fun walk(idx: IndexWord, ps: PrintStream) {
        val pointers: Set<Pointer> = idx.pointers
        for (ptr in pointers) {
            ps.println("has relation = $ptr")
        }

        // senseid=(lemma, synsetid, sensenum)
        val senseids: List<IWordID> = idx.wordIDs
        for (senseid in senseids)  // synset id, sense number, and lemma
        {
            walk(senseid, ps)
        }
    }

    fun walk(senseid: IWordID, ps: PrintStream) {
        ps.println("--------------------------------------------------------------------------------")

        //ps.println("senseid = " + senseid.toString());

        // sense=(senseid, lexid, sensekey, synset)
        val sense = dict.getWord(senseid)
        walk(sense!!, ps)

        // synset
        val synsetid = senseid.synsetID
        val synset = dict.getSynset(synsetid)
        ps.printf("● synset = %s%n", toString(synset!!))

        walk(synset, 1, ps)
    }

    fun walk(sense: Word, ps: PrintStream) {
        ps.printf("● sense: %s lexid: %d sensekey: %s%n", sense.toString(), sense.lexicalID, sense.senseKey)

        // adj marker
        val marker = sense.adjectiveMarker
        if (marker != null) {
            ps.println("  marker = $marker")
        }

        // sensekey
        val senseKey = sense.senseKey
        val senseEntry = dict.getSenseEntry(senseKey)
        if (senseEntry == null) {
            System.err.printf("⚠ Missing sensekey %s for sense at offset %d with pos %s%n", senseKey.toString(), sense.synset.offset, sense.pOS.toString())
            // throw new IllegalArgumentException(String.format("%s at offset %d with pos %s%n", senseKey.toString(), sense.getSynset().getOffset(),sense.getPOS().toString()));
        }

        // lexical relations
        val relatedMap: Map<Pointer, List<IWordID>> = sense.related
        walk(relatedMap, ps)

        // verb frames
        val verbFrames: List<VerbFrame>? = sense.verbFrames
        walk(verbFrames, sense.lemma, ps)

        ps.printf("  sensenum: %s tag cnt:%s%n", senseEntry?.senseNumber ?: "<missing>", senseEntry?.tagCount ?: "<missing>")
    }

    fun walk(relatedMap: Map<Pointer, List<IWordID>>, ps: PrintStream) {
        for (entry in relatedMap.entries) {
            val pointer = entry.key
            for (relatedId in entry.value) {
                val related = dict.getWord(relatedId)
                ps.printf("  %s lemma:%s synset:%s%n", pointer, related!!.lemma, related.synset.toString())
            }
        }
    }

    fun walk(verbFrames: List<VerbFrame>?, lemma: String, ps: PrintStream) {
        if (verbFrames != null) {
            for (verbFrame in verbFrames) {
                ps.printf("  verb frame: %s : %s%n", verbFrame.template, verbFrame.instantiateTemplate(lemma))
            }
        }
    }

    fun walk(synset: Synset, level: Int, ps: PrintStream) {
        val indentSpace = String(CharArray(level)).replace('\u0000', '\t')
        val links: Map<Pointer, List<SynsetID>> = synset.related
        for (p in links.keys) {
            ps.printf("%s🡆 %s%n", indentSpace, p.name)
            val relations2: List<SynsetID> = links[p]!!
            walk(relations2, p, level, ps)
        }
    }

    fun walk(relations2: List<SynsetID>, p: Pointer, level: Int, ps: PrintStream) {
        val indentSpace = String(CharArray(level)).replace('\u0000', '\t')
        for (synsetid2 in relations2) {
            val synset2 = dict.getSynset(synsetid2)
            ps.printf("%s%s%n", indentSpace, toString(synset2!!))

            walk(synset2, p, level + 1, ps)
        }
    }

    fun walk(synset: Synset, p: Pointer, level: Int, ps: PrintStream) {
        val indentSpace = String(CharArray(level)).replace('\u0000', '\t')
        val relations2: List<SynsetID> = synset.getRelatedFor(p)
        for (synsetid2 in relations2) {
            val synset2 = dict.getSynset(synsetid2)
            ps.printf("%s%s%n", indentSpace, toString(synset2!!))
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
            System.out.printf("FROM %s%n", wnHome)

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
                "RAM"    -> { url: URL, config: Config? -> Dictionary(url, config) }
                else     -> { url: URL, config: Config? -> RAMDictionary(url, IMMEDIATE_LOAD, config) }
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
