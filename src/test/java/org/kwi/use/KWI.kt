package org.kwi.use

import edu.mit.jwi.DictionaryFactory
import edu.mit.jwi.IDictionary
import java.io.IOException
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



    companion object {



        /**
         * Main
         *
         * @param args arguments
         * @throws java.io.IOException io exception
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
            val timeTaken = measureTimeMillis { kwi = KWI(DictionaryFactory.fromFile(wnHome, factory = DictionaryFactory.makeFactory(tag))) }
            val timeTaken2 = measureTimeMillis { PrintWalker(kwi.dict).walk(lemma, System.out) }

            println("Time taken loading : $timeTaken ms")
            println("Time taken browsing: $timeTaken2 ms")
        }
    }
}